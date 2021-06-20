package com.rsschool.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), Launcher {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openTheFirstQuestion()
    }

    override fun openQuizFragment(questionNumber: Int, answers: MutableList<Int>) {
        val fragment: Fragment = QuizFragment.newInstance(questionNumber, answers)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment).commit()
    }

    override fun openFinalFragment(answers: MutableList<Int>) {
        val fragment: Fragment = FinalFragment.newInstance(answers)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment).commit()
    }

    override fun openTheFirstQuestion() {
        val listOfQuestions = mutableListOf<Int>()
        for (i in Database.questions.indices)
            listOfQuestions.add(0)
        openQuizFragment(1, listOfQuestions)
    }

    override fun closeApp() {
        finish()
    }
}