package com.example.quiz

import android.app.Application
import com.example.quiz.ch8.CatNetworkService
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication : Application() {
    // 마치 static 처럼 이용가능

    var networkService: CatNetworkService

    val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(request)
        }.build()

    val retrofit: Retrofit
        get() = Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    init {
        networkService = retrofit.create(CatNetworkService::class.java)
    }

    companion object {
        val auth: FirebaseAuth by lazy {
            Firebase.auth
        }

        val db: FirebaseFirestore by lazy {
            FirebaseFirestore.getInstance()
        }

        // 로그인 성공시에 로그인 id, 앱 전역에서 사용해서
        var email: String? = null
        fun checkAuth(): Boolean {
            val currentUser = auth.currentUser
            return currentUser?.let {
                email = currentUser.email
                if (currentUser.isEmailVerified) {
                    true
                } else {
                    false
                }
            } ?: let {
                false
            }
        }
    }
}