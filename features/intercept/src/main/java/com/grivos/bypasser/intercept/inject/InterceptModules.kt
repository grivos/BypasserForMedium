package com.grivos.bypasser.intercept.inject

import com.grivos.bypasser.intercept.BuildConfig
import com.grivos.bypasser.intercept.data.datasource.MediumArticlesCacheDataSource
import com.grivos.bypasser.intercept.data.datasource.MediumArticlesRemoteDataSource
import com.grivos.bypasser.intercept.data.repository.MediumArticlesRepositoryImpl
import com.grivos.bypasser.intercept.datasource.cache.MediumArticlesCacheDataSourceImpl
import com.grivos.bypasser.intercept.datasource.remote.MediumArticlesRemoteDataSourceImpl
import com.grivos.bypasser.intercept.domain.model.MediumArticle
import com.grivos.bypasser.intercept.domain.repository.MediumArticlesRepository
import com.grivos.bypasser.intercept.domain.usecase.MediumArticlesInterceptUseCase
import com.grivos.bypasser.intercept.presentation.InterceptPresenter
import com.grivos.cache.ReactiveCache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

object InterceptModules {
    fun injectFeature() = loadFeature
}

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            viewModelModule,
            useCaseModule,
            repositoryModule,
            dataSourceModule,
            networkModule,
            cacheModule
        )
    )
}

val viewModelModule: Module = module {
    viewModel { InterceptPresenter(mediumArticlesInterceptUseCase = get()) }
}

val useCaseModule: Module = module {
    factory { MediumArticlesInterceptUseCase(repository = get(), mediumFallbackHandler = get()) }
}

val repositoryModule: Module = module {
    single {
        MediumArticlesRepositoryImpl(
            cacheDataSource = get(),
            remoteDataSource = get()
        ) as MediumArticlesRepository
    }
}

val dataSourceModule: Module = module {
    single {
        MediumArticlesCacheDataSourceImpl(
            cache = get(qualifier = named(MEDIUM_ARTICLES_CACHE))
        ) as MediumArticlesCacheDataSource
    }
    single { MediumArticlesRemoteDataSourceImpl(client = get()) as MediumArticlesRemoteDataSource }
}

val networkModule: Module = module {
    single {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor =
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        clientBuilder.build()
    }
}

val cacheModule: Module = module {
    single(qualifier = named(MEDIUM_ARTICLES_CACHE)) { ReactiveCache<List<MediumArticle>>() }
}

private const val MEDIUM_ARTICLES_CACHE = "MEDIUM_ARTICLES_CACHE"
private const val TIMEOUT_SECONDS = 20L
