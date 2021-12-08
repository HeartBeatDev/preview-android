package com.preview.android.domain.usecases.config

import com.preview.android.domain.models.config.Config
import com.preview.android.domain.repositories.ConfigRepository
import com.preview.android.domain.usecases.AbstractUseCase
import javax.inject.Inject

class GetConfigUseCase @Inject constructor(
    private val repository: ConfigRepository
): AbstractUseCase<Nothing, Config>() {

    override suspend fun execute(parameters: Nothing?): Config? {
        return repository.getConfig()
    }
}