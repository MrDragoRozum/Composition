package com.rozum.composition.domain.repository

import com.rozum.composition.domain.entity.GameSettings
import com.rozum.composition.domain.entity.Level
import com.rozum.composition.domain.entity.Question

interface GameRepository {

    fun generateQuestion(maxSumValue: Int, countOfOptions: Int): Question
    fun getGameSettings(level: Level): GameSettings
}