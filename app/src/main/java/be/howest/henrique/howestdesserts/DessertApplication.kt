package be.howest.henrique.howestdesserts

import android.app.Application
import be.howest.henrique.howestdesserts.data.AppContainer


class DessertApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}