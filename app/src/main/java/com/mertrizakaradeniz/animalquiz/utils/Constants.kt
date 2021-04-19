package com.mertrizakaradeniz.animalquiz.utils

import com.mertrizakaradeniz.animalquiz.R
import com.mertrizakaradeniz.animalquiz.models.Animal
import com.mertrizakaradeniz.animalquiz.models.Question

//  ANIMALS
val dog = Animal("Dog", R.drawable.ic_dog, R.raw.dog)
val dolphin = Animal("Dolphin", R.drawable.ic_dolphin, R.raw.dolphin)
val duck = Animal("Duck", R.drawable.ic_duck, R.raw.duck)
val eagle = Animal("Eagle", R.drawable.ic_eagle, R.raw.eagle)
val elephant = Animal("Elephant", R.drawable.ic_elephant, R.raw.elephant)
val horse = Animal("Horse", R.drawable.ic_horse, R.raw.horse)
val lion = Animal("Lion", R.drawable.ic_lion, R.raw.lion)
val monkey = Animal("Monkey", R.drawable.ic_monkey, R.raw.monkey)

//  QUESTIONS
val Q1Lvl1 = Question("Find the dog", dog, dolphin, null, null, null, null, null, null, 0)
val Q2Lvl1 = Question("Find the monkey", elephant, monkey, null, null, null, null, null, null, 1)
val Q3Lvl1 = Question("Find the eagle", lion, eagle, null, null, null, null, null, null, 1)

val Q1Lvl2 = Question("Find the elephant", lion, dolphin, elephant, null, null, null, null, null, 2)
val Q2Lvl2 = Question("Find the horse", dog, horse, duck, null, null, null, null, null, 1)
val Q3Lvl2 = Question("Find the lion", lion, eagle, dolphin, null, null, null, null, null, 0)

val Q1Lvl3 = Question("Find the dog", dolphin, duck, eagle, dog, null, null, null, null, 3)
val Q2Lvl3 = Question("Find the dolphin", dolphin, monkey, lion, horse, null, null, null, null, 0)
val Q3Lvl3 = Question("Find the eagle", dolphin, monkey, lion, eagle, null, null, null, null, 3)

val Q1Lvl4 = Question("Find the dog", dolphin, duck, eagle, dog, horse, monkey, null, null, 3)
val Q2Lvl4 = Question("Find the dolphin", dolphin, monkey, lion, horse, duck, elephant, null, null, 0)
val Q3Lvl4 = Question("Find the eagle", dolphin, monkey, lion, eagle, horse, duck, null, null, 3)

val Q1Lvl5 = Question("Find the dog", dog, dolphin, duck, eagle, elephant, horse, lion, monkey, 0)
val Q2Lvl5 = Question("Find the dog", dog, dolphin, duck, eagle, elephant, horse, lion, monkey, 0)
val Q3Lvl5 = Question("Find the dog", dog, dolphin, duck, eagle, elephant, horse, lion, monkey, 0)

