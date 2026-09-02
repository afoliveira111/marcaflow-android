package com.afoliveira.marcaflow.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

private val TitleColor = Color(0xFF111827)
private val BodyColor = Color(0xFF374151)
private val SecondaryColor = Color(0xFF6B7280)
private val FieldBorderColor = Color(0xFF9CA3AF)
private val FocusedBorderColor = Color(0xFF7189E8)
private val ButtonColor = Color(0xFFA9BDF8)
private val ButtonTextColor = Color(0xFF172033)

@Composable
fun LoginScreen(
    onLoginSuccess: (
        token: String,
        userName: String,
        businessName: String
    ) -> Unit,
    viewModel: LoginViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    LaunchedEffect(uiState) {
        val state = uiState

        if (state is LoginUiState.Success) {
            onLoginSuccess(
                state.token,
                state.userName,
                state.businessName
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "MarcaFlow",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = TitleColor
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = "Gestão de agendamentos",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = BodyColor
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                viewModel.clearError()
            },
            label = {
                Text(
                    text = "E-mail"
                )
            },
            singleLine = true,
            enabled = uiState !is LoginUiState.Loading,
            textStyle = TextStyle(
                color = TitleColor,
                fontSize = 16.sp
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TitleColor,
                unfocusedTextColor = TitleColor,
                disabledTextColor = SecondaryColor,

                focusedLabelColor = FocusedBorderColor,
                unfocusedLabelColor = SecondaryColor,

                focusedBorderColor = FocusedBorderColor,
                unfocusedBorderColor = FieldBorderColor,

                cursorColor = FocusedBorderColor,

                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                viewModel.clearError()
            },
            label = {
                Text(
                    text = "Palavra-passe"
                )
            },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            enabled = uiState !is LoginUiState.Loading,
            textStyle = TextStyle(
                color = TitleColor,
                fontSize = 16.sp
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TitleColor,
                unfocusedTextColor = TitleColor,
                disabledTextColor = SecondaryColor,

                focusedLabelColor = FocusedBorderColor,
                unfocusedLabelColor = SecondaryColor,

                focusedBorderColor = FocusedBorderColor,
                unfocusedBorderColor = FieldBorderColor,

                cursorColor = FocusedBorderColor,

                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        if (uiState is LoginUiState.Error) {

            Text(
                text = (uiState as LoginUiState.Error).message,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )
        }

        Button(
            onClick = {
                viewModel.login(
                    email = email,
                    password = password
                )
            },
            enabled = uiState !is LoginUiState.Loading,
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonColor,
                contentColor = ButtonTextColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {

            if (uiState is LoginUiState.Loading) {

                CircularProgressIndicator(
                    modifier = Modifier.height(24.dp),
                    strokeWidth = 2.dp,
                    color = ButtonTextColor
                )

            } else {

                Text(
                    text = "Entrar",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ButtonTextColor
                )
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "Acesso reservado aos responsáveis pelo negócio.",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = SecondaryColor,
            modifier = Modifier.align(
                Alignment.CenterHorizontally
            )
        )
    }
}