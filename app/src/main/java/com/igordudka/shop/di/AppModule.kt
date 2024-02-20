package com.igordudka.shop.di

import android.content.Context
import androidx.room.Room
import com.igordudka.data.database.room.AppDatabase
import com.igordudka.data.network.api.ShopApi
import com.igordudka.repository.DatabaseRepository
import com.igordudka.repository.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNetworkRepository() : NetworkRepository{

        val api: ShopApi by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://run.mocky.io/v3/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            retrofit.create(ShopApi::class.java)
        }
        return NetworkRepository(api)
    }

    @Provides
    @Singleton
    fun provideDatabaseRepository(@ApplicationContext context: Context) : DatabaseRepository{
        val db by lazy {
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "app.db"
            ).build()
        }
        return DatabaseRepository(db.databaseDao())
    }
}