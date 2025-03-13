package com.example.schedule.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LogoutConfirmationCard(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f)) // Dimmed background for modal effect
            .clickable(onClick = onDismiss) // Close when tapped outside
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(0.85f),
            colors = CardDefaults.cardColors(
                containerColor = Color.Black.copy(alpha = 0.6f) // Glass effect
            ),
            elevation = CardDefaults.cardElevation(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF2E2E2E).copy(alpha = 0.8f), // Dark Gray (Subtle)
                                Color(0xFF1C1C1C).copy(alpha = 0.6f)  // Slightly Lighter Gray
                            )
                        )
                    )
                    .border(
                        1.dp,
                        Color(0xFF4DB6AC), // Subtle cyan border
                        RoundedCornerShape(16.dp)
                    )
                    .padding(24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 🔥 Logout Heading
                    Text(
                        text = "Leaving so soon?",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // ❄️ Subtext
                    Text(
                        text = "Your journey isn't over yet! Want to logout?",
                        fontSize = 16.sp,
                        color = Color(0xFFB0BEC5), // Subtle grayish-blue
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // ❌ Cancel Button (Text Clickable)
                        Text(
                            text = "Cancel",
                            color = Color(0xFF64FFDA), // Neon Cyan
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .clickable { onDismiss() }
                                .padding(vertical = 12.dp, horizontal = 24.dp)
                        )

                        // ✅ Logout Button with Premium Gradient
                        Button(
                            onClick = onConfirm,
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp)
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(Color(0xFFFF6F61), Color(0xFFFF3D00)) // Deep Orange-Red Gradient
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            shape = RoundedCornerShape(12.dp),
                            //elevation = ButtonDefaults.buttonElevation(6.dp)
                        ) {
                            Text("Logout", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLogoutConfirmationCard() {
    LogoutConfirmationCard(
        onConfirm = { /* Dummy action */ },
        onDismiss = { /* Dummy action */ }
    )
}