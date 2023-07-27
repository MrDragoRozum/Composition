package com.rozum.composition.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.rozum.composition.R

fun interface OnOptionClickListener {
    fun onOptionClick(option: Int)
}

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
fun bindSetImageSmile(imageView: ImageView, winner: Boolean) {
    imageView.setImageResource(getSmileResId(winner))
}

private fun getSmileResId(winner: Boolean) = if (winner) {
    R.drawable.ic_smile
} else {
    R.drawable.ic_sad
}

@BindingAdapter("percentOfRightAnswerWithAnimation")
fun bindPercentOfRightAnswer(progressBar: ProgressBar, percent: Int) {
    progressBar.setProgress(percent, true)
}

@BindingAdapter("enoughPercent")
fun bindEnoughPercent(progressBar: ProgressBar, percent: Boolean) {
    progressBar.progressTintList =
        ColorStateList.valueOf(getColor(progressBar.context, percent))
}

@BindingAdapter("enoughCount")
fun bindEnoughCount(textView: TextView, count: Boolean) {
    textView.setTextColor(getColor(textView.context, count))
}

private fun getColor(context: Context, isColor: Boolean) = if (isColor) ContextCompat.getColor(
    context,
    android.R.color.holo_green_light
)
else ContextCompat.getColor(
    context,
    android.R.color.holo_red_light
)

@BindingAdapter("numberAsText")
fun bindNumberAsText(textView: TextView, number: Int) {
    textView.text = number.toString()
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionClickListener(textView: TextView, clickListener: OnOptionClickListener) {
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}
