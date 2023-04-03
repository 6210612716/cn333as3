package com.example.multigame.ui.quizgame

data class GameUiState(
    val currentQuestion: QuizQuestion = QuizQuestion("", listOf(), ""),
    val currentQuestionCount: Int = 1,
    val score: Int = 0,
    val isUserAnswerWrong: Boolean = false,
    val isGameOver: Boolean = false,
    val textStatus: String = "",
)
