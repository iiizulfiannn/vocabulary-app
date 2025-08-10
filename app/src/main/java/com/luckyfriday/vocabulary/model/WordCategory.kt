package com.luckyfriday.vocabulary.model

import androidx.annotation.ColorRes
import com.luckyfriday.vocabulary.R

enum class WordCategory(val title: String, @ColorRes val color: Int) {
    ALL_CATEGORY("All Categories", R.color.black),
    ADJECTIVE("Adjective", R.color.green),
    PREPOSITION("Adverb", R.color.red),
    VERB("Noun", R.color.yellow),
    NOUN("Verb", R.color.purple)
}

enum class ListWordState{
    NORMAL,
    REMOVED
}