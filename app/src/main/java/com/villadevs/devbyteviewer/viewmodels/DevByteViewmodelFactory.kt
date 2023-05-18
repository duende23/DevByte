package com.villadevs.devbyteviewer.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DevByteViewmodelFactory (val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DevByteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DevByteViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}