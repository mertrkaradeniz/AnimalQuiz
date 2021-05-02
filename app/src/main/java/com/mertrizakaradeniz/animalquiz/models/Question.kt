package com.mertrizakaradeniz.animalquiz.models

data class Question(
    val questionText: String? = null,
    val animalOne: Animal? = null,
    val animalTwo: Animal? = null,
    val animalThree: Animal? = null,
    val animalFour: Animal? = null,
    val animalFive: Animal? = null,
    val animalSix: Animal? = null,
    val animalSeven: Animal? = null,
    val animalEight: Animal? = null,
    val correctAnswer: Int? = null
)