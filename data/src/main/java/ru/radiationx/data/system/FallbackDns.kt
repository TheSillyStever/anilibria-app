package ru.radiationx.data.system

import okhttp3.Dns
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.dnsoverhttps.DnsOverHttps
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FallbackDns @Inject constructor() : Dns {
    
    // minimal client dedicated to resolving DoH queries to prevent circular DI dependencies
    private val bootstrapClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .build()

    private val yandexDns = DnsOverHttps.Builder().client(bootstrapClient)
        .url("https://common.dot.dns.yandex.net/dns-query".toHttpUrl())
        .post(true) // POST is generally more reliable for DoH
        .bootstrapDnsHosts(
            InetAddress.getByName("77.88.8.8"),
            InetAddress.getByName("77.88.8.1")
        )
        .build()

    override fun lookup(hostname: String): List<InetAddress> {
        val addresses = mutableListOf<InetAddress>()
        
        try {
            // First attempt to resolve over Yandex DoH
            addresses.addAll(yandexDns.lookup(hostname))
        } catch (e: Exception) {
            // Fallback to local system dns if yandex doh fails or times out
            try {
                addresses.addAll(Dns.SYSTEM.lookup(hostname))
            } catch (ex: Exception) {
                // ignore
            }
        }

        // Hardcoded workaround fallback just for the anilibria tracking/API domain
        if (hostname == "wwnd.space" || hostname == "www.wwnd.space") {
            try {
                val fallbackAddress = InetAddress.getByName("31.184.217.238")
                if (!addresses.contains(fallbackAddress)) {
                    addresses.add(fallbackAddress)
                }
            } catch (ignore: Exception) {}
        }

        if (addresses.isEmpty()) {
            throw UnknownHostException(hostname)
        }

        return addresses
    }
}
