package com.example.projecte_m07.habitos

import com.example.recyclerview.habitos.Habito
import com.example.recyclerview.habitos.HabitoCreate
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface HabitsService {
    @GET("habitos/{userId}")
    suspend fun getHabitos(@Path("userId") userId: Int): List<Habito>

    @POST("habitos/{userId}")
    suspend fun createHabito(@Path("userId") userId: Int, @Body habito: HabitoCreate): Habito

    @PUT("habitos/{id}")
    suspend fun updateHabito(@Path("id") id: Int, @Body habito: HabitoCreate): Habito

    @DELETE("habitos/{id}")
    suspend fun deleteHabito(@Path("id") id: Int): Response<Unit>

    @POST("register")
    suspend fun register(@Body usuario: UsuarioCreate): UsuarioResponse

    @POST("login")
    suspend fun login(@Body usuario: UsuarioLogin): UsuarioResponse

    @DELETE("usuarios/{id}")
    suspend fun deleteUsuario(@Path("id") id: Int): Response<Unit>

    @PATCH("habitos/{id}/completar")
    suspend fun completarHabito(@Path("id") id: Int, @Body data: Map<String, Boolean>): Habito

    @PUT("usuarios/{id}")
    suspend fun updateUsuario(@Path("id") id: Int, @Body usuario: UsuarioUpdateRequest): UsuarioResponse

    @POST("historial/{userId}")
    suspend fun addHistorial(@Path("userId") userId: Int, @Body entrada: HistorialCreate): Map<String, String>

    @GET("historial/{userId}")
    suspend fun getHistorial(@Path("userId") userId: Int): List<HistorialEntry>
}

class HabitosAPI{
    companion object  {
        private var mAPI : HabitsService? = null
        private const val BASE_URL = "https://habitos-backend-production-115b.up.railway.app/"
        @Synchronized
        fun API(): HabitsService {
            if (mAPI == null){
                val gsonHourMinute = GsonBuilder()
                    .setDateFormat("HH:mm")
                    .create()
                mAPI = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gsonHourMinute))
                    .baseUrl(BASE_URL)
                    .client(OkHttpClient())
                    .build()
                    .create(HabitsService::class.java)
            }
            return mAPI!!
        }
    }
}