package com.example.thirtydayskotlin

import android.app.Application
import com.example.thirtydayskotlin.di.AppComponent
import com.example.thirtydayskotlin.di.DaggerAppComponent
import com.example.thirtydayskotlin.di.UtilsModule

class MyApplication: Application() {
    var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().utilsModule(UtilsModule()).build()
    }
}