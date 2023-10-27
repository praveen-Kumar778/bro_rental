package com.example.brorentalapp

import android.app.Application
import com.example.brorentalapp.utils.SessionConfig


class BroRentalApplication:Application() {

    lateinit var sharedPref: SessionConfig
    override fun onCreate() {
        super.onCreate()

        sharedPref = SessionConfig(applicationContext)    }
    private fun initialize() {
        sharedPref = SessionConfig(applicationContext)
    }

}