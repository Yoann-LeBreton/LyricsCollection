package fr.yggz.android.lyricscollection.data.datasources

import fr.yggz.android.lyricscollection.data.database.AlbumDao
import fr.yggz.android.lyricscollection.models.database.AlbumDb
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class AlbumLocalDataSourceTest {

    private lateinit var albumLocalDataSource: AlbumLocalDataSource
    private lateinit var albumDao: AlbumDao

    private val localAlbums: List<AlbumDb> = listOf(
        AlbumDb(1, "album 1", false),
        AlbumDb(1, "album 1", false)
    )

    @Before
    fun setUp() {
        albumDao = Mockito.mock(AlbumDao::class.java)
        albumLocalDataSource = AlbumLocalDataSourceImpl(albumDao)
    }

    @Test
    fun retrieveLocalAlbums(): Unit = runBlocking {
        Mockito.`when`(albumDao.getAlbums()).thenReturn(flow {
            emit(localAlbums)
        })
        val result = albumLocalDataSource.getAlbums()
        Assert.assertEquals(localAlbums, result.first())
        Mockito.verify(albumDao, Mockito.times(1)).getAlbums()
    }

    @Test
    fun insertLocalAlbums(): Unit = runBlocking {
        Mockito.`when`(albumDao.insert(localAlbums)).thenReturn(Mockito.any())
        albumLocalDataSource.insertAlbums(localAlbums)
        Mockito.verify(albumDao, Mockito.times(1)).insert(localAlbums)
    }

    @Test
    fun setAlbumFavorite(): Unit = runBlocking {
        Mockito.`when`(albumDao.updateFavorite(true, 2)).then { }
        albumLocalDataSource.setAlbumFavorite(2, true)
        Mockito.verify(albumDao, Mockito.times(1)).updateFavorite(true, 2)
    }
}