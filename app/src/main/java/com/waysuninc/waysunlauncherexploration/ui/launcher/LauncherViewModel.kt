package com.waysuninc.waysunlauncherexploration.ui.launcher

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waysuninc.waysunlauncherexploration.data.entities.App
import com.waysuninc.waysunlauncherexploration.data.repository.AppsRepository
import kotlinx.coroutines.launch

class LauncherViewModel(val appsRepository: AppsRepository) : ViewModel() {

    val appsDownloaded = MutableLiveData<ArrayList<App>>(ArrayList())
    val appsToDownload = MutableLiveData<ArrayList<App>>(ArrayList())

    init {
        viewModelScope.launch {
            getApps()
        }
    }

    private fun getApps() {
        val apps = appsRepository.getApps()
        apps.observeForever(Observer {

            val downloaded = ArrayList<App>()
            val notDownloaded = ArrayList<App>()

            for (app in it) {
                if (app.downloaded) downloaded.add(app) else notDownloaded.add(app)
            }
            appsDownloaded.value!!.clear()
            appsDownloaded.postValue(downloaded)
            appsToDownload.value!!.clear()
            appsToDownload.postValue(notDownloaded)
        })
    }

}