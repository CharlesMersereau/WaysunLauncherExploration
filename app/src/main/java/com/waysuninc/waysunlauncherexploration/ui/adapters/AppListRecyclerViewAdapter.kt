package com.waysuninc.waysunlauncherexploration.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.waysuninc.waysunlauncherexploration.data.entities.App
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.waysuninc.waysunlauncherexploration.databinding.ItemApplistAppBinding

class AppListRecyclerViewAdapter(context: Context, val onAppClickListener: OnAppClickListener): SortedListAdapter<App>(context, App::class.java, Comparator<App> { a, b -> a.priority.compareTo(b.priority)} ) {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<out App> {
        return AppViewHolder(ItemApplistAppBinding.inflate(inflater, parent, false), onAppClickListener)
    }

    class AppViewHolder(val binding: ItemApplistAppBinding, val onAppClickListener: OnAppClickListener) : SortedListAdapter.ViewHolder<App>(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun performBind(item: App) {
            binding.tvAppListItemLabel.text = item.label
            binding.ivAppListItemIcon.setImageDrawable(item.icon)
        }

        override fun onClick(p0: View?) {
            onAppClickListener.onAppClicked(currentItem)
        }
    }

    interface OnAppClickListener {
        fun onAppClicked(appClicked: App)
    }
}