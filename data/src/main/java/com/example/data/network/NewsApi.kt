package com.example.data.network

import com.example.data.BuildConfig
import com.example.domain.models.articles.NewsData
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.security.cert.X509Certificate
import javax.net.ssl.*
import javax.security.cert.CertificateException


interface NewsAPI {

    @GET("top-headlines")
    fun searchNews(@Query("sources") q: String): Single<NewsData>

    companion object Factory {
        fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
            try {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }

                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<java.security.cert.X509Certificate>,
                        authType: String
                    ) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<java.security.cert.X509Certificate>,
                        authType: String
                    ) {
                    }
                })

                // Install the all-trusting trust manager
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())

                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = sslContext.getSocketFactory()

                val builder = OkHttpClient.Builder()
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier(object : HostnameVerifier {
                    override fun verify(hostname: String, session: SSLSession): Boolean {
                        return true
                    }
                })
                return builder
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }

        fun create(): NewsAPI {
            val clientBuilder = getUnsafeOkHttpClient()


            val headerAuthorizationInterceptor = Interceptor { chain ->
                var request: Request = chain.request()
                val url =
                    request.url().newBuilder().addQueryParameter("apiKey", "ec784c67daaf46949c7972ce50337999").build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }


            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

            clientBuilder
                .addInterceptor(headerAuthorizationInterceptor)
                .addInterceptor(loggingInterceptor)

            val retrofit = Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
                .client(clientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(NewsAPI::class.java)
        }
    }

}