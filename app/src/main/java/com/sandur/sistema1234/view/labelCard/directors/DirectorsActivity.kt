package com.sandur.sistema1234.view.labelCard.directors

import Director
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sandur.sistema1234.R
import com.sandur.sistema1234.ui.theme.Sistema1234Theme
import com.sandur.sistema1234.view.components.DirectorCard
import com.sandur.sistema1234.view.components.headerImage
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.ArrowLeftSolid
import kotlinx.coroutines.delay
import org.json.JSONArray

class DirectorsActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sistema1234Theme {
                val directors = remember { mutableStateOf<List<Director>>(emptyList()) }
                val isLoading = remember { mutableStateOf(true) }
                val errorMessage = remember { mutableStateOf<String?>(null) }

                LaunchedEffect(Unit) {
                    delay(1000)
                    loadDirectors(directors, isLoading, errorMessage)
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Directores") },
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
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            startActivity(Intent(this, DirectorsInsertActivity::class.java))
                        }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null
                            )
                        }
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {
                        Box(
                            contentAlignment = Alignment.BottomEnd,
                            modifier = Modifier.height(200.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.5f))
                            )
                            headerImage(R.drawable.directores, "Directores de cine", "")
                        }
                        Column(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .padding(bottom = 30.dp)
                        ) {
                            if (isLoading.value) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Cargando directores...",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .padding(vertical = 20.dp),
                                    )
                                }
                            } else if (errorMessage.value != null) {
                                Text(
                                    text = "Error: ${errorMessage.value}",
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            } else {
                                directors.value.forEach { director ->
                                    DirectorCard(director)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loadDirectors(
        directorsState: MutableState<List<Director>>,
        isLoading: MutableState<Boolean>,
        errorMessage: MutableState<String?>
    ) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/directores.php"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("Volley", response)
                val directors = processDirectorsResponse(response)
                directorsState.value = directors
                isLoading.value = false
            },
            { error ->
                Log.e("Volley", "Error en la solicitud: ${error.message}")
                errorMessage.value = error.message
                isLoading.value = false
            }
        )
        queue.add(stringRequest)
    }

    private fun processDirectorsResponse(response: String): List<Director> {
        val jsonArray = JSONArray(response)
        val directors = mutableListOf<Director>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val director = Director(
                iddirector = jsonObject.optString("iddirector", ""),
                nombres = jsonObject.optString("nombres", "N/A"),
                peliculas = jsonObject.optString("peliculas", "N/A")
            )
            directors.add(director)
        }
        return directors
    }
}
