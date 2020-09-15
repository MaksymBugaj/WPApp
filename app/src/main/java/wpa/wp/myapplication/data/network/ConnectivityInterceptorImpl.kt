package wpa.wp.myapplication.data.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptorImpl(
    private val application: Application
) : ConnectivityInterceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline())
            throw NoConnectivityException()
        return chain.proceed(chain.request())
    }

    private fun isOnline(): Boolean {
        val connectivityManager = application.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        var res = false
        connectivityManager.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                        res = when {
                            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                            else -> false
                        }
                    }
                } else {
                    TODO("VERSION.SDK_INT < M")
                }
            }
        return res
    }
}

class NoConnectivityException : IOException()