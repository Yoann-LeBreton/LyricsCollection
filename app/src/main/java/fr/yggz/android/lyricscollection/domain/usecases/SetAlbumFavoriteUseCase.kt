package fr.yggz.android.lyricscollection.domain.usecases

import fr.yggz.android.lyricscollection.domain.repositories.SongsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SetAlbumFavoriteUseCase: KoinComponent {
    private val songsRepository: SongsRepository by inject()

    suspend operator fun invoke(albumId: Int, favorite: Boolean) = songsRepository.setAlbumFavorite(albumId, favorite)
}