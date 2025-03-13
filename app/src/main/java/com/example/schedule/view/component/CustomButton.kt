package com.example.schedule.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    onLogOutClick: () -> Unit,
    text: String,
    btncolor: Brush,
    textcolor: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(btncolor, shape = RoundedCornerShape(12.dp))
            .clickable { onLogOutClick() }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textcolor,
            fontWeight = FontWeight.Bold
        )
    }
}