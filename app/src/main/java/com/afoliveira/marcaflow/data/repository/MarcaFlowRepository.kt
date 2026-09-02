package com.afoliveira.marcaflow.data.repository

import com.afoliveira.marcaflow.data.remote.MarcaFlowApi
import com.afoliveira.marcaflow.data.remote.dto.LoginRequest
import com.afoliveira.marcaflow.data.remote.dto.LoginResponse
import com.afoliveira.marcaflow.domain.model.Appointment
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MarcaFlowRepository(
    private val api: MarcaFlowApi
) {

    suspend fun login(
        email: String,
        password: String
    ): LoginResponse {

        return api.login(
            LoginRequest(
                email = email,
                password = password
            )
        )
    }

    suspend fun getAppointments(
        token: String
    ): List<Appointment> {

        val response = api.getAppointments(
            authorization = "Bearer $token"
        )

        return response.appointments.map { dto ->

            val dateTime = Instant
                .parse(dto.startAt)
                .atZone(ZoneId.systemDefault())

            Appointment(
                id = dto.id,
                customerName = dto.customerName,
                customerPhone = dto.customerPhone,
                customerEmail = dto.customerEmail,
                serviceName = dto.serviceName,
                date = dateTime.format(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")
                ),
                startTime = dateTime.format(
                    DateTimeFormatter.ofPattern("HH:mm")
                ),
                durationMinutes = dto.durationMinutes,
                totalPriceCents = dto.totalPriceCents,
                status = dto.status
            )
        }
    }
}