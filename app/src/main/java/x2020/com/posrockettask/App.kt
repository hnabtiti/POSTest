package x2020.com.posrockettask

import android.app.Application
import org.koin.android.ext.android.startKoin
import x2020.com.posrockettask.api.networkModule
import x2020.com.posrockettask.api.serverRepository
import x2020.com.posrockettask.database.room.DatabaseRepository
import x2020.com.posrockettask.database.sqldelight.sqlDeliDatabase

val moduleList = listOf(
    networkModule,
    serverRepository,
    productsViewModel,
    DatabaseRepository,
    sqlDeliDatabase
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