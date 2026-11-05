package com.example.landap.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.landap.model.District
import com.example.landap.model.Mandal
import com.example.landap.model.Village
import com.example.landap.ui.theme.*

@Composable
fun LocationSelector(
    districts: List<District>,
    selectedDistrict: District,
    onDistrictSelected: (District) -> Unit,
    mandals: List<Mandal>,
    selectedMandal: Mandal,
    onMandalSelected: (Mandal) -> Unit,
    villages: List<Village>,
    selectedVillage: Village,
    onVillageSelected: (Village) -> Unit,
    selectedSurveyNo: String,
    onSurveyNoSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDistrictPicker by remember { mutableStateOf(false) }
    var showMandalPicker by remember { mutableStateOf(false) }
    var showVillagePicker by remember { mutableStateOf(false) }
    var showSurveyPicker by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header with LGD Tag
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(OrangePrimary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = OrangePrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Revenue Hierarchy (LGD Flow)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = OrangeDark
                    )
                }

                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = OrangeContainer
                ) {
                    Text(
                        text = "LGD State: 28",
                        style = MaterialTheme.typography.labelSmall,
                        color = OnOrangeContainer,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Step 1: District
            DropdownFlowStepItem(
                stepNumber = "1",
                label = "District (జిల్లా)",
                value = "${selectedDistrict.name} (${selectedDistrict.teluguName})",
                codeBadge = "LGD: ${selectedDistrict.lgdCode}",
                onClick = { showDistrictPicker = true },
                tag = "select_district_field"
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Step 2: Tahseel / Mandal
            DropdownFlowStepItem(
                stepNumber = "2",
                label = "Tahseel / Mandal (తహసీల్ / మండలం)",
                value = "${selectedMandal.name} (${selectedMandal.teluguName})",
                codeBadge = "LGD: ${selectedMandal.lgdCode}",
                onClick = { showMandalPicker = true },
                tag = "select_mandal_field"
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Step 3: Village
            DropdownFlowStepItem(
                stepNumber = "3",
                label = "Revenue Village (రెవెన్యూ గ్రామం)",
                value = "${selectedVillage.name} (${selectedVillage.teluguName})",
                codeBadge = "LGD: ${selectedVillage.lgdCode}",
                onClick = { showVillagePicker = true },
                tag = "select_village_field"
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Step 4: Survey Number & Sub-division
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, BorderLight, RoundedCornerShape(8.dp))
                    .background(SurfaceVariantLight)
                    .padding(horizontal = 10.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = OrangePrimary,
                            modifier = Modifier.size(18.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "4",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Survey Number / Sub-division",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextSecondary,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.sp
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = OrangePrimary.copy(alpha = 0.15f),
                        modifier = Modifier.clickable { showSurveyPicker = true }
                    ) {
                        Text(
                            text = "Selected: $selectedSurveyNo",
                            style = MaterialTheme.typography.labelSmall,
                            color = OrangePrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Quick survey number chips from the selected village
                Text(
                    text = "Quick Select in ${selectedVillage.name}:",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMuted,
                    fontSize = 10.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    selectedVillage.sampleSurveys.forEach { survey ->
                        val isChosen = survey == selectedSurveyNo
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (isChosen) OrangePrimary else Color.White,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isChosen) OrangePrimary else BorderLight
                            ),
                            modifier = Modifier
                                .clickable { onSurveyNoSelected(survey) }
                                .testTag("quick_survey_${survey.replace("/", "_")}")
                        ) {
                            Text(
                                text = survey,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (isChosen) FontWeight.Bold else FontWeight.Medium,
                                color = if (isChosen) Color.White else OrangeDark,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    // District Picker Sheet
    if (showDistrictPicker) {
        ItemSearchDialog(
            title = "Select AP District (28 Administrative Units)",
            items = districts,
            displayName = { "${it.name} (${it.teluguName})" },
            subtitle = { "HQ: ${it.headquarters} • LGD Code: ${it.lgdCode} • ${it.mandalCount} Mandals" },
            onSelect = {
                onDistrictSelected(it)
                showDistrictPicker = false
            },
            onDismiss = { showDistrictPicker = false }
        )
    }

    // Mandal Picker Sheet
    if (showMandalPicker) {
        ItemSearchDialog(
            title = "Select Tahseel/Mandal in ${selectedDistrict.name}",
            items = mandals,
            displayName = { "${it.name} (${it.teluguName})" },
            subtitle = { "LGD Code: ${it.lgdCode} • District: ${selectedDistrict.name}" },
            onSelect = {
                onMandalSelected(it)
                showMandalPicker = false
            },
            onDismiss = { showMandalPicker = false }
        )
    }

    // Village Picker Sheet
    if (showVillagePicker) {
        ItemSearchDialog(
            title = "Select Village in ${selectedMandal.name}",
            items = villages,
            displayName = { "${it.name} (${it.teluguName})" },
            subtitle = { "LGD Code: ${it.lgdCode} • ${it.totalSurveys} Registered Surveys" },
            onSelect = {
                onVillageSelected(it)
                showVillagePicker = false
            },
            onDismiss = { showVillagePicker = false }
        )
    }

    // Survey Picker Sheet
    if (showSurveyPicker) {
        ItemSearchDialog(
            title = "Survey Numbers in ${selectedVillage.name}",
            items = selectedVillage.sampleSurveys,
            displayName = { "Survey No. $it" },
            subtitle = { "Village: ${selectedVillage.name} (${selectedVillage.teluguName})" },
            onSelect = {
                onSurveyNoSelected(it)
                showSurveyPicker = false
            },
            onDismiss = { showSurveyPicker = false }
        )
    }
}

@Composable
private fun DropdownFlowStepItem(
    stepNumber: String,
    label: String,
    value: String,
    codeBadge: String,
    onClick: () -> Unit,
    tag: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, BorderLight, RoundedCornerShape(8.dp))
            .background(SurfaceVariantLight)
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .testTag(tag)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = OrangePrimary.copy(alpha = 0.2f),
                    modifier = Modifier.size(18.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = stepNumber,
                            color = OrangeDark,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextSecondary,
                    fontSize = 10.sp
                )
            }

            Surface(
                shape = RoundedCornerShape(4.dp),
                color = Color.White
            ) {
                Text(
                    text = codeBadge,
                    style = MaterialTheme.typography.labelSmall,
                    color = OrangePrimary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = OrangeDark,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = OrangePrimary
            )
        }
    }
}

@Composable
private fun <T> ItemSearchDialog(
    title: String,
    items: List<T>,
    displayName: (T) -> String,
    subtitle: (T) -> String = { "" },
    onSelect: (T) -> Unit,
    onDismiss: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val filtered = remember(searchQuery, items) {
        if (searchQuery.isBlank()) items
        else items.filter { displayName(it).contains(searchQuery, ignoreCase = true) || subtitle(it).contains(searchQuery, ignoreCase = true) }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        dismissButton = {},
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = OrangeDark
                )
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Filter items...") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = OrangePrimary)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_dialog_filter"),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = OrangePrimary,
                        unfocusedBorderColor = BorderLight
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 350.dp)
                ) {
                    items(filtered) { item ->
                        Surface(
                            onClick = { onSelect(item) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = SurfaceVariantLight,
                            border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight.copy(alpha = 0.5f))
                        ) {
                            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)) {
                                Text(
                                    text = displayName(item),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = OrangeDark
                                )
                                val sub = subtitle(item)
                                if (sub.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = sub,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextSecondary,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
