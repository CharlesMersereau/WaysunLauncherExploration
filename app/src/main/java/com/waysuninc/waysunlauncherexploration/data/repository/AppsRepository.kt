package com.waysuninc.waysunlauncherexploration.data.repository

import android.content.Context
import android.content.Intent.CATEGORY_LAUNCHER
import android.content.pm.PackageManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.waysuninc.waysunlauncherexploration.data.entities.App
import java.util.*
import kotlin.collections.ArrayList

object AppsRepository {

    var context: Context? = null
        set(value) {
            field = value
            checkForDownloadedApps()
        }

    val WAYSUN_APPS = Arrays.asList(
        "com.waysuninc.testapplication",
        "com.google.android.apps.maps",
        "com.ironhorsegames.idleherotowerdefensefantasytd",
        "com.waysuninc.testapplicationjava",
        "com.facebook.katana",
        "com.instagram.android",
        "com.google.android.gm",
        "com.alpha.mpsen.android"
    )
    val appList: MutableLiveData<ArrayList<App>> = MutableLiveData(ArrayList())
    val LOG_TAG = "AppsRepository"

    fun checkForDownloadedApps() {
        val apps: ArrayList<App> = ArrayList()

        for (app in WAYSUN_APPS) {
            try {
                val packageManager = (context as Context).packageManager

                val info = packageManager.getApplicationInfo(app, PackageManager.GET_META_DATA)
                val label = packageManager.getApplicationLabel(info).toString()
                val icon = packageManager.getApplicationIcon(info)
                val appLaunchIntent = packageManager.getLaunchIntentForPackage(app)
                appLaunchIntent?.addCategory(CATEGORY_LAUNCHER)

                apps.add(App(label, icon,"no descriptions yet", info, appLaunchIntent,true,2, app))
            } catch (nameNotFoundException: PackageManager.NameNotFoundException) {
                Log.i(LOG_TAG, "Package '$app' not found on device, skipping")
                apps.add(App(app,null,"no descriptions yet",null,null,false,1, app))
            }
        }
        appList.value!!.clear()
        appList.postValue(apps)
    }

    fun getApps() : MutableLiveData<ArrayList<App>> {
        return appList
    }
}