package com.rozum.composition.domain.usecases

import com.rozum.composition.domain.entity.Level
import com.rozum.composition.domain.repository.GameRepository

class GetGameSettingsUseCase(private val repository: GameRepository) {
    operator fun invoke(level: Level) = repository.getGameSettings(level)
}