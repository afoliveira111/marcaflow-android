package com.afoliveira.marcaflow.domain.model

data class Appointment(
    val id: String,
    val customerName: String,
    val customerPhone: String?,
    val customerEmail: String?,
    val serviceName: String,
    val date: String,
    val startTime: String,
    val durationMinutes: Int,
    val totalPriceCents: Int,
    val status: String
)