package com.rsschool.quiz

class Database {
    class Question(val question: String, val answerOptions: Array<Pair<String, Boolean>>, val theme: Int)
    companion object {
        val questions = arrayOf(
                Question(question = "Name an example of a high-level language.", answerOptions = arrayOf(
                        "Hindi language" to false,
                        "None" to false,
                        "Java" to true,
                        "Assembler" to false,
                        "French language" to false,
                ), theme = R.style.Theme_Quiz_First),
                Question(question = "What is machine language?", answerOptions = arrayOf(
                        "A language used by cars" to false,
                        "Machine language is made of 1's and 0's" to true,
                        "Machine language is made of 7's and 8's" to false,
                        "A language that only understands plain english" to false,
                        "None of the above" to false,
                ), theme = R.style.Theme_Quiz_Second),
                Question(question = "What is the definition of a variable?", answerOptions = arrayOf(
                        "Variables store data for the program to work with. A variable could contain text or numerical values" to true,
                        "Variables allow decisions to be made in a program" to false,
                        "Variables receive data and messages enabling two or more computers to communicate" to false,
                        "All of these" to false,
                        "None of these" to false,
                ), theme = R.style.Theme_Quiz_Third),
                Question(question = "What's the difference between high level and low-level languages?", answerOptions = arrayOf(
                        "High level is written in capitals and low level is written in lower case" to false,
                        "High level is hard and low level is easy" to false,
                        "High level language is used by software and low level languages is used by hardware" to false,
                        "Low level languages is made of 1's and 0's and high level language is BASIC, COBOL, FORTRAN, C++, etc." to true,
                        "None of the above" to false,
                ), theme = R.style.Theme_Quiz_Fourth),
                Question(question = "What are some examples of high-level languages?", answerOptions = arrayOf(
                        "BASIC" to false,
                        "COBOL" to false,
                        "FORTRAN" to false,
                        "C++" to false,
                        "All of these" to true,
                ), theme = R.style.Theme_Quiz_Fifth),
        )
    }
}