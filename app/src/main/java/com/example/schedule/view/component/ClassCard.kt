package com.example.schedule.view.component

import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CurrentClassCard(
    subject: String,
    classFrom: String,
    classTo: String,
    roomNo: String,
    campusNo: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = subject,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.Gray, shape = RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "⏰ $classFrom - $classTo",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "📍 Room No: $roomNo",
                color = Color.Gray,
                fontSize = 14.sp
            )

            Text(
                text = "🏛️ Campus No: $campusNo",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}

//@Composable
//fun CurrentDayScheduleScreen() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Black)
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "📅 Today's Classes",
//            color = Color.White,
//            fontSize = 22.sp,
//            fontWeight = FontWeight.Bold
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        CurrentClassCard(
//            subject = "Data Structures & Algorithms",
//            classFrom = "10:00 AM",
//            classTo = "11:30 AM",
//            roomNo = "405",
//            campusNo = "6"
//        )
//
//        CurrentClassCard(
//            subject = "Operating Systems",
//            classFrom = "12:00 PM",
//            classTo = "1:30 PM",
//            roomNo = "310",
//            campusNo = "6"
//        )
//
//        CurrentClassCard(
//            subject = "Database Management",
//            classFrom = "3:00 PM",
//            classTo = "4:30 PM",
//            roomNo = "502",
//            campusNo = "3"
//        )
//    }
//}
//
//// Preview Section
//@Preview(showBackground = true, backgroundColor = 0xFF000000)
//@Composable
//fun PreviewCurrentDayScheduleScreen() {
//    CurrentDayScheduleScreen()
//}
