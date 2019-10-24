package com.grivos.fallback.domain.model

import android.graphics.drawable.Drawable

data class InstalledAppInfo(
    val name: String,
    val packageName: String,
    val activityName: String,
    val icon: Drawable
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InstalledAppInfo

        if (name != other.name) return false
        if (packageName != other.packageName) return false
        if (activityName != other.activityName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + packageName.hashCode()
        result = 31 * result + activityName.hashCode()
        return result
    }
}
