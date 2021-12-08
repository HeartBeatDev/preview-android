package com.preview.android.presentation.di

import android.content.Context
import androidx.room.Room
import com.preview.android.BuildConfig
import com.preview.android.data.db.AppDatabase
import com.preview.android.data.db.auth.ActionDao
import com.preview.android.data.db.config.ConfigDao
import com.preview.android.data.db.user.UserDao
import com.preview.android.data.network.BASE_URL
import com.preview.android.data.network.api.AuthApi
import com.preview.android.data.network.api.ConfigApi
import com.preview.android.data.repositories.auth.AuthLocalDataSource
import com.preview.android.data.repositories.auth.AuthRemoteDataSource
import com.preview.android.data.repositories.auth.AuthRepositoryImpl
import com.preview.android.data.repositories.config.ConfigLocalDataSource
import com.preview.android.data.repositories.config.ConfigRemoteDataSource
import com.preview.android.data.repositories.config.ConfigRepositoryImpl
import com.preview.android.data.repositories.user.UserLocalDataSource
import com.preview.android.data.repositories.user.UserRepositoryImpl
import com.preview.android.data.sources.auth.AuthDbDataSource
import com.preview.android.data.sources.auth.AuthRetrofitDataSource
import com.preview.android.data.sources.config.ConfigDbDataSource
import com.preview.android.data.sources.config.ConfigRetrofitDataSource
import com.preview.android.data.sources.user.UserDbDataSource
import com.preview.android.domain.repositories.AuthRepository
import com.preview.android.domain.repositories.ConfigRepository
import com.preview.android.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideInterceptor(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient) : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Provides
    @Singleton
    fun provideConfigApi(retrofit : Retrofit) : ConfigApi = retrofit.create(ConfigApi::class.java)

    @Provides
    @Singleton
    fun provideAuthApi(retrofit : Retrofit) : AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context) = Room
            .databaseBuilder(appContext, AppDatabase::class.java, "aso_nineteen_database")
            .build()

    @Provides
    @Singleton
    fun provideConfigDao(appDatabase: AppDatabase): ConfigDao = appDatabase.configDao()

    @Provides
    @Singleton
    fun provideActionDao(appDatabase: AppDatabase): ActionDao = appDatabase.actionDao()

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()

    @Provides
    @Singleton
    fun provideConfigRemoteDataSource(configApi: ConfigApi): ConfigRemoteDataSource {
        return ConfigRetrofitDataSource(configApi)
    }

    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(authApi: AuthApi): AuthRemoteDataSource {
        return AuthRetrofitDataSource(authApi)
    }

    @Provides
    @Singleton
    fun provideConfigLocalDataSource(configDao: ConfigDao): ConfigLocalDataSource {
        return ConfigDbDataSource(configDao)
    }

    @Provides
    @Singleton
    fun provideUserLocalDataSource(userDao: UserDao): UserLocalDataSource {
        return UserDbDataSource(userDao)
    }

    @Provides
    @Singleton
    fun provideAuthLocalDataSource(actionDao: ActionDao): AuthLocalDataSource {
        return AuthDbDataSource(actionDao)
    }

    @Provides
    @Singleton
    fun provideConfigRepository(
        remoteDataSource: ConfigRemoteDataSource,
        localDataSource: ConfigLocalDataSource
    ): ConfigRepository {
        return ConfigRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Provides
    @Singleton
    fun provideUserRepository(localDataSource: UserLocalDataSource): UserRepository {
        return UserRepositoryImpl(localDataSource)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authRemoteDataSource: AuthRemoteDataSource,
        authLocalDataSource: AuthLocalDataSource
    ): AuthRepository {
        return AuthRepositoryImpl(authRemoteDataSource, authLocalDataSource)
    }
}