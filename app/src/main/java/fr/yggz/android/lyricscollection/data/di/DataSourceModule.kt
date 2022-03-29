package fr.yggz.android.lyricscollection.data.di

import fr.yggz.android.lyricscollection.BuildConfig
import fr.yggz.android.lyricscollection.data.api.SongsApi
import fr.yggz.android.lyricscollection.data.database.AlbumDao
import fr.yggz.android.lyricscollection.data.database.SongDao
import fr.yggz.android.lyricscollection.data.datasources.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val DataSourceModule: Module = module {
    single { createClient() }
    single(definition = { retrofitWS<SongsApi>(get(), BuildConfig.BASE_URL) })
    single<SongsRemoteDataSource>(definition = { SongsRemoteDataSourceImpl(get()) })
    single<SongsLocalDataSource>(definition = { SongsLocalDataSourceImpl(songDao = get()) })
    single<AlbumLocalDataSource>(definition = { AlbumLocalDataSourceImpl(albumDao = get()) })
}

fun provideSongsLocalDataSource(songDao: SongDao): SongsLocalDataSource {
    return SongsLocalDataSourceImpl(songDao = songDao)
}

fun provideAlbumLocalDataSource(albumDao: AlbumDao): AlbumLocalDataSource {
    return AlbumLocalDataSourceImpl(albumDao = albumDao)
}

inline fun <reified T> retrofitWS(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}

private fun createClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
    }
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}
