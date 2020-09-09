package wpa.wp.myapplication.di

import android.app.Application
import dagger.Module
import dagger.Provides
import wpa.wp.myapplication.data.network.ApiService
import wpa.wp.myapplication.data.network.ConnectivityInterceptor
import wpa.wp.myapplication.data.network.ConnectivityInterceptorImpl
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideConnectivityInterceptor(application: Application): ConnectivityInterceptor {
        return ConnectivityInterceptorImpl(application)
    }

    @Provides
    @Singleton
    fun provideQuizApiService(connectivityInterceptor: ConnectivityInterceptor): ApiService{
        return ApiService.invoke(connectivityInterceptor)
    }
}