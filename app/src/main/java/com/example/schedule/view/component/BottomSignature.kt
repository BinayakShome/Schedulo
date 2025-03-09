package com.example.schedule.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun BottomSignature()
{
    Box(
        modifier = Modifier.fillMaxWidth().background(Color.Transparent)
    )
    {
        val uriHandler = LocalUriHandler.current
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                "@Binayak", modifier = Modifier.clickable {
                    uriHandler.openUri("https://www.linkedin.com/in/binayak-shome-831b192b6/?originalSubdomain=in")
                },
                color = Color.White,
                fontSize = 12.sp
            )
            Text(" | ",color = Color.White, fontSize = 13.sp)
            Text(
                "Contact", modifier = Modifier.clickable {
                    uriHandler.openUri("https://wa.me/+918812989114")
                },
                color = Color.White,
                fontSize = 12.sp
            )
            Text(" | ",color = Color.White, fontSize = 13.sp)
            Text(
                "Gmail", modifier = Modifier.clickable {
                    uriHandler.openUri("mailto:binayakshome3@gmail.com")
                },
                color = Color.White,
                fontSize = 12.sp
            )
            Text(" | ",color = Color.White, fontSize = 13.sp)
            Text(
                "Instagram", modifier = Modifier.clickable {
                    uriHandler.openUri("https://www.instagram.com/binayakshome_06/")
                },
                color = Color.White,
                fontSize = 12.sp
            )
        }
    }
}