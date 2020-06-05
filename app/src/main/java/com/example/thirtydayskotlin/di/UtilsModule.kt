package com.example.thirtydayskotlin.di

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.thirtydayskotlin.repository.Repository
import com.example.thirtydayskotlin.request.ApiCallInterface
import com.example.thirtydayskotlin.utils.Urls
import com.example.thirtydayskotlin.utils.ViewModelFactory
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class UtilsModule
{
    @Provides
    @Singleton
    fun provideGson(): Gson {
        val builder = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return builder.setLenient().create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson?, okHttpClient: OkHttpClient?): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Urls.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun getApiCallInterface(retrofit: Retrofit): ApiCallInterface {
        return retrofit.create(ApiCallInterface::class.java)
    }

    @get:Singleton
    @get:Provides
    val requestHeader: OkHttpClient
        get() {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor { chain: Interceptor.Chain ->
                val original = chain.request()
                val request = original.newBuilder().build()
                Log.i("Airtel","Request_Data"+request.url())
                Log.i("Airtel","Request_Data"+request.headers())
                Log.i("Airtel","Request_Data"+request.method())
                chain.proceed(request)
            }
                .connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
            return httpClient.build()
        }

    @Provides
    @Singleton
    fun getRepository(apiCallInterface: ApiCallInterface?): Repository {
        return Repository(apiCallInterface!!)
    }

    @Provides
    @Singleton
    fun getViewModelFactory(myRepository: Repository): ViewModelProvider.Factory {
        return ViewModelFactory(myRepository)
    }
}