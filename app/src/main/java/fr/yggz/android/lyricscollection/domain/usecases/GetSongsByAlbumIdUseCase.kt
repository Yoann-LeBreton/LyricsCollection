package fr.yggz.android.lyricscollection.domain.usecases

import fr.yggz.android.lyricscollection.domain.repositories.SongsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetSongsByAlbumIdUseCase : KoinComponent {
    private val songsRepository: SongsRepository by inject()

    suspend operator fun invoke(albumId: Int) = songsRepository.getSongsByAlbumId(albumId)
}