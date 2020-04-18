package br.com.raphael.heroismarvel.remote.util

import android.annotation.SuppressLint
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import javax.net.ssl.SSLSocketFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


object HttpClientUtils {
    private val trustAllCerts =
        arrayOf<TrustManager>(
            trustManager
        )//Do nothing

    //Do nothing
    val trustManager: X509TrustManager
        get() = object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(
                chain: Array<X509Certificate>,
                authType: String
            ) {
                //Do nothing
            }

            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(
                chain: Array<X509Certificate>,
                authType: String
            ) {
                //Do nothing
            }

            override fun getAcceptedIssuers(): Array<X509Certificate?> {
                return arrayOfNulls(0)
            }
        }

    private val sSLContext: SSLContext?
        get() {
            val sslContext: SSLContext
            return try {
                sslContext = SSLContext.getInstance("TLS")
                sslContext.init(
                    null, trustAllCerts,
                    SecureRandom()
                )
                sslContext
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
                null
            } catch (e: KeyManagementException) {
                e.printStackTrace()
                null
            }
        }

    val sSLSocketFactory: SSLSocketFactory?
        get() {
            val sslContext = sSLContext
            return sslContext?.socketFactory
        }
}
