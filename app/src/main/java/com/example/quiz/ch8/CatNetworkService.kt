package com.example.quiz.ch8

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface CatNetworkService {
    @GET("images/search")
    fun getCatList(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Call<List<Cat>> // 반환 타입을 DTO 리스트로 명시
}