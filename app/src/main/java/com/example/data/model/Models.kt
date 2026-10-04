package com.example.data.model

data class WordItem(
    val id: String,
    val bangla: String,
    val english: String,
    val pronunciation: String,
    val category: String,
    val exampleBangla: String = "",
    val exampleEnglish: String = "",
    val funFact: String = ""
)

data class AlphabetItem(
    val char: String,
    val romanization: String,
    val soundDescription: String,
    val exampleWord: String,
    val exampleMeaning: String,
    val isVowel: Boolean
)

data class QuizQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val targetWordBangla: String = ""
)

data class BlessingQuote(
    val id: Int,
    val sacredTitle: String,
    val mantraOrVerse: String,
    val meaning: String,
    val blessingNote: String
)

data class PhraseItem(
    val id: String,
    val bangla: String,
    val pronunciation: String,
    val english: String,
    val situation: String
)
