package fr.yggz.android.lyricscollection.data.di

import fr.yggz.android.lyricscollection.presentation.splashscreen.SplashViewModel
import fr.yggz.android.lyricscollection.presentation.main.song.DashboardViewModel
import fr.yggz.android.lyricscollection.presentation.main.album.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val ViewModelModule : Module = module {
    viewModel {
        SplashViewModel()
    }
    viewModel {
        HomeViewModel()
    }
    viewModel {
        DashboardViewModel()
    }

}