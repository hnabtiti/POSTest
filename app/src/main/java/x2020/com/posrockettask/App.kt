package x2020.com.posrockettask

import android.app.Application
import android.content.Context
import org.koin.android.ext.android.startKoin
import x2020.com.posrockettask.database.DatabaseRepository
import x2020.com.posrockettask.api.networkModule
import x2020.com.posrockettask.api.serverRepository

val moduleList = listOf(
    networkModule, viewModelModule,
    serverRepository, DatabaseRepository,productsViewModel
)


class App : Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin(this, moduleList)

    }

}