package com.sample.easytodo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EasyToDoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}