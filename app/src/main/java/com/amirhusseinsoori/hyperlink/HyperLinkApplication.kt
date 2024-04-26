package com.amirhusseinsoori.hyperlink

import android.app.Application
import com.amirhusseinsoori.hyperlink.data.di.appModule
import com.amirhusseinsoori.hyperlink.data.di.dbModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class HyperLinkApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {

            androidLogger()

            androidContext(this@HyperLinkApplication)

            modules(dbModule, appModule)


        }


    }
}