package ru.radiationx.anilibria

import android.app.ActivityManager
import android.app.Application
import android.content.Context

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.mintrocket.lib.mintpermissions.ext.initMintPermissions
import ru.mintrocket.lib.mintpermissions.flows.ext.initMintPermissionsFlow
import ru.radiationx.anilibria.di.AppModule
import ru.radiationx.data.datasource.holders.PreferencesHolder
import ru.radiationx.data.di.DataModule
import ru.radiationx.data.migration.MigrationDataSource
import ru.radiationx.quill.Quill
import ru.radiationx.quill.get
import timber.log.Timber

/*  Created by radiationx on 05.11.17. */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (isMainProcess()) {
            initInMainProcess()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun initInMainProcess() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        initDependencies()

        appVersionCheck()



        val preferencesHolder = get<PreferencesHolder>()

        preferencesHolder
            .notificationsAll
            .onEach {
                changeSubscribeStatus(it, "all")
            }
            .launchIn(GlobalScope)

        preferencesHolder
            .notificationsService
            .onEach {
                changeSubscribeStatus(it, "service")
                changeSubscribeStatus(it, "app_update")
                changeSubscribeStatus(it, "config")
            }
            .launchIn(GlobalScope)


        initMintPermissions()
        initMintPermissionsFlow()
    }

    private fun changeSubscribeStatus(enabled: Boolean, topic: String) {
    }

    private fun initDependencies() {
        Quill.getRootScope().installModules(AppModule(this), DataModule(this))
    }

    private fun appVersionCheck() {
        val migrationDataSource = get<MigrationDataSource>()
        migrationDataSource.update()
    }

    private fun isMainProcess() = packageName == getCurrentProcessName()

    private fun getCurrentProcessName(): String? {
        val mypid = android.os.Process.myPid()
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val processes = manager.runningAppProcesses
        return processes?.firstOrNull { it.pid == mypid }?.processName
    }

}
