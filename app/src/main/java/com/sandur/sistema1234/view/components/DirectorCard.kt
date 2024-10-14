package com.sandur.sistema1234.view.components

import Director
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sandur.sistema1234.view.labelCard.directors.DirectorsUpdateActivity

@Composable
fun DirectorCard(director: Director) {
    val context = LocalContext.current // Obtener el contexto

    Card(
        modifier = Modifier
            .padding(top = 30.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            .clickable {
                // Aquí implementas la lógica para navegar a otra actividad
                val intent = Intent(context, DirectorsUpdateActivity::class.java).apply {
                    putExtra("iddirector", director.iddirector)
                    putExtra("nombres", director.nombres)
                    putExtra("peliculas", director.peliculas)
                }
                context.startActivity(intent)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
        ) {
            Text(text = "ID: ${director.iddirector}")
            Text(text = director.nombres, style = MaterialTheme.typography.titleLarge)
            Text(text = "Películas: ${director.peliculas}")
        }
    }
}
