package com.vimalcvs.myapplication.repository

import com.vimalcvs.myapplication.api.ApiService
import com.vimalcvs.myapplication.model.ModelPost
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val apiService: ApiService) {
    suspend fun getPosts(): List<ModelPost> {
        val response = apiService.getPosts()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception(response.code().toString())
        }
    }
}