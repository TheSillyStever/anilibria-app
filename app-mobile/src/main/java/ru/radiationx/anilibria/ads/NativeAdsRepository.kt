package ru.radiationx.anilibria.ads

import android.content.Context
import javax.inject.Inject

class NativeAdsRepository @Inject constructor(
    private val context: Context,
) {
    suspend fun load(request: Any): Any = Any()
}