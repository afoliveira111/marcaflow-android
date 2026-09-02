package com.afoliveira.marcaflow.presentation.agenda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afoliveira.marcaflow.data.remote.RetrofitClient
import com.afoliveira.marcaflow.data.repository.MarcaFlowRepository
import java.io.IOException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class AgendaViewModel : ViewModel() {

    private val repository = MarcaFlowRepository(
        api = RetrofitClient.api
    )

    private val _uiState =
        MutableStateFlow<AgendaUiState>(
            AgendaUiState.Loading
        )

    val uiState: StateFlow<AgendaUiState> =
        _uiState.asStateFlow()

    fun loadAgenda(
        token: String
    ) {

        viewModelScope.launch {

            _uiState.value =
                AgendaUiState.Loading

            try {

                val appointments =
                    repository.getAppointments(
                        token = token
                    )

                _uiState.value =
                    AgendaUiState.Success(
                        appointments = appointments
                    )

            } catch (exception: HttpException) {

                val message =
                    when (exception.code()) {

                        401 ->
                            "Sessão inválida ou expirada."

                        403 ->
                            "Utilizador sem acesso ao negócio."

                        else ->
                            "Erro no servidor (${exception.code()})."
                    }

                _uiState.value =
                    AgendaUiState.Error(
                        message = message
                    )

            } catch (exception: IOException) {

                _uiState.value =
                    AgendaUiState.Error(
                        message = "Não foi possível ligar ao MarcaFlow."
                    )

            } catch (exception: Exception) {

                _uiState.value =
                    AgendaUiState.Error(
                        message = "Ocorreu um erro inesperado."
                    )
            }
        }
    }
}