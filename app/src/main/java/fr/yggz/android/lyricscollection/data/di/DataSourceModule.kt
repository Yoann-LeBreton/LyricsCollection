package fr.yggz.android.lyricscollection.data.di

import fr.yggz.android.lyricscollection.BuildConfig
import fr.yggz.android.lyricscollection.data.api.SongsApi
import fr.yggz.android.lyricscollection.data.database.SongDao
import fr.yggz.android.lyricscollection.data.datasources.SongsLocalDataSource
import fr.yggz.android.lyricscollection.data.datasources.SongsLocalDataSourceImpl
import fr.yggz.android.lyricscollection.data.datasources.SongsRemoteDataSource
import fr.yggz.android.lyricscollection.data.datasources.SongsRemoteDataSourceImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val DataSourceModule: Module = module {
    single { createClient() }
    single(definition = { retrofitWS<SongsApi>(get(), BuildConfig.BASE_URL) })
    single<SongsRemoteDataSource>(definition = { SongsRemoteDataSourceImpl()})
    single<SongsLocalDataSource> (definition = { provideSongsLocalDataSource(get()) })
}

fun provideSongsLocalDataSource(songDao: SongDao): SongsLocalDataSource{
    return SongsLocalDataSourceImpl(songDao = songDao)
}

inline fun <reified T> retrofitWS(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}

private fun createClient(): OkHttpClient{
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
    }
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}
