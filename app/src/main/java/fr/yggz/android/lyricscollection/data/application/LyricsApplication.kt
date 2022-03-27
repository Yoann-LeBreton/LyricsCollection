package fr.yggz.android.lyricscollection.data.application

import android.app.Application
import fr.yggz.android.lyricscollection.data.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LyricsApplication :  Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@LyricsApplication)
            modules(
                DatabaseModule,
                DataSourceModule,
                RepositoriesModule,
                UseCasesModule,
                ViewModelModule
            )
        }
    }

}