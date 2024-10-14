package com.sandur.sistema1234.view.labelCard.user

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.sandur.sistema1234.ui.theme.Sistema1234Theme
import com.sandur.sistema1234.utils.UserStore
import com.sandur.sistema1234.view.labelCard.login.LoginActivity
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.WindowCloseSolid
import kotlinx.coroutines.launch

class ProfileActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var showAlertDialog by remember { mutableStateOf(false) }

            Sistema1234Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Profile") }
                        )
                    }
                ) { innerPadding ->
                    Column(
                        Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = { showAlertDialog = true }
                        ) {
                            Text(text = "Cerrar Sesión")
                        }
                    }
                }

                // Diálogo de confirmación
                if (showAlertDialog) {
                    AlertDialog(
                        onDismissRequest = { showAlertDialog = false },
                        title = { Text(text = "Cerrar Sesión") },
                        text = {
                            Text(
                                modifier = Modifier.padding(horizontal = 30.dp),
                                text = "¿Desea cerrar sesión?",
                                textAlign = TextAlign.Center
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = LineAwesomeIcons.WindowCloseSolid,
                                modifier = Modifier.size(24.dp),
                                contentDescription = "Cerrar"
                            )
                        },
                        confirmButton = {
                            ElevatedButton(
                                onClick = {
                                    cerrarSesion()
                                    showAlertDialog = false // Cerrar el diálogo después de confirmar
                                }
                            ) {
                                Text(text = "Sí")
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = { showAlertDialog = false } // Cerrar el diálogo si se cancela
                            ) {
                                Text(text = "No")
                            }
                        }
                    )
                }
            }
        }
    }

    private fun cerrarSesion() {
        val userStore = UserStore(this)
        lifecycleScope.launch {
            userStore.saveUser("") // Limpiar el usuario
            finish() // Cerrar ProfileActivity
            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java)) // Iniciar MainActivity
        }
    }
}