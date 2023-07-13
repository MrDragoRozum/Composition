package com.rozum.composition.domain.usecases

import com.rozum.composition.domain.repository.GameRepository

class GenerateQuestionUseCases(private val repository: GameRepository) {
    operator fun invoke(maxSumValue: Int) =
        repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)

    private companion object {
        private const val COUNT_OF_OPTIONS = 6
    }
}