package com.example.schedule.view.component

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.schedule.R

@Composable
fun LoginButton(
    iconOnly: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    Button(
        modifier = Modifier.width(if (iconOnly) 40.dp else Dp.Unspecified),
        onClick = { onClick?.invoke() },
        shape = ButtonDefaults.shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color(0xFF1F1F1F),
        ),
        contentPadding = PaddingValues(horizontal = if (iconOnly) 8.0.dp else 12.dp),
        border = BorderStroke(
            width = 1.dp,
            color = Color(0xFF747775),
        ),
    ) {
        Box(
            modifier = Modifier
                .padding(end = if (iconOnly) 0.dp else 10.dp)
                .paint(painter = painterResource(id = R.drawable.google_logo))
        )
        if (!iconOnly) {
            Text(
                text = "Sign in with Google",
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
private fun LoginButton() {
    LoginButton(
        iconOnly = false,
        onClick = { println("Google button clicked") }
    )
}
