package wpa.wp.myapplication.data.network

import com.google.gson.GsonBuilder
import io.reactivex.Flowable
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import timber.log.Timber
import wpa.wp.myapplication.data.db.converters.*
import wpa.wp.myapplication.data.db.entity.details.QuizDetails
import wpa.wp.myapplication.data.db.entity.quiz.Quiz


interface ApiService {


    @GET("quizzes/0/100/")
    fun getQuizes(): Single<Quiz>

    @GET("quiz/{id_quizu}/0")
    fun getSpecificQuiz(
        @Path("id_quizu") id: Long
    ): Single<QuizDetails>

    @GET("quiz/{id_quizu}/0")
    fun getSpecificQuiz2(
        @Path("id_quizu") id: Long
    ): Flowable<QuizDetails>


    companion object{
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): ApiService {
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url
                    .newBuilder()
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Timber.tag("OkHttp").d(message)
                }
            })
            logging.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .addInterceptor(logging)
                .build()

            val gson =
                GsonBuilder()
                    .registerTypeAdapterFactory(NullIntToEmptyAdapterFactory<Any?>())
                    .registerTypeAdapterFactory(NullLongToEmptyAdapterFactory<Any?>())
                    .registerTypeAdapterFactory(NullDoubleToEmptyAdapterFactory<Any?>())
                    .registerTypeAdapterFactory(NullBooleanToEmptyAdapterFactory<Any?>())

                    .create()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://quiz.o2.pl/api/v1/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
        }
    }
}