package com.grivos.bypasser.intercept.data.datasource

import com.grivos.bypasser.intercept.domain.model.MediumArticle
import io.reactivex.Single

interface MediumArticlesCacheDataSource {
    fun getMediumArticle(url: String): Single<MediumArticle>
    fun setMediumArticle(url: String, article: MediumArticle): Single<MediumArticle>
}

interface MediumArticlesRemoteDataSource {
    fun getMediumArticle(url: String): Single<MediumArticle>
}
