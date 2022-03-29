package fr.yggz.android.lyricscollection.data.di

import fr.yggz.android.lyricscollection.domain.usecases.*
import org.koin.core.module.Module
import org.koin.dsl.module

val UseCasesModule: Module = module {
    single { SyncSongsUseCase() }
    single { GetSongsUseCase() }
    single { GetAlbumsUseCase() }
    single { SetAlbumFavoriteUseCase() }
    single { SetSongFavoriteUseCase() }
    single { GetSongsByAlbumIdUseCase() }
}