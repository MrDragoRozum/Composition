package com.rozum.composition.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.rozum.composition.R

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, count: Int) {
    textView.text = String.format(textView.context.getString(R.string.required_score), count)
}

@BindingAdapter("score")
fun bindCountOfRightAnswers(textView: TextView, score: Int) {
    textView.text = String.format(textView.context.getString(R.string.score_answers), score)
}

@BindingAdapter("requiredPercentage")
fun bindMinPercentOfRightAnswers(textView: TextView, percent: Int) {
    textView.text = String.format(textView.context.getString(R.string.required_percentage), percent)
}

@BindingAdapter("scorePercentage")
fun bindScorePercentage(textView: TextView, percent: Int) {
    textView.text = String.format(textView.context.getString(R.string.score_percentage), percent)
}

@BindingAdapter("imageSmile")
fun bindSetImageSmile(imageView: ImageView, isWinner: Boolean) {
    imageView.setImageResource(getSmileResId(isWinner))
}

private fun getSmileResId(isWinner: Boolean) = if (isWinner) {
    R.drawable.ic_smile
} else {
    R.drawable.ic_sad
}