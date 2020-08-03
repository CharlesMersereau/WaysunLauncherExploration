package com.waysuninc.waysunlauncherexploration.data.entities

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.graphics.drawable.Drawable
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter

class App (
    val label: String,
    val icon: Drawable?,
    val description: String,
    val appInfo: ApplicationInfo?,
    val launchIntent: Intent?,
    val downloaded: Boolean,
    val priority: Int,
    val packageName: String
) : SortedListAdapter.ViewModel {
    override fun <T : Any?> isContentTheSameAs(model: T): Boolean {
        val other = model as App
        return label == other.label ||
                description == other.description ||
                downloaded == other.downloaded ||
                priority == other.priority
    }

    override fun <T : Any?> isSameModelAs(model: T): Boolean {
        return label == (model as App).label
    }
}