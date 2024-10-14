package com.sandur.sistema1234.view.labelCard.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sandur.sistema1234.ui.theme.Sistema1234Theme
import com.sandur.sistema1234.utils.Url
import com.sandur.sistema1234.utils.UserStore
import com.sandur.sistema1234.view.labelCard.user.ProfileActivity
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.ArrowLeftSolid
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {

    var saveSession: Boolean = false

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            var user by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var checkBoxState by remember { mutableStateOf(false) }
            var loginError by remember { mutableStateOf("") }

            Sistema1234Theme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Login") },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(
                                        imageVector = LineAwesomeIcons.ArrowLeftSolid,
                                        modifier = Modifier.size(24.dp),
                                        contentDescription = "Atrás"
                                    )
                                }
                            }
                        )
                    }) { innerPadding ->
                    Column(
                        Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        OutlinedTextField(
                            value = user,
                            onValueChange = { user = it },
                            label = { Text("Ingrese usuario") }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Ingrese contraseña") },
                            visualTransformation = PasswordVisualTransformation()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            loginError = "" // Reiniciar mensaje de error
                            CheckLogin(user, password) { success ->
                                if (success) {
                                    //startActivity(Intent(this@LoginActivity, DirectorsActivity::class.java))
                                    //finish() // Cerrar LoginActivity después de navegar
                                } else {
                                    // Si el inicio de sesión falla, se maneja en verifyLogin
                                }
                            }
                        }) {
                            Text(text = "Iniciar Sesión")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "¿Desea guardar el inicio de sesión?")
                            Checkbox(checked = checkBoxState, onCheckedChange = {
                                checkBoxState = it
                                saveSession = it
                            })
                        }
                        if (loginError.isNotEmpty()) {
                            Text(text = loginError, color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }

    private fun CheckLogin(user: String, password: String, onResult: (Boolean) -> Unit) {
        val queue = Volley.newRequestQueue(this)
        val url = Url.BASE_URL+"iniciarsesion.php"

        val stringRequest = object : StringRequest(
            Method.POST, url,
            { response ->
                Log.d("VOLLEY", response)
                verifyLogin(response)
            },
            { error ->
                Log.e("VOLLEY", "Ocurrió un error", error)
                onResult(false) // Si hay error en la conexión
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                return hashMapOf(
                    "usuario" to user,
                    "clave" to password
                )
            }
        }
        queue.add(stringRequest)
    }

    private fun verifyLogin(response: String) {
        when(response) {
            "-1" -> {
                Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
            }
            "-2" -> {
                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ProfileActivity::class.java))
                verifySaveSession(response)
            }
        }
    }

    private fun verifySaveSession(response: String){
        if (saveSession) {
            val userStore = UserStore(this)
            lifecycleScope.launch {
                userStore.saveUser(response)
            }
        }
    }
}