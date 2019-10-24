package com.grivos.bypasser.main.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.grivos.bypasser.main.R
import com.grivos.fallback.domain.model.InstalledAppInfo

class ChooseAppDialogAdapter(
    private val callback: ChooseAppDialog.Callback
) : ListAdapter<InstalledAppInfo, ChooseAppDialogAdapter.ViewHolder>(object :
    DiffUtil.ItemCallback<InstalledAppInfo>() {

    override fun areItemsTheSame(oldItem: InstalledAppInfo, newItem: InstalledAppInfo): Boolean =
        oldItem.packageName == newItem.packageName

    override fun areContentsTheSame(oldItem: InstalledAppInfo, newItem: InstalledAppInfo): Boolean =
        oldItem == newItem
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_app, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleView = itemView.findViewById<TextView>(R.id.itemAppTitle)
        private val logoView = itemView.findViewById<ImageView>(R.id.itemAppLogo)
        private lateinit var appInfo: InstalledAppInfo

        init {
            itemView.setOnClickListener {
                callback.onClickedApp(appInfo)
            }
        }

        fun bind(appInfo: InstalledAppInfo) {
            this.appInfo = appInfo
            titleView.text = appInfo.name
            logoView.setImageDrawable(appInfo.icon)
        }
    }
}
