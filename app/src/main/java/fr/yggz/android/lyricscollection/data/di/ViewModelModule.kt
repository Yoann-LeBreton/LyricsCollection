package fr.yggz.android.lyricscollection.data.di

import fr.yggz.android.lyricscollection.presentation.main.album.AlbumViewModel
import fr.yggz.android.lyricscollection.presentation.main.song.SongViewModel
import fr.yggz.android.lyricscollection.presentation.splashscreen.SplashViewModel
import fr.yggz.android.lyricscollection.presentation.tablet.TabletViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val ViewModelModule: Module = module {
    viewModel {
        SplashViewModel()
    }
    viewModel {
        AlbumViewModel()
    }
    viewModel {
        SongViewModel()
    }
    viewModel {
        TabletViewModel()
    }
}