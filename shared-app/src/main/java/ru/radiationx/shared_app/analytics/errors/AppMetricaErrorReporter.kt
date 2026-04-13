package ru.radiationx.shared_app.analytics.errors

import ru.radiationx.data.analytics.AnalyticsErrorReporter
import timber.log.Timber
import javax.inject.Inject

class AppMetricaErrorReporter @Inject constructor() : AnalyticsErrorReporter {
    override fun report(message: String, error: Throwable) {}
    override fun report(group: String, message: String) {}
    override fun report(group: String, message: String, error: Throwable) {}
}