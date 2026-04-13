package ru.radiationx.data.di.providers

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.radiationx.data.SharedBuildConfig
import ru.radiationx.data.analytics.features.SslCompatAnalytics
import ru.radiationx.data.sslcompat.SslCompat
import ru.radiationx.data.sslcompat.appendSslCompat
import ru.radiationx.data.system.appendSslCompatAnalytics
import ru.radiationx.data.system.FallbackDns
import javax.inject.Inject
import javax.inject.Provider


class SimpleOkHttpProvider @Inject constructor(
    private val context: Context,
    private val sharedBuildConfig: SharedBuildConfig,
    private val sslCompat: SslCompat,
    private val sslCompatAnalytics: SslCompatAnalytics,
    private val fallbackDns: FallbackDns
) : Provider<OkHttpClient> {

    override fun get(): OkHttpClient = OkHttpClient.Builder()
        .dns(fallbackDns)
        .appendSslCompatAnalytics(sslCompat, sslCompatAnalytics)
        .appendSslCompat(sslCompat)
        .apply {
            if (sharedBuildConfig.debug) {
                addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                addNetworkInterceptor(ChuckerInterceptor.Builder(context).build())
            }
        }
        .build()
}