package com.sandur.sistema1234.view.components

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sandur.sistema1234.model.Product
import com.sandur.sistema1234.view.labelCard.shop.ProductDetailsActivity

@Composable
fun DrawProductItem(producto: Product) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clickable {
                val intent = Intent(context, ProductDetailsActivity::class.java).apply {
                    putExtra("idproducto", producto.idproducto)
                    putExtra("nombre", producto.nombre)
                }
                context.startActivity(intent)
            },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (producto.imagen != "null") {
                AsyncImage(
                    model = "https://servicios.campus.pe/" + producto.imagen,
                    contentDescription = "Imagen de ${producto.nombre}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(135.dp)
                )
            } else {
                Text(
                    text = "No se encontró imagen",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 60.dp)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Precio: ${producto.precio}",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp,
            )
            producto.precioRebajado?.let {
                Text(
                    text = "Precio Rebajado: $it",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 14.sp
                )
            }
        }
    }
}
