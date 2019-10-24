package com.grivos.bypasser.intercept.domain.repository

import com.grivos.bypasser.intercept.domain.model.MediumArticle
import io.reactivex.Single

interface MediumArticlesRepository {

    fun getMediumArticle(url: String): Single<MediumArticle>
}
