package com.grivos.fallback

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.grivos.fallback.domain.model.InstalledAppInfo
import com.grivos.fallback.domain.model.MediumFallbackApp
import com.grivos.fallback.domain.model.MediumFallbackAppExists
import com.grivos.fallback.domain.model.MediumFallbackAppNone
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class MediumFallbackHandler(
    private val context: Context,
    private val rxSharedPreferences: RxSharedPreferences
) {

    fun setFallbackApp(packageName: String) {
        rxSharedPreferences.getString(PREF_KEY_FALLBACK_PACKAGE).set(packageName)
    }

    fun getFallbackApp(): Observable<MediumFallbackApp> {
        val savedPackageObservable = rxSharedPreferences.getString(PREF_KEY_FALLBACK_PACKAGE)
            .asObservable()
        return Observable.combineLatest(
            savedPackageObservable,
            getPossibleFallbackApps().toObservable(),
            BiFunction<String, List<InstalledAppInfo>, MediumFallbackApp> { savedPackageName, fallbackApps ->
                val selected = fallbackApps.firstOrNull { it.packageName == savedPackageName }
                if (selected != null) {
                    MediumFallbackAppExists(selected)
                } else {
                    guessFallbackApp(fallbackApps)
                }
            })
            .distinctUntilChanged()
    }

    private fun guessFallbackApp(fallbackApps: List<InstalledAppInfo>): MediumFallbackApp {
        val mediumApp = fallbackApps.firstOrNull { it.packageName == MEDIUM_PACKAGE_NAME }
        return when {
            mediumApp != null -> MediumFallbackAppExists(mediumApp)
            fallbackApps.isNotEmpty() -> MediumFallbackAppExists(fallbackApps.first())
            else -> MediumFallbackAppNone
        }
    }

    fun getPossibleFallbackApps(): Single<List<InstalledAppInfo>> {
        return Single.defer {
            val pm = context.packageManager
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(MEDIUM_URL)
            val appsInfo = pm.queryIntentActivities(intent, 0)
                .map { it.activityInfo }
                .distinctBy { it.packageName }
                .filter { it.packageName != context.packageName }
                .map { activityInfo ->
                    InstalledAppInfo(
                        activityInfo.loadLabel(pm).toString(),
                        activityInfo.packageName,
                        activityInfo.name,
                        activityInfo.loadIcon(pm)
                    )
                }
            Single.just(appsInfo)
        }
    }

    companion object {
        private const val PREF_KEY_FALLBACK_PACKAGE = "fallback_package"
        private const val MEDIUM_PACKAGE_NAME = "com.medium.reader"
        private const val MEDIUM_URL = "https://medium.com"
    }
}
