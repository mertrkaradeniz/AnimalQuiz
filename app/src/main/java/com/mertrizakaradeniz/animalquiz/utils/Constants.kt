package com.mertrizakaradeniz.animalquiz.utils

import com.mertrizakaradeniz.animalquiz.R
import com.mertrizakaradeniz.animalquiz.models.Animal
import com.mertrizakaradeniz.animalquiz.models.Question

//  ANIMALS
val bear = Animal("Bear", R.drawable.ic_bear, R.raw.bear)
val bee = Animal("Bee", R.drawable.ic_bee, R.raw.bee)
val bird = Animal("Bird", R.drawable.ic_bird, R.raw.bird)
val cat = Animal("Cat", R.drawable.ic_cat, R.raw.cat)
val chicken = Animal("Chicken", R.drawable.ic_chicken, R.raw.chicken)
val cow = Animal("Cow", R.drawable.ic_cow, R.raw.cow)
val dog = Animal("Dog", R.drawable.ic_dog, R.raw.dog)
val dolphin = Animal("Dolphin", R.drawable.ic_dolphin, R.raw.dolphin)
val duck = Animal("Duck", R.drawable.ic_duck, R.raw.duck)
val eagle = Animal("Eagle", R.drawable.ic_eagle, R.raw.eagle)
val elephant = Animal("Elephant", R.drawable.ic_elephant, R.raw.elephant)
val horse = Animal("Horse", R.drawable.ic_horse, R.raw.horse)
val lion = Animal("Lion", R.drawable.ic_lion, R.raw.lion)
val monkey = Animal("Monkey", R.drawable.ic_monkey, R.raw.monkey)
val mouse = Animal("Mouse", R.drawable.ic_mouse, R.raw.mouse)
val owl = Animal("Owl", R.drawable.ic_owl, R.raw.owl)
val pig = Animal("Pig", R.drawable.ic_pig, R.raw.pig)
val seal = Animal("Seal", R.drawable.ic_seal, R.raw.seal)
val sheep = Animal("Sheep", R.drawable.ic_sheep, R.raw.sheep)
val snake = Animal("Snake", R.drawable.ic_snake, R.raw.snake)
val wolf = Animal("Wolf", R.drawable.ic_wolf, R.raw.wolf)

//  QUESTIONS
val Q1Lvl1 = Question("Find the dog", dog, dolphin, null, null, null, null, null, null, 0)
val Q2Lvl1 = Question("Find the monkey", elephant, monkey, null, null, null, null, null, null, 1)
val Q3Lvl1 = Question("Find the eagle", lion, eagle, null, null, null, null, null, null, 1)

val Q1Lvl2 = Question("Find the elephant", bear, dolphin, elephant, null, null, null, null, null, 2)
val Q2Lvl2 = Question("Find the horse", dog, horse, duck, null, null, null, null, null, 1)
val Q3Lvl2 = Question("Find the lion", lion, eagle, bee, null, null, null, null, null, 0)

val Q1Lvl3 = Question("Find the bird", cat, chicken, cow, bird, null, null, null, null, 3)
val Q2Lvl3 = Question("Find the wolf", wolf, snake, pig, seal, null, null, null, null, 0)
val Q3Lvl3 = Question("Find the mouse", owl, monkey, mouse, sheep, null, null, null, null, 2)

val Q1Lvl4 = Question("Find the sheep", bear, bird, chicken, sheep, dog, duck, null, null, 3)
val Q2Lvl4 = Question("Find the bee", bee, cat, cow, dolphin, duck, eagle, null, null, 0)
val Q3Lvl4 = Question("Find the bear", mouse, monkey, seal, owl, bear, snake, null, null, 4)

val Q1Lvl5 = Question("Find the chicken", chicken, wolf, seal, mouse, horse, duck, cow, bird, 0)
val Q2Lvl5 = Question("Find the cow", bear, cat, dog, eagle, lion, owl, sheep, cow, 7)
val Q3Lvl5 = Question("Find the cat", pig, bee, duck, dolphin, cat, elephant, snake, wolf, 4)

