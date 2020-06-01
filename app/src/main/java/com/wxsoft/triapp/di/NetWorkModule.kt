package com.wxsoft.triapp.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.warriorsfly.core.data.remote.interceptor.TokenInterceptor
import com.warriorsfly.core.data.remote.log.LogInterceptor
import com.warriorsfly.core.data.remote.log.Logger
import com.wxsoft.triapp.BuildConfig
import com.wxsoft.triapp.BuildConfig.DEBUG
import com.wxsoft.triapp.data.prefs.PreferenceStorage
import com.wxsoft.triapp.data.remote.*
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
class NetWorkModule {


    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideTokenInterceptor(storage: PreferenceStorage): TokenInterceptor =
        TokenInterceptor(storage.token?:"")

    @Provides
    @Singleton
    fun provideOkhttp(interceptor: TokenInterceptor): OkHttpClient {

        val builder = OkHttpClient().newBuilder()
        //测试模式下打log
        if (DEBUG) {

            val logging = LogInterceptor(Logger())
            logging.setLevel(LogInterceptor.Level.BODY)
            builder.addInterceptor(logging)
        }
        //添加token interceptor
        builder.addInterceptor(interceptor)
        return builder.build()
    }

    @Provides
    @Singleton //providing tokenInterceptor like this
    @Named("untoken")
    fun provideOkhttpUnToken(): OkHttpClient {

        val builder = OkHttpClient().newBuilder()
        //测试模式下打log
        if (DEBUG) {

            val logging = LogInterceptor(Logger())
            logging.setLevel(LogInterceptor.Level.BODY)
            builder.addInterceptor(logging)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    @Named("untoken")
    fun provideRetrofit(gson: Gson, @Named("untoken") client: OkHttpClient): Retrofit {

        val builder = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.API_ENDPOINT)

        builder.client(client)

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofitWithToken(gson: Gson, client: OkHttpClient): Retrofit {

        val builder = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .baseUrl(BuildConfig.API_ENDPOINT)

        return builder.build()
    }

    @Provides
    fun provideIdentityApi(
        @Named("untoken") retrofit: Retrofit
    ): IdentityApi {

        return retrofit.create(IdentityApi::class.java)
    }

    @Provides
    fun provideDictionaryApi(retrofit: Retrofit): DictionaryApi {

        return retrofit.create(DictionaryApi::class.java)
    }

    @Provides
    fun providePatientApi(retrofit: Retrofit): PatientApi {

        return retrofit.create(PatientApi::class.java)
    }

    @Provides
    fun provideVitalApi(retrofit: Retrofit): VitalApi {

        return retrofit.create(VitalApi::class.java)
    }

    @Provides
    fun provideRescueRecordApi(retrofit: Retrofit): RescueRecordApi {

        return retrofit.create(RescueRecordApi::class.java)
    }


    @Provides
    fun provideRescueApi(retrofit: Retrofit): RescueApi {

        return retrofit.create(RescueApi::class.java)
    }

    @Provides
    fun provideStatisticApi(retrofit: Retrofit): StatisticApi {

        return retrofit.create(StatisticApi::class.java)
    }

    @Provides
    fun provideReportApi(retrofit: Retrofit): ReportApi {

        return retrofit.create(ReportApi::class.java)
    }

    @Provides
    fun provideRatingApi(retrofit: Retrofit): RatingApi {

        return retrofit.create(RatingApi::class.java)
    }
}