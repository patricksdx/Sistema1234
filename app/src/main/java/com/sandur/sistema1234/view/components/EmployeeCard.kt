package com.sandur.sistema1234.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sandur.sistema1234.model.Employee

@Composable
fun EmployeeCard(employee: Employee) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .height(300.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (employee.foto != "null") {
                AsyncImage(
                    model = "https://servicios.campus.pe/fotos/" + employee.foto,
                    contentDescription = "Imagen de ${employee.nombres}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Black.copy(alpha = 0.65f))
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No se encontró imagen",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .align(Alignment.BottomStart)
            ) {
                Text(
                    text = "${employee.nombres} ${employee.apellidos}",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = employee.cargo,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = employee.telefono,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "${employee.ciudad} / ${employee.pais}",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}