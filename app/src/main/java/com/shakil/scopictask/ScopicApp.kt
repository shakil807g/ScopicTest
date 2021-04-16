package com.shakil.scopictask

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.util.CoilUtils
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient

@HiltAndroidApp
class ScopicApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}