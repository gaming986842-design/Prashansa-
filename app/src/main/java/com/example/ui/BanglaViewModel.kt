package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.AppDatabase
import com.example.data.db.QuizScoreEntity
import com.example.data.db.SavedWordEntity
import com.example.data.model.AlphabetItem
import com.example.data.model.BlessingQuote
import com.example.data.model.PhraseItem
import com.example.data.model.QuizQuestion
import com.example.data.model.WordItem
import com.example.data.repository.BanglaRepository
import com.example.ui.util.TtsHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppTab(val title: String) {
    VOCAB("Vocabulary"),
    QUIZ("Quiz"),
    ALPHABET("Alphabet"),
    PHRASES("Phrases"),
    STATS("Progress")
}

data class QuizUiState(
    val questions: List<QuizQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val selectedIndex: Int? = null,
    val isAnswered: Boolean = false,
    val isCorrect: Boolean = false,
    val score: Int = 0,
    val streak: Int = 0,
    val isCompleted: Boolean = false
)

class BanglaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BanglaRepository
    private val ttsHelper: TtsHelper

    init {
        val db = AppDatabase.getDatabase(application)
        repository = BanglaRepository(db.banglaDao())
        ttsHelper = TtsHelper(application)
    }

    private val _currentTab = MutableStateFlow(AppTab.VOCAB)
    val currentTab: StateFlow<AppTab> = _currentTab.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val allWords = repository.getAllWords()
    val allCategories = listOf("All", "Greetings", "Essentials", "Emotion", "Nature & Food", "People", "Numbers")

    val savedWordsState: StateFlow<Map<String, SavedWordEntity>> = repository.savedWords
        .combine(MutableStateFlow(Unit)) { list, _ ->
            list.associateBy { it.wordId }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    val quizScoresState: StateFlow<List<QuizScoreEntity>> = repository.quizScores
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val learnedCount: StateFlow<Int> = repository.learnedCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val favoriteCount: StateFlow<Int> = repository.favoriteCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // Quiz State
    private val _quizState = MutableStateFlow(QuizUiState())
    val quizState: StateFlow<QuizUiState> = _quizState.asStateFlow()

    // Blessings & Alphabet
    val blessings: List<BlessingQuote> = repository.getBlessingQuotes()
    val alphabet: List<AlphabetItem> = repository.getAlphabetList()
    val phrases: List<PhraseItem> = repository.getPhrases()

    init {
        resetQuiz()
    }

    fun selectTab(tab: AppTab) {
        _currentTab.value = tab
    }

    fun setCategory(category: String) {
        _selectedCategory.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun getFilteredWords(): List<WordItem> {
        val cat = _selectedCategory.value
        val query = _searchQuery.value.trim().lowercase()

        return allWords.filter { word ->
            val matchesCategory = (cat == "All" || word.category.equals(cat, ignoreCase = true))
            val matchesSearch = query.isEmpty() ||
                    word.bangla.lowercase().contains(query) ||
                    word.english.lowercase().contains(query) ||
                    word.pronunciation.lowercase().contains(query)
            matchesCategory && matchesSearch
        }
    }

    fun toggleFavorite(wordId: String) {
        viewModelScope.launch {
            val isFav = savedWordsState.value[wordId]?.isFavorite ?: false
            repository.toggleFavorite(wordId, isFav)
        }
    }

    fun toggleLearned(wordId: String) {
        viewModelScope.launch {
            val isLearned = savedWordsState.value[wordId]?.isLearned ?: false
            repository.toggleLearned(wordId, isLearned)
        }
    }

    fun speak(text: String, phonetic: String = "") {
        ttsHelper.speak(text, phonetic)
    }

    // Quiz Actions
    fun onSelectQuizOption(index: Int) {
        val state = _quizState.value
        if (state.isAnswered || state.questions.isEmpty()) return

        val currentQ = state.questions[state.currentIndex]
        val isCorrect = (index == currentQ.correctIndex)
        val newScore = if (isCorrect) state.score + 1 else state.score
        val newStreak = if (isCorrect) state.streak + 1 else 0

        _quizState.value = state.copy(
            selectedIndex = index,
            isAnswered = true,
            isCorrect = isCorrect,
            score = newScore,
            streak = newStreak
        )

        // Pronounce the word if correct
        if (currentQ.targetWordBangla.isNotEmpty()) {
            ttsHelper.speak(currentQ.targetWordBangla)
        }
    }

    fun nextQuizQuestion() {
        val state = _quizState.value
        if (!state.isAnswered) return

        if (state.currentIndex < state.questions.size - 1) {
            _quizState.value = state.copy(
                currentIndex = state.currentIndex + 1,
                selectedIndex = null,
                isAnswered = false,
                isCorrect = false
            )
        } else {
            // Finished
            _quizState.value = state.copy(isCompleted = true)
            // Save to room
            viewModelScope.launch {
                repository.saveQuizScore(state.score, state.questions.size)
            }
        }
    }

    fun resetQuiz() {
        val allQ = repository.getQuizQuestions().shuffled()
        _quizState.value = QuizUiState(
            questions = allQ,
            currentIndex = 0,
            selectedIndex = null,
            isAnswered = false,
            isCorrect = false,
            score = 0,
            streak = 0,
            isCompleted = false
        )
    }

    override fun onCleared() {
        super.onCleared()
        ttsHelper.shutdown()
    }
}
