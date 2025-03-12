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
            .background(Color.Black.copy(alpha = 0.5f)) // Dim background
            .clickable(onClick = onDismiss) // Close when clicked outside
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(0.85f),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.5f) // Glass effect
            ),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.6f),
                                Color.White.copy(alpha = 0.05f)
                            )
                        )
                    )
                    .border(
                        1.dp,
                        Color.Cyan.copy(alpha = 0.9f),
                        RoundedCornerShape(20.dp)
                    )
                    .padding(24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Tussi jaa rahe ho? Tussi na jao😳",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Phir se soch lo.... 😂",
                        fontSize = 20.sp,
                        color = Color.Cyan.copy(alpha = 6.5f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
//                        Button(
//                            onClick = onDismiss,
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = Color.Gray.copy(alpha = 0.7f)
//                            ),
//                            modifier = Modifier
//                                .weight(1f)
//                                .padding(end = 8.dp)
//                        ) {
                        Text(
                            text = "Cancel",
                            color = Color.Red.copy(alpha = 0.8f),
                            modifier = Modifier
                                .clickable { onDismiss() } // Proper function invocation
                                .padding(vertical = 12.dp, horizontal = 24.dp),
                            fontWeight = FontWeight.Bold
                        )
                        //}

                        Button(
                            onClick = onConfirm,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red.copy(alpha = 0.8f)
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp),
                            shape = RoundedCornerShape(12.dp),
                            elevation = ButtonDefaults.buttonElevation(8.dp)
                        ) {
                            Text("Logout", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}


//@Preview
//@Composable
//fun PreviewConfirmationCard() {
//    ConfirmationCard(
//        onConfirm = { /* Dummy action */ },
//        onDismiss = { /* Dummy action */ }
//    )
//}