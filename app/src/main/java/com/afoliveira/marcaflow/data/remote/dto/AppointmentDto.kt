package com.afoliveira.marcaflow.data.remote.dto


data class AppointmentDto(
    val id: String,
    val customerName: String,
    val customerPhone: String?,
    val customerEmail: String?,
    val serviceName: String,
    val startAt: String,
    val endAt: String,
    val durationMinutes: Int,
    val totalPriceCents: Int,
    val status: String
)