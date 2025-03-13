package com.example.schedule.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeekScheduleList(weekSchedule: Map<String, List<Pair<String, String>>>) {
    val days = listOf(
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    )
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        days.forEach { day ->
            item {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "📅 $day",
                        color = Color.Cyan,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Divider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    )

                    val schedule = weekSchedule[day.uppercase().take(3)] ?: emptyList()
                    if (schedule.isEmpty()) {
                        Text(
                            text = "No classes scheduled 📌",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    } else {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(schedule) { (subject, room) ->
                                WeeklySchedule(subject = subject, roomNo = room)
                            }
                        }
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            BottomSignature()
        }
    }
}