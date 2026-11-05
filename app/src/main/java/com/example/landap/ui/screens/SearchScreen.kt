package com.example.landap.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.landap.data.LandRepository
import com.example.landap.model.*
import com.example.landap.ui.components.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    repository: LandRepository,
    onNavigateToFmbFullscreen: (LandRecord) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val districts = remember { repository.districts }

    var selectedDistrict by remember { mutableStateOf(districts.first()) }
    var mandals by remember { mutableStateOf(repository.getMandalsForDistrict(districts.first().id)) }
    var selectedMandal by remember { mutableStateOf(mandals.getOrElse(1) { mandals.first() }) }
    var villages by remember { mutableStateOf(repository.getVillagesForMandal(selectedMandal.id)) }
    var selectedVillage by remember { mutableStateOf(villages.first()) }

    var selectedDocType by remember { mutableStateOf(DocumentType.ADANGAL) }
    var searchMode by remember { mutableStateOf(SearchMode.SURVEY_NO) }
    var query by remember { mutableStateOf("29/3") }

    val recentSearches by repository.recentSearches.collectAsState()

    var activeRecord by remember { mutableStateOf<LandRecord?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var showDisclaimer by remember { mutableStateOf(false) }
    var showPattadarModal by remember { mutableStateOf(false) }
    var showFmbInResult by remember { mutableStateOf(false) }

    // Initialize with a default fetched record for great preview
    LaunchedEffect(Unit) {
        activeRecord = repository.searchLandRecord(
            selectedDocType,
            selectedDistrict,
            selectedMandal,
            selectedVillage,
            searchMode,
            query
        )
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .testTag("search_screen")
    ) {
        GovernmentHeader(
            onOpenDisclaimer = { showDisclaimer = true }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Document Type Tabs (Adangal / 1-B / FMB)
            DocumentTypeSelector(
                selectedType = selectedDocType,
                onTypeSelected = { newType ->
                    selectedDocType = newType
                    if (newType == DocumentType.FMB) {
                        showFmbInResult = true
                    }
                    activeRecord?.let { current ->
                        activeRecord = current.copy(documentType = newType)
                    }
                }
            )

            // 4-Step Revenue Hierarchy Flow: District -> Tahseel/Mandal -> Village -> Survey Number
            LocationSelector(
                districts = districts,
                selectedDistrict = selectedDistrict,
                onDistrictSelected = { d ->
                    selectedDistrict = d
                    val newMandals = repository.getMandalsForDistrict(d.id)
                    mandals = newMandals
                    selectedMandal = newMandals.first()
                    val newVillages = repository.getVillagesForMandal(selectedMandal.id)
                    villages = newVillages
                    selectedVillage = newVillages.first()
                    query = selectedVillage.sampleSurveys.firstOrNull() ?: "29/3"
                },
                mandals = mandals,
                selectedMandal = selectedMandal,
                onMandalSelected = { m ->
                    selectedMandal = m
                    val newVillages = repository.getVillagesForMandal(m.id)
                    villages = newVillages
                    selectedVillage = newVillages.first()
                    query = selectedVillage.sampleSurveys.firstOrNull() ?: "29/3"
                },
                villages = villages,
                selectedVillage = selectedVillage,
                onVillageSelected = { v ->
                    selectedVillage = v
                    query = v.sampleSurveys.firstOrNull() ?: "29/3"
                },
                selectedSurveyNo = query,
                onSurveyNoSelected = { chosenSurvey ->
                    query = chosenSurvey
                    searchMode = SearchMode.SURVEY_NO
                    coroutineScope.launch {
                        isLoading = true
                        delay(450)
                        activeRecord = repository.searchLandRecord(
                            selectedDocType,
                            selectedDistrict,
                            selectedMandal,
                            selectedVillage,
                            SearchMode.SURVEY_NO,
                            chosenSurvey
                        )
                        isLoading = false
                    }
                }
            )

            // Search Filter & Manual Input Section
            SearchInputSection(
                searchMode = searchMode,
                onSearchModeChange = { mode ->
                    searchMode = mode
                    when (mode) {
                        SearchMode.SURVEY_NO -> query = selectedVillage.sampleSurveys.firstOrNull() ?: "29/3"
                        SearchMode.KHATA_NO -> query = "518"
                        SearchMode.PATTADAR_NAME -> query = "Padmavathi"
                        SearchMode.LP_NO -> query = "LP-00452"
                    }
                },
                query = query,
                onQueryChange = { query = it },
                onSearchSubmit = {
                    coroutineScope.launch {
                        isLoading = true
                        delay(600) // MeeBhoomi portal query simulation
                        activeRecord = repository.searchLandRecord(
                            selectedDocType,
                            selectedDistrict,
                            selectedMandal,
                            selectedVillage,
                            searchMode,
                            query
                        )
                        isLoading = false
                    }
                },
                onOpenPattadarDirectory = {
                    showPattadarModal = true
                },
                recentSearches = recentSearches,
                onSelectRecentSearch = { searchStr ->
                    val cleanVal = searchStr.substringAfterLast(":").trim()
                    if (cleanVal.isNotEmpty()) {
                        query = cleanVal
                        coroutineScope.launch {
                            isLoading = true
                            delay(400)
                            activeRecord = repository.searchLandRecord(
                                selectedDocType,
                                selectedDistrict,
                                selectedMandal,
                                selectedVillage,
                                searchMode,
                                cleanVal
                            )
                            isLoading = false
                        }
                    }
                },
                isLoading = isLoading
            )

            // Record Results Card
            activeRecord?.let { record ->
                RecordResultCard(
                    record = record,
                    onToggleSave = { rec ->
                        val updated = repository.toggleSaveRecord(rec)
                        activeRecord = updated
                    },
                    onViewFmbMap = {
                        showFmbInResult = !showFmbInResult
                    }
                )

                // Optional FMB Map Preview
                if (showFmbInResult || selectedDocType == DocumentType.FMB) {
                    FmbMapViewer(record = record)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Disclaimer Dialog
    if (showDisclaimer) {
        GovernmentDisclaimerDialog(onDismiss = { showDisclaimer = false })
    }

    // Pattadar Modal
    if (showPattadarModal) {
        val pattadars = remember(selectedVillage.code) {
            repository.getPattadarsForVillage(selectedVillage.code)
        }
        PattadarDirectoryModal(
            villageName = selectedVillage.name,
            pattadars = pattadars,
            onSelectPattadar = { selected ->
                query = selected.pattadarName
                showPattadarModal = false
                coroutineScope.launch {
                    isLoading = true
                    delay(500)
                    activeRecord = repository.searchLandRecord(
                        selectedDocType,
                        selectedDistrict,
                        selectedMandal,
                        selectedVillage,
                        SearchMode.PATTADAR_NAME,
                        selected.pattadarName
                    )
                    isLoading = false
                }
            },
            onDismiss = { showPattadarModal = false }
        )
    }
}
