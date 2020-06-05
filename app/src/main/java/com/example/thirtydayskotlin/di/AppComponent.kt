package com.example.thirtydayskotlin.di

import com.example.thirtydayskotlin.base.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ UtilsModule::class])
@Singleton
interface AppComponent {
    fun doMainActivityInjection(mainActivity: MainActivity?)
}