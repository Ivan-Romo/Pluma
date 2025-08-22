package com.pluma.app.ui

import android.graphics.RenderEffect as AndroidRenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GlowingCircleLesson(
    name: String,
    nextIn: String,
    modifier: Modifier = Modifier,
    circleSize: Dp = 360.dp
) {
    val diskDark = Color(0xFF22110D)
    val neon = Color(0xFFFC7747)
    val neonSoft = Color(0xFFFCA26F)

    val density = LocalDensity.current
    val textGlowPx = with(density) { 18.dp.toPx() }
    val nameGlowPx = with(density) { 24.dp.toPx() }

    Box(
        modifier = modifier.fillMaxSize().padding(top = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(circleSize)
                .drawBehind {
                    val r = this.size.minDimension / 2f
                    val c = center

                    // Vignette exterior
//                    drawCircle(
//                        brush = Brush.radialGradient(
//                            colors = listOf(Color.Transparent, BackgroundColor),
//                            center = c, radius = r * 1.25f
//                        ),
//                        radius = r * 1.25f, center = c
//                    )

                    // Disco base
                  //  drawCircle(color = diskDark, radius = r * 0.995f, center = c)

//                    // Profundidad interior
//                    drawCircle(
//                        brush = Brush.radialGradient(
//                            colors = listOf(Color(0xFF2B1410), diskDark),
//                            center = c, radius = r * 0.98f
//                        ),
//                        radius = r * 0.98f, center = c
//                    )

                    // HALO ANULAR (usar pares Float–Color)
                    drawCircle(
                        brush = Brush.radialGradient(
                            0.3f to Color.Transparent,
                            //0.3f to neonSoft.copy(alpha = 0.05f),
                            0.50f to neonSoft.copy(alpha = 0.05f),
                            0.715f to neon.copy(alpha = 0.09f),   // pico fino
                            0.75f to neonSoft.copy(alpha = 0.05f),
                            0.80f to Color.Transparent,
                            center = c, radius = r
                        ),
                        radius = r, center = c, blendMode = BlendMode.Plus
                    )

//                    // Glow interior para el texto
//                    drawCircle(
//                        brush = Brush.radialGradient(
//                            colors = listOf(neonSoft.copy(alpha = 0.16f), Color.Transparent),
//                            center = c, radius = r * 0.36f
//                        ),
//                        radius = r * 0.36f, center = c, blendMode = BlendMode.Plus
//                    )

                    // Aura exterior
//                    drawCircle(
//                        brush = Brush.radialGradient(
//                            colors = listOf(neon.copy(alpha = 0.12f), Color.Transparent),
//                            center = c, radius = r * 1.35f
//                        ),
//                        radius = r * 1.35f, center = c, blendMode = BlendMode.Plus
//                    )
                }
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Your new lesson is here,",
                color = Color(0xFFEFCFB9),
                fontSize = 18.sp,
                letterSpacing = 0.2.sp,
                style = MaterialTheme.typography.titleLarge.copy(
                    shadow = Shadow(
                        color = neon.copy(alpha = 0.35f),
                        offset = Offset.Zero,
                        blurRadius = textGlowPx
                    )
                )
            )

            Spacer(Modifier.height(10.dp))

            val blurLayer =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    Modifier.graphicsLayer {
                        // Android RenderEffect -> Compose RenderEffect
                        renderEffect = AndroidRenderEffect
                            .createBlurEffect(22f, 22f, Shader.TileMode.DECAL)
                            .asComposeRenderEffect()
                        alpha = 0.9f
                    }
                } else Modifier

            // Nombre con glow
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = name,
                    color = neon,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = blurLayer,
                    style = MaterialTheme.typography.titleLarge.copy(
                        shadow = Shadow(
                            color = neon.copy(alpha = 0.9f),
                            offset = Offset.Zero,
                            blurRadius = nameGlowPx
                        )
                    )
                )
                Text(
                    text = name,
                    color = Color(0xFFFFE3D4),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(18.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(22.dp).background(neon, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Timer,
                        contentDescription = null,
                        tint = Color(0xFF2A120E),
                        modifier = Modifier.size(14.dp)
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Next in: $nextIn",
                    color = neon,
                    fontSize = 15.sp,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        shadow = Shadow(
                            color = neon.copy(alpha = 0.45f),
                            offset = Offset.Zero,
                            blurRadius = textGlowPx
                        )
                    )
                )
            }
        }
    }
}