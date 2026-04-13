package ru.radiationx.anilibria.ads

import android.app.Activity
import android.view.View
import ru.radiationx.anilibria.apptheme.AppTheme
import ru.radiationx.data.ads.domain.BannerAdConfig

class BannerAdController(
    private val activity: Activity,
    private val adView: View,
    private val adContainer: View,
) {
    fun load(config: BannerAdConfig, theme: AppTheme) {}
    fun destroy() {}
}