package com.waysuninc.waysunlauncherexploration

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.waysuninc.waysunlauncherexploration.data.repository.AppsRepository
import com.waysuninc.waysunlauncherexploration.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var broadcastReceiver: CheckDownloadedAppsBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppsRepository.context = this

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        broadcastReceiver = CheckDownloadedAppsBroadcastReceiver()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION).apply {
            addAction(Intent.ACTION_PACKAGE_ADDED)
            addAction(Intent.ACTION_PACKAGE_REMOVED)
            addDataScheme("package")
        }
        registerReceiver(broadcastReceiver, filter)

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

    class CheckDownloadedAppsBroadcastReceiver() : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Toast.makeText(context, "app downloaded", Toast.LENGTH_LONG).show()
            AppsRepository.checkForDownloadedApps()
        }
    }
}