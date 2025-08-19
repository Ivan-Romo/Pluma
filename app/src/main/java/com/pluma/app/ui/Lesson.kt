package com.pluma.app.ui

data class NarratorInfo(
    val title: String,
    val what: List<WhatItem>,
    val why: String,
    val how: HowSection,
    val examples: List<Example>,
    val recommended_readings: List<RecommendedReading>
)

data class WhatItem(
    val part: String,
    val text: String
)

data class HowSection(
    val tips: List<String>,
    val exercises: List<String>
)

data class Example(
    val description: String,
    val text: String
)

data class RecommendedReading(
    val title: String,
    val author: String
)
