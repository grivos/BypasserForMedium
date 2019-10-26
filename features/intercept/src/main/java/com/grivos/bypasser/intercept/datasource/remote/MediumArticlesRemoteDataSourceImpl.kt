package com.grivos.bypasser.intercept.datasource.remote

import com.grivos.bypasser.intercept.data.datasource.MediumArticlesRemoteDataSource
import com.grivos.bypasser.intercept.domain.model.MediumArticle
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class MediumArticlesRemoteDataSourceImpl(private val client: OkHttpClient) :
    MediumArticlesRemoteDataSource {

    override fun getMediumArticle(url: String): Single<MediumArticle> {
        client.followRedirects
        return Single.defer {
            try {
                val request = Request.Builder()
                    .url(url)
                    .build()
                val response = client.newCall(request).execute()
                val body = response.body?.string()
                if (url.contains(MEDIUM_DEEP_LINK_HOST)) {
                    // This is a redirect page, try to get the real Medium url
                    val redirectUrl = getRedirectUrl(body)
                    if (redirectUrl != null) {
                        getMediumArticle(redirectUrl)
                    } else {
                        // Failed getting the real Medium url, just guess that it's a premium post
                        Single.just(MediumArticle(url, true))
                    }
                } else {
                    val isPremium =
                        body?.contains(MEDIUM_PREMIUM_STAR_ICON_CLASS) == true
                    Single.just(MediumArticle(url, isPremium))
                }
            } catch (e: IOException) {
                Single.error<MediumArticle>(e)
            }
        }
    }

    private fun getRedirectUrl(body: String?): String? {
        return body?.let { html ->
            val startIndex = html.indexOf("https")
            if (startIndex != -1) {
                val endIndex = html.indexOf("\"", startIndex)
                html.substring(startIndex, endIndex)
            } else {
                null
            }
        }
    }

    companion object {
        private const val MEDIUM_PREMIUM_STAR_ICON_CLASS = "star-15px_svg__svgIcon-use"
        private const val MEDIUM_DEEP_LINK_HOST = "link.medium.com"
    }
}
