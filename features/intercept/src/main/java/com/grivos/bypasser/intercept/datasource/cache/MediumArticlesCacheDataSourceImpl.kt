package com.grivos.bypasser.intercept.datasource.cache

import com.grivos.bypasser.intercept.data.datasource.MediumArticlesCacheDataSource
import com.grivos.bypasser.intercept.domain.model.MediumArticle
import com.grivos.cache.ReactiveCache
import io.reactivex.Single

class MediumArticlesCacheDataSourceImpl constructor(
    private val cache: ReactiveCache<MutableMap<String, MediumArticle>>
) : MediumArticlesCacheDataSource {

    private val key = "Medium Articles"

    override fun getMediumArticle(url: String): Single<MediumArticle> =
        cache.load(key)
            .map { map -> map[url] }

    override fun setMediumArticle(url: String, article: MediumArticle): Single<MediumArticle> =
        cache.load(key, mutableMapOf())
            .map { map ->
                map[url] = article
                map
            }
            .flatMap { cache.save(key, it) }
            .map { article }
}
