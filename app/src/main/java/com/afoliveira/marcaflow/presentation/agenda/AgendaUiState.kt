package com.afoliveira.marcaflow.presentation.agenda

import com.afoliveira.marcaflow.domain.model.Appointment

sealed interface AgendaUiState {

    data object Loading : AgendaUiState

    data class Success(
        val appointments: List<Appointment>
    ) : AgendaUiState

    data class Error(
        val message: String
    ) : AgendaUiState
}