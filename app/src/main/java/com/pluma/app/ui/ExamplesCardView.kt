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
fun ExamplesCardView(lesson: Lesson) {
    PlumaCardView {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Examples",
                fontFamily = RalewayFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp,

                color = InsideCardColor
            )

            for (i in lesson.examples.indices){

                Text(
                    text = lesson.examples[i].description,
                    fontFamily = RalewayFamily,
                    fontSize = 24.sp,
                    color = Primary
                )
                Text(
                    text = lesson.examples[i].text,
                    fontFamily = RalewayFamily,
                    style = MaterialTheme.typography.bodyLarge,
                    color = InsideCardColor,
                    lineHeight = 24.sp
                )
            }




        }
    }
}