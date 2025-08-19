package com.pluma.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.math.abs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LessonApp()
        }
    }
}@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonApp() {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    // Alturas variables
    val itemHeights = listOf(150.dp, 300.dp, 180.dp, 250.dp, 220.dp, 400.dp, 120.dp, 260.dp, 200.dp, 280.dp)
    val density = LocalDensity.current
    val itemHeightsPx = itemHeights.map { with(density) { it.toPx() } }

    // Precalculamos posiciones absolutas
    val itemOffsetsPx = itemHeightsPx.runningFold(0f) { acc, h -> acc + h }

    // Snap target (segundo elemento)
    val snapIndex = 1
    val snapTarget = itemOffsetsPx[snapIndex]

    // Progreso del scroll hasta el snap del item 1
    val progress = (scrollState.value.toFloat() / snapTarget).coerceIn(0f, 1f)

    // Altura gradual: de 90.dp a 120.dp
    val minHeight = 90.dp
    val maxHeight = 120.dp
    val topBarHeight = lerp(minHeight, maxHeight, progress)

    // Mostrar "¿Qué tal?" solo si hemos llegado al snapIndex
    val showTopBarDetails = scrollState.value >= snapTarget.toInt()

    // Snap connection
    val snapConnection = remember {
        object : NestedScrollConnection {
            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                val currentOffset = scrollState.value.toFloat()
                val snapThreshold = itemHeightsPx[snapIndex] * 0.4f

                if (abs(currentOffset - snapTarget) < snapThreshold) {
                    coroutineScope.launch {
                        scrollState.animateScrollTo(snapTarget.toInt())
                    }
                }
                return super.onPostFling(consumed, available)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(topBarHeight),
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Hola")
                        if (showTopBarDetails) {
                            Spacer(Modifier.width(8.dp))
                            Text("¿Qué tal?")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .nestedScroll(snapConnection)
                .verticalScroll(scrollState)
        ) {
            itemHeights.forEachIndexed { index, height ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height)
                        .background(if (index == snapIndex) Color.Red else Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Elemento $index (${height.value.toInt()}dp)",
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            }
        }
    }
}
