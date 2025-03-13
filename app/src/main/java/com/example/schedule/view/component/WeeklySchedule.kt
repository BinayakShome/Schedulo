package com.example.schedule.view.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeeklySchedule(
    subject: String,
    roomNo: String
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 6.dp, vertical = 8.dp)
            .shadow(6.dp, shape = RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF252525)),
        border = BorderStroke(1.dp, Color.Cyan)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = subject,
                color = Color.Cyan,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "📍 Room No: $roomNo",
                color = Color.LightGray,
                fontSize = 14.sp // 🔥 Adjusted font size for balance
            )
        }
    }
}

// ✅ **Improved Preview**
@Preview(showBackground = true)
@Composable
fun PreviewWeeklySchedule() {
    Column(
        modifier = Modifier
            .background(Color.DarkGray) // 🔥 Dark theme preview background
            .padding(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp), // 🔥 Adds spacing between cards
            modifier = Modifier.fillMaxWidth()
        ) {
            WeeklySchedule(subject = "OS", roomNo = "A-LH-004")
            WeeklySchedule(subject = "DBMS", roomNo = "B-201")
        }
        Spacer(modifier = Modifier.height(8.dp)) // 🔥 Adds space between rows
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            WeeklySchedule(subject = "CN", roomNo = "C-105")
            WeeklySchedule(subject = "ML", roomNo = "ETC-301")
        }
    }
}
