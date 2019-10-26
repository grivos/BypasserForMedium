package com.grivos.bypasser.intercept.domain.usecase

import com.grivos.bypasser.intercept.domain.repository.MediumArticlesRepository
import com.grivos.fallback.MediumFallbackHandler
import com.grivos.fallback.domain.model.MediumInterceptionResult
import com.grivos.fallback.domain.model.MediumInterceptionResultNotPremium
import com.grivos.fallback.domain.model.MediumInterceptionResultPremium
import io.reactivex.Single

class MediumArticlesInterceptUseCase(
    private val repository: MediumArticlesRepository,
    private val mediumFallbackHandler: MediumFallbackHandler
) {

    fun getInterceptResult(url: String): Single<MediumInterceptionResult> {
        return repository.getMediumArticle(url)
            .flatMap { article ->
                if (article.isPremium) {
                    Single.just(MediumInterceptionResultPremium(article.url))
                } else {
                    mediumFallbackHandler.getFallbackApp()
                        .firstOrError()
                        .map { MediumInterceptionResultNotPremium(it) }
                }
            }
    }
}
