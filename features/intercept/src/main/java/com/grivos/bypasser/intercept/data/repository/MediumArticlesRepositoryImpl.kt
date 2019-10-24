package com.grivos.bypasser.intercept.data.repository

import com.grivos.bypasser.intercept.data.datasource.MediumArticlesCacheDataSource
import com.grivos.bypasser.intercept.data.datasource.MediumArticlesRemoteDataSource
import com.grivos.bypasser.intercept.domain.model.MediumArticle
import com.grivos.bypasser.intercept.domain.repository.MediumArticlesRepository
import io.reactivex.Single

class MediumArticlesRepositoryImpl constructor(
    private val cacheDataSource: MediumArticlesCacheDataSource,
    private val remoteDataSource: MediumArticlesRemoteDataSource
) : MediumArticlesRepository {

    override fun getMediumArticle(url: String): Single<MediumArticle> {
        return cacheDataSource.getMediumArticle(url)
            .onErrorResumeNext {
                remoteDataSource.getMediumArticle(url)
                    .flatMap { article -> cacheDataSource.setMediumArticle(url, article) }
            }
    }
}
