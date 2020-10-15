package com.example.documentapp

import android.app.Application
import com.example.documentapp.di.ApplicationComponent
import com.example.documentapp.di.DaggerApplicationComponent

class DocumentApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.create()

    }

    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }
}