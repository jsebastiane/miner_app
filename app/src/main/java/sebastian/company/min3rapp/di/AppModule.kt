package sebastian.company.min3rapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sebastian.company.min3rapp.common.Constants
import sebastian.company.min3rapp.data.remote.CoinGApi
import sebastian.company.min3rapp.data.remote.NewsApi
import sebastian.company.min3rapp.data.repository.CoinRepositoryImpl
import sebastian.company.min3rapp.data.repository.GlobalCoinImpl
import sebastian.company.min3rapp.data.repository.NewsRepositoryImpl
import sebastian.company.min3rapp.domain.repository.CoinRepository
import sebastian.company.min3rapp.domain.repository.GlobalCoinRepository
import sebastian.company.min3rapp.domain.repository.NewsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi{
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun providesCoinApi(): CoinGApi{
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.COIN_G_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinGApi::class.java)
    }

    //Providing the implementation of NewsRepositoryImpl and providing it with the api we will get from
    //provides NewsApp
    @Provides
    @Singleton
    fun provideNewsRepository(api : NewsApi): NewsRepository{
        return NewsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesGlobalCoinRepository(api: CoinGApi): GlobalCoinRepository{
        return GlobalCoinImpl(api)
    }

    @Provides
    @Singleton
    fun providesCoinRepository(api: CoinGApi): CoinRepository{
        return CoinRepositoryImpl(api)
    }

}