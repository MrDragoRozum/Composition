package com.rozum.composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(
    val winner: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestion: Int,
    val gameSettings: GameSettings
) : Parcelable {
    val percentOfRightAnswer
        get() =
            if (countOfQuestion != 0)
                (countOfRightAnswers / countOfQuestion.toDouble() * 100).toInt()
            else
                0
}
