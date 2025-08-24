package com.pluma.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ArchiveButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(25.dp) // tamaño del botón
            .background(
                color = Color(0xFF5D4036), // el color rosado que se ve en tu imagen
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = Icons.Default.Inbox, // usa el icono de inbox de Material Icons
            contentDescription = "Inbox",
            tint = Primary,
            modifier = Modifier.size(20.dp)
        )
    }
}
