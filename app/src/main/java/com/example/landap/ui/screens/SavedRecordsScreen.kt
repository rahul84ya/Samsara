package com.example.landap.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.landap.data.LandRepository
import com.example.landap.model.DocumentType
import com.example.landap.model.LandRecord
import com.example.landap.ui.components.FmbMapViewer
import com.example.landap.ui.theme.*

@Composable
fun SavedRecordsScreen(
    repository: LandRepository,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val savedRecords by repository.savedRecords.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var expandedRecordId by remember { mutableStateOf<String?>(null) }
    var recordToDelete by remember { mutableStateOf<LandRecord?>(null) }

    val filteredRecords = remember(savedRecords, searchQuery) {
        if (searchQuery.isBlank()) savedRecords
        else savedRecords.filter {
            it.pattadarName.contains(searchQuery, ignoreCase = true) ||
            it.surveyNo.contains(searchQuery, ignoreCase = true) ||
            it.khataNo.contains(searchQuery, ignoreCase = true) ||
            it.village.contains(searchQuery, ignoreCase = true) ||
            it.district.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .testTag("saved_records_screen")
    ) {
        // Top Bar
        Surface(
            color = OrangeDeepHeader,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Text(
                    text = "Saved Land Records",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Offline accessible Adangal & ROR 1-B documents",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 11.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Search field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Filter saved records by survey, name, khata...") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = OrangePrimary)
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("filter_saved_input"),
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = OrangePrimary,
                    unfocusedBorderColor = BorderLight
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            if (filteredRecords.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.BookmarkBorder,
                            contentDescription = null,
                            tint = Color(0xFFFDBA74),
                            modifier = Modifier.size(56.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = if (searchQuery.isEmpty()) "No Saved Records Yet" else "No matching saved records",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = OrangeDark
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (searchQuery.isEmpty())
                                "When viewing land records on the Search tab, tap 'Save Offline' to store them here for instant offline access."
                            else "Try searching for a different survey number, Pattadar name, or village.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            lineHeight = 18.sp
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(filteredRecords) { record ->
                        val isExpanded = expandedRecordId == record.id
                        SavedRecordCardItem(
                            record = record,
                            isExpanded = isExpanded,
                            onToggleExpand = {
                                expandedRecordId = if (isExpanded) null else record.id
                            },
                            onDelete = { recordToDelete = record },
                            onShare = {
                                val shareText = buildString {
                                    appendLine("Dhara AP Land Record (${record.documentType.title})")
                                    appendLine("Pattadar: ${record.pattadarName}")
                                    appendLine("Village: ${record.village}, ${record.mandal}, ${record.district}")
                                    appendLine("Survey No: ${record.surveyNo} | Khata: ${record.khataNo}")
                                    appendLine("Total Extent: ${record.totalExtent}")
                                    appendLine("Classification: ${record.landClassification}")
                                    appendLine("ULPIN: ${record.lpNumber}")
                                }
                                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                    putExtra(Intent.EXTRA_TEXT, shareText)
                                    type = "text/plain"
                                }
                                context.startActivity(Intent.createChooser(sendIntent, "Share Saved Record"))
                            }
                        )
                    }
                }
            }
        }
    }

    // Confirm Delete Dialog
    recordToDelete?.let { rec ->
        AlertDialog(
            onDismissRequest = { recordToDelete = null },
            confirmButton = {
                Button(
                    onClick = {
                        repository.deleteSavedRecord(rec.id)
                        recordToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = StatusErrorRed)
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { recordToDelete = null }) {
                    Text("Cancel")
                }
            },
            title = { Text("Remove Saved Record?") },
            text = {
                Text("This will remove Survey No. ${rec.surveyNo} in ${rec.village} from your offline saved records.")
            }
        )
    }
}

@Composable
private fun SavedRecordCardItem(
    record: LandRecord,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    onDelete: () -> Unit,
    onShare: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onToggleExpand)
            .testTag("saved_item_${record.id}"),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = when (record.documentType) {
                        DocumentType.ADANGAL -> OrangeContainer
                        DocumentType.ROR_1B -> SaffronGoldContainer
                        DocumentType.FMB -> Color(0xFFFEF08A)
                    }
                ) {
                    Text(
                        text = record.documentType.title,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = when (record.documentType) {
                            DocumentType.ADANGAL -> OnOrangeContainer
                            DocumentType.ROR_1B -> OnSaffronContainer
                            DocumentType.FMB -> Color(0xFF713F12)
                        },
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onShare,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = TextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteOutline,
                            contentDescription = "Delete",
                            tint = StatusErrorRed,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = record.pattadarName,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = OrangeDark
            )

            Text(
                text = "${record.village}, ${record.mandal} Mandal • ${record.district} District",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Sy. No: ${record.surveyNo} • Khata: ${record.khataNo}",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = OrangePrimary
                )
                Text(
                    text = record.totalExtent,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = OrangeDark
                )
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(12.dp))
                Divider(color = BorderLight)
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "ULPIN: ${record.lpNumber}",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMuted,
                    fontSize = 10.sp
                )
                Text(
                    text = "Nature: ${record.natureOfLand}",
                    style = MaterialTheme.typography.bodySmall,
                    color = OrangeDark
                )
                Text(
                    text = "Water Source: ${record.waterSource}",
                    style = MaterialTheme.typography.bodySmall,
                    color = OrangeDark
                )
                Text(
                    text = "Market Rate: ${record.marketValuePerAcre}",
                    style = MaterialTheme.typography.bodySmall,
                    color = OrangeDark
                )

                if (record.documentType == DocumentType.FMB) {
                    Spacer(modifier = Modifier.height(10.dp))
                    FmbMapViewer(record = record)
                }
            }
        }
    }
}
