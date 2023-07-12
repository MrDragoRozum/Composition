package com.rozum.composition.data

import com.rozum.composition.domain.entity.GameSettings
import com.rozum.composition.domain.entity.Level
import com.rozum.composition.domain.entity.Question
import com.rozum.composition.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1
    override fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val rightAnswer = sum - visibleNumber
        val options = mutableSetOf<Int>()
        options.add(rightAnswer)
        val from = max(rightAnswer - countOfOptions, MIN_ANSWER_VALUE)
        val to = min(maxSumValue, rightAnswer + countOfOptions)
        while (options.size < countOfOptions) options.add(Random.nextInt(from, to))
        return Question(sum, visibleNumber, options.toList())
    }

    override fun getGameSettings(level: Level) = when (level) {
        Level.TEST -> {
            GameSettings(
                10,
                3,
                50,
                8
            )
        }

        Level.EASY -> {
            GameSettings(
                10,
                10,
                70,
                60
            )
        }

        Level.MEDIUM -> {
            GameSettings(
                20,
                20,
                80,
                40
            )
        }

        Level.HARD -> {
            GameSettings(
                30,
                30,
                90,
                40
            )
        }
    }
}