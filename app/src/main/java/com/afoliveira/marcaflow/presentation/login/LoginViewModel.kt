package com.afoliveira.marcaflow.presentation.login

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

class LoginViewModel : ViewModel() {

    private val repository = MarcaFlowRepository(
        api = RetrofitClient.api
    )

    private val _uiState =
        MutableStateFlow<LoginUiState>(LoginUiState.Idle)

    val uiState: StateFlow<LoginUiState> =
        _uiState.asStateFlow()

    fun login(
        email: String,
        password: String
    ) {

        if (email.isBlank()) {
            _uiState.value = LoginUiState.Error(
                "Introduza o e-mail."
            )
            return
        }

        if (password.isBlank()) {
            _uiState.value = LoginUiState.Error(
                "Introduza a palavra-passe."
            )
            return
        }

        viewModelScope.launch {

            _uiState.value = LoginUiState.Loading

            try {

                val response = repository.login(
                    email = email.trim(),
                    password = password
                )

                _uiState.value = LoginUiState.Success(
                    token = response.token,
                    userName = response.user.name,
                    businessName = response.business.name
                )

            } catch (exception: HttpException) {

                val message =
                    when (exception.code()) {

                        401 ->
                            "E-mail ou palavra-passe inválidos."

                        403 ->
                            "Este utilizador não possui acesso a um negócio."

                        else ->
                            "Erro no servidor (${exception.code()})."
                    }

                _uiState.value =
                    LoginUiState.Error(message)

            } catch (exception: IOException) {

                _uiState.value = LoginUiState.Error(
                    "Não foi possível ligar ao MarcaFlow. Verifique a internet."
                )

            } catch (exception: Exception) {

                _uiState.value = LoginUiState.Error(
                    "Ocorreu um erro inesperado."
                )
            }
        }
    }

    fun clearError() {
        if (_uiState.value is LoginUiState.Error) {
            _uiState.value = LoginUiState.Idle
        }
    }
}