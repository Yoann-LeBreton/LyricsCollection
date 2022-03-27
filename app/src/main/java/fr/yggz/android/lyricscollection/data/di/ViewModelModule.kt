package fr.yggz.android.lyricscollection.data.di

import fr.yggz.android.lyricscollection.data.repositories.SongsRepositoryImpl
import fr.yggz.android.lyricscollection.domain.repositories.SongsRepository
import fr.yggz.android.lyricscollection.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val ViewModelModule : Module = module {
    viewModel {
        HomeViewModel()
    }

}