package com.pluma.app.ui

import android.graphics.Shader
import android.os.Build
import android.graphics.RenderEffect as AndroidRenderEffect
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.pluma.app.RalewayFamily
import kotlin.math.PI
import kotlin.math.sin

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

    // -------- Animaciones sutiles del halo --------
    val infinite = rememberInfiniteTransition(label = "halo")

    // “Respiración” del halo (radio y alpha)
    val pulse by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    // Pequeño drift del centro (2dp máx) para que no quede 100% rígido
    val driftT by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "drift"
    )

    // Ondita suave que se expande y se desvanece
    val ripple by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(5200, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ripple"
    )

    // Blur “vivo” (solo S+)
    val blurRadius by infinite.animateFloat(
        initialValue = 12f,
        targetValue = 16f,
        animationSpec = infiniteRepeatable(
            animation = tween(3400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blur"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        // --- Círculo animado ---
        Box(
            modifier = Modifier
                .size(circleSize)
                .graphicsLayer {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        renderEffect = AndroidRenderEffect
                            .createBlurEffect(blurRadius, blurRadius, Shader.TileMode.DECAL)
                            .asComposeRenderEffect()
                    }
                }
                .drawWithCache {
                    val r = this.size.minDimension / 2f
                    val c = this.size.center

                    // Suavísimo “breathing” del tamaño del halo
                    val scale = lerp(0.985f, 1.02f, pulse)

                    // Micro-drift del centro para evitar rigidez (≈2dp)
                    val driftPx = with(density) { 2.dp.toPx() }
                    val dx = (sin(driftT * 2 * PI).toFloat()) * driftPx
                    // Fase desfasada en Y para que no sea lineal
                    val dy = (sin(driftT * 2 * PI + PI / 2).toFloat()) * (driftPx * 0.5f)
                    val cc = Offset(c.x + dx, c.y + dy)

                    val ringAlpha = lerp(0.1f, 0.20f, pulse)

                    // Pincel del halo
                    val haloBrush = Brush.radialGradient(
                        0.30f to Color.Transparent,
                        0.50f to neonSoft.copy(alpha = 0.04f),
                        0.715f to neon.copy(alpha = ringAlpha), // pico fino
                        0.75f to neonSoft.copy(alpha = 0.05f),
                        0.82f to Color.Transparent,
                        center = cc,
                        radius = r * scale
                    )

                    // Parámetros de la ondita/ripple
                    val rippleRadius = lerp(r * 0.55f, r * 0.95f, ripple)
                    val rippleAlpha = lerp(0.03f, 0f, ripple)
                    val rippleStroke = with(density) { 1.5.dp.toPx() }

                    onDrawBehind {
                        // Halo anular
                        drawCircle(
                            brush = haloBrush,
                            radius = r * scale,
                            center = cc,
                            blendMode = BlendMode.Plus
                        )

                        // Ripple sutil (muy poca alpha)
                        drawCircle(
                            color = neon.copy(alpha = rippleAlpha),
                            radius = rippleRadius,
                            center = cc,
                            style = Stroke(width = rippleStroke),
                            blendMode = BlendMode.Plus
                        )
                    }
                }
        )

        // --- Contenido ---
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
                        color = Color(0xFFFC7747).copy(alpha = 0.35f),
                        offset = Offset.Zero,
                        blurRadius = textGlowPx
                    )
                )
            )

            Spacer(Modifier.height(10.dp))

            val blurLayer =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    Modifier.graphicsLayer {
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
                    color = Color(0xFFFC7747),
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
                )
                Text(
                    text = "$nextIn ",
                    color = Primary,
                    fontFamily = RalewayFamily,
                    fontSize = 15.sp,
                )
            }
        }
    }
}
