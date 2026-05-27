package com.example.lb3

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

data class Product(val id: Int, val name: String, val price: Double, val category: String)
data class OrderItem(val id: Int, val price: Double)
data class OrderRequest(val items: List<OrderItem>, val total: Double)
data class OrderResponse(val id: Int, val user_id: Int, val total: Double, val status: String, val date: String)
data class AuthRequest(val username: String, val password: String)
data class AuthResponse(val token: String, val username: String, val message: String?)

interface ApiService {
    @POST("auth/register")
    suspend fun register(@Body request: AuthRequest): AuthResponse

    @POST("auth/login")
    suspend fun login(@Body request: AuthRequest): AuthResponse

    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("products/recommendations")
    suspend fun getRecommendations(@Header("Authorization") token: String): List<Product>

    @POST("orders")
    suspend fun createOrder(@Header("Authorization") token: String, @Body request: OrderRequest): OrderResponse

    @GET("orders")
    suspend fun getOrders(@Header("Authorization") token: String): List<OrderResponse>
}

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:3000/api/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}