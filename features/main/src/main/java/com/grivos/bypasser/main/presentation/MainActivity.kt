package com.grivos.bypasser.main.presentation

import android.graphics.drawable.Animatable
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import com.grivos.bypasser.main.R
import com.grivos.fallback.domain.model.InstalledAppInfo
import com.grivos.fallback.domain.model.MediumFallbackApp
import com.grivos.fallback.domain.model.MediumFallbackAppExists
import com.grivos.fallback.domain.model.MediumFallbackAppNone
import com.grivos.presentation.BaseActivity
import com.grivos.spanomatic.utils.addSpansFromAnnotations
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(), MainView, ChooseAppDialog.Callback {

    private val presenter: MainPresenter by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainChangeFallback.setOnClickListener { presenter.onClickChangeFallbackApp() }
        val drawable =
            AppCompatResources.getDrawable(this, R.drawable.anim_star_off)?.mutate()
        mainAnimatedLogo.setImageDrawable(drawable)
        presenter.attachView(this, lifecycle)
    }

    override fun showFallbackApp(fallbackApp: MediumFallbackApp) {
        when (fallbackApp) {
            MediumFallbackAppNone -> {
                mainFallbackCardView.isVisible = false
                mainIntro.text = addSpansFromAnnotations(R.string.intro_no_fallback)
            }
            is MediumFallbackAppExists -> {
                mainFallbackCardView.isVisible = true
                mainFallbackLogo.setImageDrawable(fallbackApp.app.icon)
                mainIntro.text = addSpansFromAnnotations(R.string.intro)
            }
        }
    }

    override fun showChooseFallbackDialog(apps: List<InstalledAppInfo>) {
        ChooseAppDialog(apps, this).show(supportFragmentManager, TAG_CHOOSE_APP_DIALOG)
    }

    override fun animateLogo() {
        (mainAnimatedLogo.drawable as? Animatable)?.start()
    }

    override fun onClickedApp(appInfo: InstalledAppInfo) {
        (supportFragmentManager.findFragmentByTag(TAG_CHOOSE_APP_DIALOG) as? ChooseAppDialog)?.dismiss()
        presenter.onClickedApp(appInfo)
    }

    companion object {
        private const val TAG_CHOOSE_APP_DIALOG = "choose_app"
    }
}
