package com.sandur.sistema1234.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.sandur.sistema1234.R
import com.sandur.sistema1234.ui.theme.Sistema1234Theme
import com.sandur.sistema1234.view.components.headerImage

class BeginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sistema1234Theme {
                Scaffold { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            contentAlignment = Alignment.BottomEnd,
                            modifier = Modifier.height(300.dp)
                        ){
                            Box(modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.5f)))
                            headerImage(R.drawable.begin_image, "Mujer Comprando", "Comenzar")
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(all = dimensionResource(id = R.dimen.size_3)),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Empezar",
                                style = MaterialTheme.typography.displayLarge
                            )
                            Text(
                                text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(innerPadding),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(onClick = {
                                    startActivity(Intent(this@BeginActivity, TermsAndConditionsActivity::class.java))
                                }) {
                                    Text(text = "Terms and Conditions")
                                }
                                Button(onClick = {
                                    startActivity(Intent(this@BeginActivity, HomeActivity::class.java))
                                }) {
                                    Text(text = "Start")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
