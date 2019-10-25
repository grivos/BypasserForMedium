object ApplicationId {
    val id = "com.grivos.bypasser"
}

object Modules {
    val app = ":app"
    val navigation = ":navigation"
    val cache = ":common:cache"
    val presentation = ":common:presentation"
    val intercept = ":features:intercept"
    val main = ":features:main"
    val webview = ":features:webview"
    val fallback = ":fallback"
}

object Releases {
    val versionCode = 2
    val versionName = "1.0.1"
}

object Versions {
    val compileSdk = 28
    val minSdk = 21
    val targetSdk = 28

    val appcompat = "1.1.0"
    val design = "1.1.0-beta01"
    val cardview = "1.0.0"
    val recyclerview = "1.1.0-beta05"
    val constraintLayout = "1.1.3"
    val ktx = "1.0.0-alpha1"

    val kotlin = "1.3.50"
    val timber = "4.7.1"
    val rxjava = "2.2.13"
    val rxAndroid = "2.1.1"
    val rxkotlin = "2.4.0"
    val okhttp = "4.2.1"
    val loggingInterceptor = "4.0.0"
    val rxpaper = "1.4.0"
    val lifecycle = "2.1.0"
    val leakCanary = "2.0-alpha-3"
    val koin = "2.0.1"
    val rxPreferences = "2.0.0"
    val spanomatic = "1.0.1"
    val viewPump = "1.0.0"
    val nestedScrollWebView = "v1.0.2"
}

object Libraries {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val ktx = "androidx.core:core-ktx:${Versions.ktx}"
    val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
    val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    val rxkotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxkotlin}"
    val rxPreferences = "com.f2prateek.rx.preferences2:rx-preferences:${Versions.rxPreferences}"

    val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"

    val rxpaper = "com.github.pakoito:RxPaper2:${Versions.rxpaper}"
    val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"

    val leakCanaryAndroid = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"

    val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"

    val spanomatic = "com.grivos.spanomatic:spanomatic:${Versions.spanomatic}"
    val viewPump = "io.github.inflationx:viewpump:${Versions.viewPump}"

    val nestedScrollWebView = "com.github.rhlff:NestedScrollWebView:${Versions.nestedScrollWebView}"
}

object SupportLibraries {
    val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    val design = "com.google.android.material:material:${Versions.design}"
    val cardview = "androidx.cardview:cardview:${Versions.cardview}"
    val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
}