package com.sandur.sistema1234.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import com.sandur.sistema1234.R
import com.sandur.sistema1234.ui.theme.Sistema1234Theme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sistema1234Theme {
                Scaffold(
                    //topBar = {
                    //    TopAppBar(title = { Text(text = "") })
                    //}
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(all = dimensionResource(id = R.dimen.size_4)),
                                textAlign = TextAlign.Center,
                                text = "“El secreto de la felicidad no se encuentra en la búsqueda de más, sino en el desarrollo de la capacidad para disfrutar de menos”",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "Sócrates",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = dimensionResource(id = R.dimen.size_22)),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Bienvenido",
                                style = MaterialTheme.typography.displayLarge,
                                modifier = Modifier.padding(all = dimensionResource(id = R.dimen.size_2)),
                                textAlign = TextAlign.Center
                            )
                            Button(
                                onClick = {
                                    startActivity(Intent(this@MainActivity, BeginActivity::class.java))
                                }
                            ) {
                                Text(text = "Empezar")
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = dimensionResource(id = R.dimen.size_3)),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Text(
                                text = "Todos los derechos reservados",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }
            }
        }
    }
}
