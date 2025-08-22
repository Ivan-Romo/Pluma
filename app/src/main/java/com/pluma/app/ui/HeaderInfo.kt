package com.pluma.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Definir los colores
val CardColor = Color(0xFF5D4036)
val Primary = Color(0xFFFFB59D)
val InsideCardColor = Color(0xFFF1DFDA)
val BackgroundColor = Color(0xFF1A110F)

@Composable
fun HeaderInfo(name: String, timeLeft: String = "20:25") {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Texto principal en blanco
        Text(
            text = "Your new lesson is here,",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Normal
            ),
            color = Color.White
        )


        Text(
            text = name,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Medium
            ),
            color = Primary
        )

        Spacer(modifier = Modifier.size(8.dp))

        // Fila del tiempo
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.WatchLater,
                contentDescription = "Próxima lección",
                tint = Primary,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = "Next lesson in:",
                style = MaterialTheme.typography.bodyMedium,
                color = InsideCardColor
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = timeLeft,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = InsideCardColor
            )
        }
    }
}