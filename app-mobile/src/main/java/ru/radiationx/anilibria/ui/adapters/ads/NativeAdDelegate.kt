package ru.radiationx.anilibria.ui.adapters.ads

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.radiationx.anilibria.R
import ru.radiationx.anilibria.ui.adapters.ListItem
import ru.radiationx.anilibria.ui.adapters.NativeAdListItem
import ru.radiationx.anilibria.ui.common.adapters.AppAdapterDelegate

class NativeAdDelegate :
    AppAdapterDelegate<NativeAdListItem, ListItem, NativeAdDelegate.ViewHolder>(
        R.layout.item_native_ad,
        { it is NativeAdListItem },
        { ViewHolder(it) }
    ) {
    override fun bindData(item: NativeAdListItem, holder: ViewHolder) {}
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}