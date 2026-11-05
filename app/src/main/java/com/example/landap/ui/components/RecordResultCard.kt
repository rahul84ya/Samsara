package com.example.landap.ui.components

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.landap.model.DocumentType
import com.example.landap.model.LandRecord
import com.example.landap.ui.theme.*

@Composable
fun RecordResultCard(
    record: LandRecord,
    onToggleSave: (LandRecord) -> Unit,
    onViewFmbMap: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, OrangePrimary.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {

            // Official Andhra Pradesh Government Header Banner
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                color = OrangeDeepHeader
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "ఆంధ్రప్రదేశ్ ప్రభుత్వం - రెవెన్యూ శాఖ",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        fontSize = 15.sp
                    )
                    Text(
                        text = "GOVERNMENT OF ANDHRA PRADESH - REVENUE DEPARTMENT",
                        style = MaterialTheme.typography.labelSmall,
                        color = SaffronGoldContainer,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "మీభూమి డిజిటల్ అధికారిక ధృవీకరణ పత్రం (MeeBhoomi Certified Record)",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Form Title & Transaction Metadata Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(OrangeContainer)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = when (record.documentType) {
                            DocumentType.ADANGAL -> "గ్రామ లెక్క 3 - అడంగల్ / పహానీ నకలు (Form 3 Adangal)"
                            DocumentType.ROR_1B -> "హక్కుల పత్రం - 1B నకలు (Record of Rights ROR 1-B)"
                            DocumentType.FMB -> "క్షేత్ర కొలతల పుస్తకం (Field Measurement Book - FMB)"
                        },
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = OnOrangeContainer,
                        fontSize = 11.sp
                    )
                    Text(
                        text = "MeeSeva Ref: ${record.meesevaTxnId} • ULPIN: ${record.lpNumber}",
                        style = MaterialTheme.typography.labelSmall,
                        color = OrangeDark,
                        fontSize = 9.5.sp
                    )
                }

                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = Color(0xFF2E7D32)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(10.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "VERIFIED",
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Section 22-A Clearance Notice
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(6.dp),
                color = Color(0xFFE8F5E9),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF81C784))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.GppGood,
                        contentDescription = null,
                        tint = Color(0xFF2E7D32),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "సెక్షన్ 22-A తనిఖీ : ${record.section22AStatus}",
                        color = Color(0xFF1B5E20),
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Official 20-Point MeeBhoomi Tabular Ledger
            Text(
                text = "రెవెన్యూ రికార్డు వివరాలు (OFFICIAL REVENUE PARTICULARS)",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = OrangeDark,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .border(1.dp, BorderLight, RoundedCornerShape(6.dp))
            ) {
                RecordRow("1. జిల్లా (District)", "${record.district} [LGD: ${record.districtLgd}]", isEven = true)
                RecordRow("2. తహసీల్ / మండలం (Mandal)", "${record.mandal} [LGD: ${record.mandalLgd}]", isEven = false)
                RecordRow("3. రెవెన్యూ గ్రామం (Village)", "${record.village} [LGD: ${record.villageLgd}]", isEven = true)
                RecordRow("4. సర్వే నం. & సబ్-డివిజన్ (Survey No)", record.surveyNo, isEven = false, highlight = true)
                RecordRow("5. ఖాతా నం. (Khata / Passbook)", record.khataNo, isEven = true, highlight = true)
                RecordRow("6. 14-అంకెల భూ-ఆధార్ (ULPIN)", record.lpNumber, isEven = false)
                RecordRow("7. పట్టాదారు పేరు (Pattadar Name)", record.pattadarName, isEven = true, isBold = true)
                RecordRow("8. తండ్రి / భర్త పేరు (Father/Husband)", record.pattadarFatherName, isEven = false)
                RecordRow("9. అనుభవదారు పేరు (Occupant Name)", record.occupantName, isEven = true)
                RecordRow("10. అనుభవదారు సంబంధం (Relation)", "స్వయంగా (Self Enjoyment)", isEven = false)
                RecordRow("11. మొత్తం విస్తీర్ణం (Total Extent)", record.totalExtent, isEven = true, isBold = true)
                RecordRow("12. భూమి స్వభావం (Nature of Land)", record.natureOfLand, isEven = false)
                RecordRow("13. వర్గీకరణ (Classification)", record.landClassification, isEven = true)
                RecordRow("14. నీటి పారుదల వనరు (Water Source)", record.waterSource, isEven = false)
                RecordRow("15. నేల రకం & తారాం (Soil Type)", record.soilType, isEven = true)
                RecordRow("16. బేసిక్ మార్కెట్ విలువ (Govt Value)", record.marketValuePerAcre, isEven = false)
                RecordRow("17. ప్రస్తుత పంట (Crop Cultivated)", record.cultivationCrop, isEven = true)
                RecordRow("18. భూమి శిస్తు & సెస్ (Land Cess)", record.landRevenueCess, isEven = false)
                RecordRow("19. హక్కు సంక్రమణ (Title Acquisition)", record.titleAcquisitionMode, isEven = true)
                RecordRow("20. మ్యుటేషన్ వివరాలు (Mutation Ref)", record.lastMutationDate, isEven = false)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Cardinal Boundaries Box (చతుస్సీమలు)
            Text(
                text = "భూమి చతుస్సీమలు (CARDINAL BOUNDARIES)",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = OrangeDark,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(6.dp),
                color = SurfaceVariantLight,
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    record.adjacentSurveys.forEach { (dir, desc) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp)
                        ) {
                            Text(
                                text = "$dir: ",
                                fontWeight = FontWeight.Bold,
                                color = OrangeDark,
                                fontSize = 11.sp,
                                modifier = Modifier.width(60.dp)
                            )
                            Text(
                                text = desc,
                                color = TextPrimary,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Digital Signature Certificate Stamp
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(6.dp),
                color = Color(0xFFFFF8E1),
                border = androidx.compose.foundation.BorderStroke(1.dp, SaffronGoldContainer)
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.VerifiedUser,
                        contentDescription = null,
                        tint = OrangeDark,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = record.digitalSignatureInfo,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = OrangeDark,
                            fontSize = 10.sp
                        )
                        Text(
                            text = "ఈ పత్రం ఇన్ఫర్మేషన్ టెక్నాలజీ చట్టం 2000 ప్రకారం చట్టబద్ధమైన డిజిటల్ రికార్డు.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                            fontSize = 9.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action Buttons: FMB Map, Save Record, Share WhatsApp / SMS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onViewFmbMap,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("btn_view_fmb_map"),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = OrangeDark),
                    border = androidx.compose.foundation.BorderStroke(1.dp, OrangePrimary)
                ) {
                    Icon(
                        imageVector = Icons.Default.Map,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("FMB స్కెచ్", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { onToggleSave(record) },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("btn_toggle_save_record"),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (record.isSaved) Color(0xFF2E7D32) else OrangePrimary
                    )
                ) {
                    Icon(
                        imageVector = if (record.isSaved) Icons.Default.BookmarkAdded else Icons.Default.BookmarkBorder,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        if (record.isSaved) "సేవ్ అయింది" else "సేవ్ చేయి",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                IconButton(
                    onClick = {
                        val shareText = """
                            🏛️ ఆంధ్రప్రదేశ్ ప్రభుత్వ మీభూమి రికార్డు (Dhara App)
                            -------------------------------------------
                            📄 రికార్డు రకం: ${record.documentType.title}
                            📍 జిల్లా: ${record.district} [LGD: ${record.districtLgd}]
                            🏢 మండలం: ${record.mandal} [LGD: ${record.mandalLgd}]
                            🏡 గ్రామం: ${record.village} [LGD: ${record.villageLgd}]
                            🔢 సర్వే నంబర్: ${record.surveyNo}
                            📖 ఖాతా సంఖ్య: ${record.khataNo}
                            🆔 భూ-ఆధార్ ULPIN: ${record.lpNumber}
                            👤 పట్టాదారు: ${record.pattadarName} (${record.pattadarFatherName})
                            🌾 విస్తీర్ణం: ${record.totalExtent}
                            💧 భూమి రకం: ${record.landClassification}
                            💰 మార్కెట్ విలువ: ${record.marketValuePerAcre}
                            🛡️ సెక్షన్ 22-A: ${record.section22AStatus}
                            ✅ MeeSeva ID: ${record.meesevaTxnId}
                            -------------------------------------------
                            ధర (Dhara) యాప్ ద్వారా అధికారికంగా ధృవీకరించబడింది.
                        """.trimIndent()

                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, shareText)
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(sendIntent, "ల్యాండ్ రికార్డు షేర్ చేయండి"))
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceVariantLight)
                        .border(1.dp, BorderLight, RoundedCornerShape(8.dp))
                        .testTag("btn_share_record")
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = OrangeDark,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun RecordRow(
    label: String,
    value: String,
    isEven: Boolean,
    isBold: Boolean = false,
    highlight: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (highlight) Color(0xFFFFF3E0) else if (isEven) Color(0xFFFAFAFA) else Color.White)
            .padding(horizontal = 8.dp, vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            color = if (highlight) OrangeDark else Color(0xFF424242),
            fontWeight = if (highlight) FontWeight.Bold else FontWeight.Medium,
            modifier = Modifier.weight(1.1f)
        )
        Text(
            text = value,
            fontSize = 11.5.sp,
            color = if (highlight) OrangeDark else Color(0xFF1A1A1A),
            fontWeight = if (isBold || highlight) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.weight(1.3f)
        )
    }
}
