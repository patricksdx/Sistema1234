package com.sandur.sistema1234.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sandur.sistema1234.model.ProductDetails

@Composable
fun DrawProductDetails(item: ProductDetails) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        if (item.imagengrande != "null") {
            AsyncImage(
                model = "https://servicios.campus.pe/" + item.imagengrande,
                contentDescription = "Imagen de ${item.nombre}",
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        } else {
            Text(
                text = "No se encontró imagen",
                color = MaterialTheme.colorScheme.error,
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 150.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Precio
        if (item.preciorebajado.isNotEmpty() && item.preciorebajado != "0") {
            Text(
                text = "Precio Rebajado: S/${item.preciorebajado}",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        } else {
            Text(
                text = "Precio: S/${item.precio}",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Información de stock
        Text(
            text = "Unidades en existencia: ${item.unidadesenexistencia}",
            style = MaterialTheme.typography.bodySmall,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        // Si está en oferta
        if (item.enoferta == "1") {
            Text(
                text = "¡En oferta!",
                color = Color.Red,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        // Descripción del producto
        if (item.descripcion != ""){
            Text(
                text = item.descripcion,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Text(
                text = "No se encontró descripción",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        Box (
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Agregar Al Carrito")
            }
        }
    }
}
