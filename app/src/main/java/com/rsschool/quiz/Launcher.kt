package com.rsschool.quiz

interface Launcher {
    fun openQuizFragment(questionNumber: Int, answers: MutableList<Int>)
    fun openFinalFragment(answers: MutableList<Int>)
    fun openTheFirstQuestion()
    fun closeApp()
}