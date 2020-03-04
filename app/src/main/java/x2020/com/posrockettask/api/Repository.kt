package x2020.com.posrockettask.api

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val URL = "https://posrocket.free.beeceptor.com"

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
//        var req = chain.request()
//        val url = req.url().newBuilder().addQueryParameter("key", "key").build()
//        req = req.newBuilder().url(url).build()
        return chain.proceed(chain.request())
    }
}

val networkModule = module {
    factory { AuthInterceptor() }
    factory { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    factory { provideForecastApi(get()) }

}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder().build()
}

fun provideForecastApi(retrofit: Retrofit): POSRocketServiceInterface =
    retrofit.create(POSRocketServiceInterface::class.java)


val serverRepository = module {
    factory { Repository(get(), get()) }
}

class Repository(
    private val service: POSRocketServiceInterface,
    private val appContext: Context? = null
) {
    suspend fun discounts() = try {
        service.discounts()
    } catch (e: Exception) {
        appContext?.let {
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(appContext, "$e", Toast.LENGTH_SHORT).show()
            }
        }
        null
    }

    suspend fun extraCharges() = try {
        service.extraCharges()
    } catch (e: Exception) {
        appContext?.let {
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(appContext, "$e", Toast.LENGTH_SHORT).show()
            }
        }
        null
    }
}