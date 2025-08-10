package com.luckyfriday.vocabulary.model

data class WordData(
    val id: Int,
    var name: String,
    var meaning: String,
    var category: WordCategory
)
