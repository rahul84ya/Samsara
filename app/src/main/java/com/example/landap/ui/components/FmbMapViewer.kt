package com.example.landap.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.landap.model.LandRecord
import com.example.landap.ui.theme.*

@Composable
fun FmbMapViewer(
    record: LandRecord,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Map,
                        contentDescription = "FMB Cadastral Map",
                        tint = OrangePrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Cadastral Survey Map (FMB)",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = OrangeDark
                        )
                        Text(
                            text = "Survey No. ${record.surveyNo} • LPM ${record.lpNumber}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.sp
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = OrangeContainer
                ) {
                    Text(
                        text = "Scale 1:1000",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = OnOrangeContainer,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Canvas drawing cadastral polygon
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFFFFDF7)) // Warm drafting paper parchment tone
                    .border(1.dp, BorderLight, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height

                    // Grid lines (cadastral grid)
                    val gridSpacing = 40f
                    var x = gridSpacing
                    while (x < w) {
                        drawLine(
                            color = Color(0xFFFFEDD5).copy(alpha = 0.7f),
                            start = Offset(x, 0f),
                            end = Offset(x, h),
                            strokeWidth = 1f
                        )
                        x += gridSpacing
                    }
                    var y = gridSpacing
                    while (y < h) {
                        drawLine(
                            color = Color(0xFFFFEDD5).copy(alpha = 0.7f),
                            start = Offset(0f, y),
                            end = Offset(w, y),
                            strokeWidth = 1f
                        )
                        y += gridSpacing
                    }

                    // Scaled cadastral polygon points
                    val pA = Offset(w * 0.22f, h * 0.22f)
                    val pB = Offset(w * 0.76f, h * 0.26f)
                    val pC = Offset(w * 0.82f, h * 0.78f)
                    val pD = Offset(w * 0.26f, h * 0.82f)

                    val polygonPath = Path().apply {
                        moveTo(pA.x, pA.y)
                        lineTo(pB.x, pB.y)
                        lineTo(pC.x, pC.y)
                        lineTo(pD.x, pD.y)
                        close()
                    }

                    // Fill parcel with warm orange tint
                    drawPath(
                        path = polygonPath,
                        color = Color(0xFFF97316).copy(alpha = 0.15f)
                    )

                    // Draw parcel boundary lines
                    drawPath(
                        path = polygonPath,
                        color = Color(0xFF9A3412),
                        style = Stroke(width = 4f)
                    )

                    // Diagonal tie-line (survey diagonal for triangle area measurement)
                    drawLine(
                        color = Color(0xFFD97706),
                        start = pA,
                        end = pC,
                        strokeWidth = 2f
                    )

                    // Draw boundary stone corners
                    val cornerPoints = listOf(pA, pB, pC, pD)
                    cornerPoints.forEach { pt ->
                        drawCircle(
                            color = Color(0xFF7C2D12),
                            radius = 6f,
                            center = pt
                        )
                        drawCircle(
                            color = Color.White,
                            radius = 3f,
                            center = pt
                        )
                    }

                    // North Arrow at Top-Right
                    val northCenter = Offset(w - 36f, 36f)
                    drawCircle(
                        color = Color(0xFFEA580C).copy(alpha = 0.15f),
                        radius = 16f,
                        center = northCenter
                    )
                    drawLine(
                        color = Color(0xFFDC2626),
                        start = Offset(northCenter.x, northCenter.y + 12f),
                        end = Offset(northCenter.x, northCenter.y - 12f),
                        strokeWidth = 3f
                    )
                }

                // Overlay labels
                Column(
                    modifier = Modifier.fillMaxSize().padding(12.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "North: ${record.adjacentSurveys["North"] ?: "Survey 30"}",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF78716C),
                            fontSize = 10.sp
                        )
                        Text(
                            text = "N ↑",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFDC2626)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color.White.copy(alpha = 0.95f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, BorderLight)
                        ) {
                            Text(
                                text = "Sy. No. ${record.surveyNo} • ${record.totalExtent}",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = OrangeDark,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "West: ${record.adjacentSurveys["West"] ?: "Cart Track"}",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF78716C),
                            fontSize = 10.sp
                        )
                        Text(
                            text = "East: ${record.adjacentSurveys["East"] ?: "Survey 31"}",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF78716C),
                            fontSize = 10.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Boundary Sides Breakdown Table
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BoundarySideBadge("A-B (North)", "58.2m / 289 lk")
                BoundarySideBadge("B-C (East)", "72.8m / 362 lk")
                BoundarySideBadge("C-D (South)", "61.4m / 305 lk")
                BoundarySideBadge("D-A (West)", "64.5m / 320 lk")
            }
        }
    }
}

@Composable
private fun BoundarySideBadge(side: String, length: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = side,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 9.sp
        )
        Text(
            text = length,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = OrangeDark,
            fontSize = 10.sp
        )
    }
}
