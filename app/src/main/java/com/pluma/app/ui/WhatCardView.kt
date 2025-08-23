package com.pluma.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pluma.app.ui.theme.InsideCardColor
import com.pluma.app.ui.theme.Primary


import com.pluma.app.RalewayFamily

@Composable
fun WhatCardView(lesson: Lesson){
    PlumaCardView {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "What is ${lesson.title.lowercase()}?",
                fontFamily = RalewayFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp,

                color = InsideCardColor
            )

            for (i in lesson.what.indices) {
                Text(
                    text = lesson.what[i].part,
                    fontFamily = RalewayFamily,
                    fontSize = 24.sp,
                    color = Primary
                )

                Text(
                    text = lesson.what[i].text,
                    fontFamily = RalewayFamily,
                    style = MaterialTheme.typography.bodyLarge,
                    color = InsideCardColor,
                    lineHeight = 24.sp
                )
            }
        }
    }
}