package com.sandur.sistema1234.view.labelCard.directors

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sandur.sistema1234.ui.theme.Sistema1234Theme
import com.sandur.sistema1234.utils.Url
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.ArrowLeftSolid

class DirectorsInsertActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            var nombres by remember { mutableStateOf("") }
            var peliculas by remember { mutableStateOf("") }

            Sistema1234Theme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Registrar Director") },
                            navigationIcon = {
                                IconButton(onClick = {finish()}) {
                                    Icon(
                                        imageVector = LineAwesomeIcons.ArrowLeftSolid,
                                        modifier = Modifier
                                            .size(24.dp),
                                        contentDescription = ""
                                    )
                                }
                            }
                        )
                    }) { innerPadding ->
                    Column (
                        Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center){
                        OutlinedTextField(value = nombres, onValueChange = {
                            nombres = it
                        },
                            label = { Text("Nombre del director") })
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(value = peliculas, onValueChange = {
                            peliculas = it
                        },
                            label = { Text("Peliculas") })
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            insertDirector(nombres, peliculas)
                        }) {
                            Text(text = "Registrar")
                        }
                    }
                }
            }
        }
    }

    private fun insertDirector(nombres: String, peliculas: String) {
        Log.d("VOLLEY", nombres + " - " + peliculas)
        val queue = Volley.newRequestQueue(this)
        val url = Url.BASE_URL + "directoresinsert.php"

        val stringRequest = object: StringRequest(
            Request.Method.POST, url,
            { response ->
                Log.d("VOLLEY", response)
                startActivity(Intent(this, DirectorsActivity::class.java))
            },{
                // Manejo de errores (opcional)
                Log.e("VOLLEY", "Error occurred", it)
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["nombres"] = nombres
                params["peliculas"] = peliculas
                return params
            }
        }
        queue.add(stringRequest)
    }
}