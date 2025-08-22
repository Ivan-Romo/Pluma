package com.pluma.app.ui

import android.graphics.RenderEffect as AndroidRenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Timer
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
import com.pluma.app.RalewayFamily

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
    val nameGlowPx = with(density) { 1.dp.toPx() }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(circleSize)
                .drawBehind {
                    val r = this.size.minDimension / 2f
                    val c = center


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

                }
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Your new lesson is here,",
                color = Color(0xFFEFCFB9),
                fontSize = 18.sp,
                letterSpacing = 0.2.sp,
                fontFamily = RalewayFamily,
                fontWeight = FontWeight.Bold,
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
                        alpha = 0.5f
                    }
                } else Modifier

            // Nombre con glow
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = name,
                    color = neon,
                    fontSize = 22.sp,
                    fontFamily = RalewayFamily,
                    fontWeight = FontWeight.Bold,
                    modifier = blurLayer,
                    style = MaterialTheme.typography.titleLarge.copy(
                        shadow = Shadow(
                            color = Primary.copy(alpha = 0.01f),
                            offset = Offset.Zero,
                            blurRadius = nameGlowPx
                        )
                    )
                )
                Text(
                    text = name,
                    fontFamily = RalewayFamily,
                    fontWeight = FontWeight.Bold,
                    color = Primary,
                    fontSize = 22.sp,

                    )


            }

            Spacer(Modifier.height(18.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    imageVector = Icons.Outlined.Timer,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(14.dp)
                )

                Spacer(Modifier.width(8.dp))


                Text(
                    text = "Next in: ",
                    color = InsideCardColor,
                    fontFamily = RalewayFamily,
                    fontSize = 15.sp,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        shadow = Shadow(
                            color = neon.copy(alpha = 0.45f),
                            offset = Offset.Zero,
                            blurRadius = textGlowPx
                        )
                    )
                )
                Text(
                    text = "$nextIn ",
                    color = Primary,
                    fontFamily = RalewayFamily,
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