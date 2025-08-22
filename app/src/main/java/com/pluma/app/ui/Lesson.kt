package com.pluma.app.ui
import android.content.Context
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.IOException

@Serializable
data class Lesson(
    val title: String,
    val what: List<WhatItem>,
    val why: String,
    val how: HowSection,
    val examples: List<Example>,
    val recommended_readings: List<RecommendedReading>
){
    companion object {
        fun fromAsset(context: Context, filename: String): Lesson {
            val json = context.assets.open(filename).bufferedReader().use { it.readText() }
            return Json.decodeFromString(json)
        }
    }
}

@Serializable
data class WhatItem(
    val part: String,
    val text: String
)

@Serializable
data class HowSection(
    val tips: List<String>,
    val exercises: List<String>
)

@Serializable
data class Example(
    val description: String,
    val text: String
)

@Serializable
data class RecommendedReading(
    val title: String,
    val author: String
)

fun readJsonFromAssets(context: Context, filename: String): String {
    return context.assets.open(filename).bufferedReader().use { it.readText() }
}