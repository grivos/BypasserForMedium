package com.grivos.bypasser.intercept.datasource.remote

import com.grivos.bypasser.intercept.data.datasource.MediumArticlesRemoteDataSource
import com.grivos.bypasser.intercept.domain.model.MediumArticle
import io.reactivex.Single
import java.io.IOException
import okhttp3.OkHttpClient
import okhttp3.Request

class MediumArticlesRemoteDataSourceImpl(private val client: OkHttpClient) :
    MediumArticlesRemoteDataSource {

    override fun getMediumArticle(url: String): Single<MediumArticle> {
        return Single.defer {
            try {
                val request = Request.Builder()
                    .url(url)
                    .build()
                val response = client.newCall(request).execute()
                val isPremium =
                    response.body?.string()?.contains(MEDIUM_PREMIUM_STAR_ICON_CLASS) == true
                Single.just(MediumArticle(url, isPremium))
            } catch (e: IOException) {
                Single.error<MediumArticle>(e)
            }
        }
    }

    companion object {
        private const val MEDIUM_PREMIUM_STAR_ICON_CLASS = "star-15px_svg__svgIcon-use"
    }
}
