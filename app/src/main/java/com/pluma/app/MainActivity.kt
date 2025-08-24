package com.pluma.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.LinearProgressIndicator

import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.pluma.app.ui.CardColor
import com.pluma.app.ui.GlowingCircleLesson
import com.pluma.app.ui.Lesson
import com.pluma.app.ui.LessonView
import com.pluma.app.ui.ArchiveButton
import com.pluma.app.ui.theme.BackgroundColor
import com.pluma.app.ui.theme.InsideCardColor
import com.pluma.app.ui.theme.Primary
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LessonApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonApp() {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    // Alturas variables
    val itemHeights = listOf(410.dp, 300.dp, 180.dp, 250.dp, 220.dp, 400.dp, 120.dp, 260.dp, 200.dp, 280.dp)
    val density = LocalDensity.current
    val itemHeightsPx = itemHeights.map { with(density) { it.toPx() } }

    // Precalculamos posiciones absolutas
    val itemOffsetsPx = itemHeightsPx.runningFold(0f) { acc, h -> acc + h }

    // Snap target (segundo elemento)
    val snapIndex = 1
    val snapTarget = itemOffsetsPx[snapIndex]

    // Progreso del scroll hasta el snap del item 1 (para animar altura de la AppBar)
    val snapProgress = (scrollState.value.toFloat() / snapTarget).coerceIn(0f, 1f)

    // Altura gradual: de 90.dp a 120.dp
    val minHeight = 100.dp
    val maxHeight = 120.dp
    val topBarHeight = lerp(minHeight, maxHeight, snapProgress)

    val showTopBarDetails = scrollState.value >= snapTarget.toInt()

    val overallProgress = if (scrollState.maxValue > 0) {
        ((scrollState.value.toFloat() - snapTarget) /
                (scrollState.maxValue.toFloat() - snapTarget))
            .coerceIn(0f, 1f)
    } else 0f

    val lesson: Lesson = Lesson.fromAsset(LocalContext.current, "file1.json")

    // Snap connection
    val snapConnection = remember {
        object : NestedScrollConnection {
            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                val currentOffset = scrollState.value.toFloat()
                val snapThreshold = itemHeightsPx[snapIndex] * 0.6f

                if (kotlin.math.abs(currentOffset - snapTarget) < snapThreshold) {
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
                    Column(modifier = Modifier.fillMaxSize()) {
                        Spacer(Modifier.height(20.dp))
                        Row(
                            Modifier.fillMaxWidth()
                                .height(40.dp)
                                .padding(5.dp),
                            Arrangement.End
                        ) {

                            ArchiveButton {
                                
                            }
                            Spacer(Modifier.width(25.dp))

                        }
                        if (showTopBarDetails) {
                            Spacer(Modifier.height(15.dp))
                            Row(Modifier
                                .fillMaxWidth()

                                .padding(start = 2.dp, end = 20.dp), Arrangement.SpaceAround) {
                                LinearProgressIndicator(
                                    color = Primary,
                                    trackColor = CardColor,
                                    progress = { overallProgress },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(4.dp)
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundColor,
                    titleContentColor = InsideCardColor
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .nestedScroll(snapConnection)
                .verticalScroll(scrollState)
                .background(BackgroundColor)
        ) {
            GlowingCircleLesson("Iván", "23:59", circleSize = 380.dp)
            LessonView(lesson)
        }
    }
}
