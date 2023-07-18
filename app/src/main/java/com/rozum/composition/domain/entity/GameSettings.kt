package com.rozum.composition.domain.entity

import java.io.Serializable

data class GameSettings(
    val maxSumValue: Int,
    val minCountOfRightAnswers: Int,
    var minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
) : Serializable
