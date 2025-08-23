package com.pluma.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Card con sombra interior uniforme en el borde.
 * El color del borde NO cambia; solo su opacidad se difumina hacia el centro.
 */
@Composable
fun PlumaCardView(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 22.dp,
    // Color único del borde/sombra (ajústalo a tu paleta)
    ringColor: Color = Color(0xFF2E1B16),     // marrón cálido
    // Relleno interior de la card
    fillColor: Color = Color(0xFF231815),
    // Definición del “falloff”: (anchoStroke → factorAlpha)
    // Más elementos = borde más suave/difuminado
    falloff: List<Pair<Dp, Float>> = listOf(
        2.dp  to 0.55f,
        6.dp  to 0.28f,
        12.dp to 0.16f,
        20.dp to 0.10f,
        28.dp to 0.06f
    ),
    contentPadding: Dp = 16.dp,
    content: @Composable () -> Unit
) {
    val shape = RoundedCornerShape(cornerRadius)
    Box(Modifier.fillMaxSize().padding(contentPadding)) {
        Box(
            modifier = modifier
                // El clip garantiza que la sombra “entre” hacia el centro,
                // no hacia fuera (inset shadow realista).
                .clip(shape)
                .background(fillColor)
                .drawBehind {
                    val r = cornerRadius.toPx()
                    val cr = CornerRadius(r, r)

                    // Trazamos múltiples strokes concéntricos con el MISMO color
                    // y alpha decreciente para simular el blur hacia dentro.
                    falloff.forEach { (wDp, alphaFactor) ->
                        drawRoundRect(
                            color = ringColor.copy(alpha = alphaFactor),
                            cornerRadius = cr,
                            style = Stroke(width = wDp.toPx())
                        )
                    }
                }
                .padding(contentPadding)
        ) {
            content()
        }
    }
}
