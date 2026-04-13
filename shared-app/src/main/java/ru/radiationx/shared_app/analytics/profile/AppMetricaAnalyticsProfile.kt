package ru.radiationx.shared_app.analytics.profile

import ru.radiationx.data.analytics.profile.AnalyticsInstallerProfileDataSource
import ru.radiationx.data.analytics.profile.AnalyticsProfile
import ru.radiationx.data.analytics.profile.AnalyticsMainProfileDataSource
import ru.radiationx.shared_app.analytics.AnalyticsCodecsProfileDataSource
import javax.inject.Inject

class AppMetricaAnalyticsProfile @Inject constructor(
    private val main: AnalyticsMainProfileDataSource,
    private val codecs: AnalyticsCodecsProfileDataSource,
    private val installer: AnalyticsInstallerProfileDataSource
) : AnalyticsProfile {
    override fun update() {}
}