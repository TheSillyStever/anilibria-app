package ru.radiationx.shared_app.analytics.events

import ru.radiationx.data.analytics.AnalyticsSender
import timber.log.Timber
import javax.inject.Inject

class AppMetricaAnalyticsSender @Inject constructor() : AnalyticsSender {
    override fun send(key: String, vararg params: Pair<String, String>) {}
    override fun error(groupId: String, message: String, throwable: Throwable) {}
}