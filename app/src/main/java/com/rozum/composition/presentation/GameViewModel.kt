package com.rozum.composition.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rozum.composition.R
import com.rozum.composition.data.GameRepositoryImpl
import com.rozum.composition.domain.entity.GameResult
import com.rozum.composition.domain.entity.GameSettings
import com.rozum.composition.domain.entity.Level
import com.rozum.composition.domain.entity.Question
import com.rozum.composition.domain.repository.GameRepository
import com.rozum.composition.domain.usecases.GenerateQuestionUseCases
import com.rozum.composition.domain.usecases.GetGameSettingsUseCase

class GameViewModel(
    private val context: Application,
    private val level: Level
) : ViewModel() {
    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String> get() = _formattedTime

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question> get() = _question

    private val _progressAnswer = MutableLiveData<String>()
    val progressAnswer: LiveData<String> get() = _progressAnswer

    private val _percentOfRightAnswer = MutableLiveData<Int>()
    val percentOfRightAnswer: LiveData<Int> get() = _percentOfRightAnswer

    private val _enoughCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean> get() = _enoughCount

    private val _enoughPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean> get() = _enoughPercent
    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int> get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult> get() = _gameResult

    private val gameRepository: GameRepository = GameRepositoryImpl
    private val generateQuestion = GenerateQuestionUseCases(gameRepository)
    private val getGameSettings = GetGameSettingsUseCase(gameRepository)

    private var countOfRightAnswer = 0
    private var countOfQuestion = 0

    private lateinit var gameSettings: GameSettings

    private var timer: CountDownTimer? = null


    init {
        startGame()
    }

    private fun startGame() {
        getGameSettings()
        startTimer()
        generateQuestion()
        updateProgress()
    }

    fun chooseAnswer(answer: Int) {
        checkAnswer(answer)
        updateProgress()
        generateQuestion()
    }

    private fun getGameSettings() {
        gameSettings = getGameSettings.invoke(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswers
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLS_IN_SECOND,
            MILLS_IN_SECOND
        ) {
            override fun onTick(millisUntilFinished: Long) {
                val millis = millisUntilFinished + MILLS_IN_SECOND
                _formattedTime.value = formattedTime(millis)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun formattedTime(millisUntilFinished: Long): String {
        val second = millisUntilFinished / MILLS_IN_SECOND
        val minutes = second / SECOND_IN_MINUTES
        val leftSecond = second - (minutes * SECOND_IN_MINUTES)
        return String.format("%02d:%02d", minutes, leftSecond)
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            enoughCount.value == true && enoughPercent.value == true,
            countOfRightAnswer,
            countOfQuestion,
            gameSettings
        )
    }

    private fun generateQuestion() {
        _question.value = generateQuestion(gameSettings.maxSumValue)
    }


    private fun checkAnswer(answer: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (answer == rightAnswer) {
            countOfRightAnswer++
        }
        countOfQuestion++
    }

    private fun updateProgress() {
        val percent = calculatePercent()
        _percentOfRightAnswer.value = percent
        _progressAnswer.value = String.format(
            context.resources.getString(
                R.string.progress_answers
            ),
            countOfRightAnswer,
            gameSettings.minCountOfRightAnswers
        )
        _enoughCount.value =
            countOfRightAnswer >= gameSettings.minCountOfRightAnswers
        _enoughPercent.value = percent >= gameSettings.minPercentOfRightAnswers
    }

    private fun calculatePercent() =
        if (countOfQuestion != 0) (countOfRightAnswer / countOfQuestion.toDouble() * 100).toInt()
        else 0

    companion object {
        private const val MILLS_IN_SECOND = 1000L
        private const val SECOND_IN_MINUTES = 60
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

}