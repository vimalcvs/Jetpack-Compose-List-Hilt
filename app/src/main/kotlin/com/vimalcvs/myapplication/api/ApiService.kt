package com.vimalcvs.myapplication.api

import com.vimalcvs.myapplication.model.ModelPost
import retrofit2.Response

import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): Response<List<ModelPost>>
}
