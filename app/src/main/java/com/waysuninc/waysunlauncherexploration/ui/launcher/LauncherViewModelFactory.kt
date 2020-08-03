package com.waysuninc.waysunlauncherexploration.ui.launcher

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.waysuninc.waysunlauncherexploration.data.repository.AppsRepository
import java.lang.IllegalArgumentException

class LauncherViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if ( modelClass.isAssignableFrom(LauncherViewModel::class.java)) {
            return LauncherViewModel(AppsRepository) as T
        }
        throw IllegalArgumentException("ViewModel class not found.")
    }
}