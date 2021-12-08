package com.preview.android.domain.usecases

abstract class AbstractUseCase<P, R> {

    abstract suspend fun execute(parameters: P? = null): R?
}