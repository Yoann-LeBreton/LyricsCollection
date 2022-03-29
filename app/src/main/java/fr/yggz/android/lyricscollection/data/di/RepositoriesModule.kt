package fr.yggz.android.lyricscollection.data.di

import fr.yggz.android.lyricscollection.data.repositories.SongsRepositoryImpl
import fr.yggz.android.lyricscollection.domain.repositories.SongsRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val RepositoriesModule: Module = module {
    single<SongsRepository>(definition = {
        SongsRepositoryImpl(
            albumLocalDataSource = get(),
            songsLocalDataSource = get(),
            remoteDataSource = get()
        )
    })
}