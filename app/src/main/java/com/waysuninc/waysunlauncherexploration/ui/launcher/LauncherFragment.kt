package com.waysuninc.waysunlauncherexploration.ui.launcher

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.waysuninc.waysunlauncherexploration.R
import com.waysuninc.waysunlauncherexploration.data.entities.App
import com.waysuninc.waysunlauncherexploration.databinding.LauncherFragmentBinding
import com.waysuninc.waysunlauncherexploration.ui.adapters.AppListRecyclerViewAdapter

class LauncherFragment : Fragment() {

    companion object {
        fun newInstance() =
            LauncherFragment()
    }

    private var _binding: LauncherFragmentBinding? = null
    private val binding: LauncherFragmentBinding get() = _binding!!
    private val launcherViewModel: LauncherViewModel by activityViewModels(factoryProducer = { LauncherViewModelFactory(requireContext()) })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LauncherFragmentBinding.inflate(inflater)

        initializeUI()

        return binding.root
    }

    private fun initializeUI() {
        // setup downloaded recycler view
        binding.appListLauncherDownloaded.tvAppListTitle.text = resources.getString(R.string.downloaded_apps_list_title)
        binding.appListLauncherDownloaded.rvAppListApps.layoutManager =
            LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
        val downloadedListAdapter = AppListRecyclerViewAdapter(requireContext(), DownloadedAppClickListener(requireContext()))
        binding.appListLauncherDownloaded.rvAppListApps.adapter = downloadedListAdapter

        launcherViewModel.appsDownloaded.observe(viewLifecycleOwner, Observer {
            (binding.appListLauncherDownloaded.rvAppListApps.adapter as AppListRecyclerViewAdapter).edit().replaceAll(it).commit()
        })

        // setup not downloaded recycler view
        binding.appListLauncherToDownload.tvAppListTitle.text = resources.getString(R.string.not_downloaded_apps_list_title)
        binding.appListLauncherToDownload.rvAppListApps.layoutManager =
            LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
        val notDownloadedListAdapter = AppListRecyclerViewAdapter(requireContext(), NotDownloadedAppClickListener(requireContext()))
        binding.appListLauncherToDownload.rvAppListApps.adapter = notDownloadedListAdapter

        launcherViewModel.appsToDownload.observe(viewLifecycleOwner, Observer {
            (binding.appListLauncherToDownload.rvAppListApps.adapter as AppListRecyclerViewAdapter).edit().replaceAll(it).commit()
        })
    }

    class DownloadedAppClickListener(val context: Context) : AppListRecyclerViewAdapter.OnAppClickListener {
        override fun onAppClicked(appClicked: App) {
            Toast.makeText(context, "${appClicked.label} was clicked", Toast.LENGTH_LONG).show()
            context.startActivity(appClicked.launchIntent)
        }
    }

    class NotDownloadedAppClickListener(val context: Context) : AppListRecyclerViewAdapter.OnAppClickListener {
        override fun onAppClicked(appClicked: App) {
            Toast.makeText(context, "${appClicked.label} was clicked", Toast.LENGTH_LONG).show()
            // add code to download the app
            val toMarket = Intent(Intent.ACTION_VIEW)
            toMarket.data = Uri.parse("market://details?id=${appClicked.packageName}")
            context.startActivity(toMarket)
        }
    }
}