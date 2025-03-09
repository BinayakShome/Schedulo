package com.example.schedule.view.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun ClassCard()
{
    Card(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
        .border(2.dp, Color.White, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(8.dp), // Matches border shape
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Text("OOPJ")
            Text("Room no.: C-201")
        }
    }
}