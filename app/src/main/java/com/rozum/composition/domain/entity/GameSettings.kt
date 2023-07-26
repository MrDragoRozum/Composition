package com.rozum.composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameSettings(
    val maxSumValue: Int,
    val minCountOfRightAnswers: Int,
    var minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
) : Parcelable {
    val minCountOfRightAnswersString get() = minCountOfRightAnswers.toString()
    val minPercentOfRightAnswersString get() = minPercentOfRightAnswers.toString()
}