package com.sandur.sistema1234.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            .height(340.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            if (employee.foto != "null") {
                AsyncImage(
                    model = "https://servicios.campus.pe/fotos/" + employee.foto,
                    contentDescription = "Imagen de ${employee.nombres}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(200.dp)
                        .padding(bottom = 10.dp)
                )
            } else {
                Text(
                    text = "No se encontró imagen",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 60.dp)
                )
            }

            Text(text = "${employee.nombres} ${employee.apellidos}")
            Text(text = "${employee.cargo}")
            Text(text = "${employee.telefono}")
            Text(text = "${employee.ciudad} / ${employee.pais}")
        }
    }
}