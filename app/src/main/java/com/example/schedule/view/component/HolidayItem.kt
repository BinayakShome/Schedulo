package com.example.schedule.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schedule.data.model.Holiday

@Composable
fun HolidayItem(holiday: Holiday) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Brush.verticalGradient(
                colors = listOf(Color(0xFF00C6FF), Color(0xFF0072FF)) // Gradient background
            ))
            .clip(MaterialTheme.shapes.medium) // Rounded corners
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Decorative line
            Box(
                modifier = Modifier
                    //.height(4.dp)
                    .fillMaxWidth()
                    .background(Color(0xFF00C6FF))
            )

            Text(
                text = holiday.EVENT,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 24.sp, // Slightly larger font size
                modifier = Modifier.padding(bottom = 12.dp) // More spacing between title and other info
            )
            Text(
                text = "Date: ${holiday.DATE}",
                color = Color(0xFFE0E0E0), // Lighter text color for secondary info
                fontSize = 18.sp, // Slightly larger font size for date
                modifier = Modifier.padding(bottom = 4.dp) // Add space between date and day
            )
            Text(
                text = "Day: ${holiday.DAYS}",
                color = Color(0xFFE0E0E0), // Lighter text color for secondary info
                fontSize = 18.sp
            )
        }
    }
}
