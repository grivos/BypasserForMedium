package com.grivos.bypasser.main.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.grivos.bypasser.main.R
import com.grivos.fallback.domain.model.InstalledAppInfo
import kotlinx.android.synthetic.main.dialog_choose_app.*

class ChooseAppDialog(private val apps: List<InstalledAppInfo>, private val callback: Callback) :
    BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.dialog_choose_app, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        chooseAppRecycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        val adapter = ChooseAppDialogAdapter(callback)
        adapter.submitList(apps)
        chooseAppRecycler.adapter = adapter
    }

    interface Callback {
        fun onClickedApp(appInfo: InstalledAppInfo)
    }
}
