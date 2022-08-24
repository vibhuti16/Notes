@file:Suppress("unused")

package dev.io.notes

import android.app.Application
import dev.io.notes.di.databaseModule
import dev.io.notes.di.repositories
import dev.io.notes.di.utils
import dev.io.notes.di.viewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            //inject Android context
            androidContext(applicationContext)
            koin.loadModules(listOf(databaseModule, viewModel, repositories, utils))
        }
    }
}