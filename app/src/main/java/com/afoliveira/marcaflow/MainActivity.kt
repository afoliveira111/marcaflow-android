package com.afoliveira.marcaflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.afoliveira.marcaflow.presentation.agenda.AgendaScreen
import com.afoliveira.marcaflow.presentation.login.LoginScreen
import com.afoliveira.marcaflow.ui.theme.MarcaFlowTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            MarcaFlowTheme {

                var token by remember {
                    mutableStateOf<String?>(null)
                }

                var userName by remember {
                    mutableStateOf("")
                }

                var businessName by remember {
                    mutableStateOf("")
                }

                val currentToken = token

                if (currentToken == null) {

                    LoginScreen(
                        onLoginSuccess = {
                                receivedToken,
                                receivedUserName,
                                receivedBusinessName ->

                            token =
                                receivedToken

                            userName =
                                receivedUserName

                            businessName =
                                receivedBusinessName
                        }
                    )

                } else {

                    AgendaScreen(
                        token =
                            currentToken,
                        businessName =
                            businessName
                    )
                }
            }
        }
    }
}