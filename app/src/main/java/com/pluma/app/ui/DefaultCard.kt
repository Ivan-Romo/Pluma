package com.pluma.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pluma.app.ui.theme.CardColor
import com.pluma.app.ui.theme.InsideCardColor
import com.pluma.app.ui.theme.Primary
import kotlin.random.Random

@Composable
fun DefaultCardView(title: String, subtitle: String, time: Int){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardColor
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Tiempo estimado de lectura
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = "",
                    tint = Primary,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "Estimated reading time: $time minutes",
                    style = MaterialTheme.typography.bodySmall,
                    color = InsideCardColor
                )
            }

            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    lineHeight = 36.sp
                ),
                color = InsideCardColor
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyLarge,
                color = InsideCardColor,
                lineHeight = 24.sp
            )
        }
    }
}