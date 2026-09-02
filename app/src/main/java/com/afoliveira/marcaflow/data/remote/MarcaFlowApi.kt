package com.afoliveira.marcaflow.data.remote

import com.afoliveira.marcaflow.data.remote.dto.AppointmentsResponse
import com.afoliveira.marcaflow.data.remote.dto.LoginRequest
import com.afoliveira.marcaflow.data.remote.dto.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MarcaFlowApi {

    @POST("api/mobile/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("api/mobile/appointments")
    suspend fun getAppointments(
        @Header("Authorization") authorization: String
    ): AppointmentsResponse
}