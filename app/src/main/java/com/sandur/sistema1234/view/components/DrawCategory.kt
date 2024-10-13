package com.sandur.sistema1234.view.components

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sandur.sistema1234.model.ProductCategory
import com.sandur.sistema1234.view.labelCard.shop.ProductsActivity

@Composable
fun DrawCategory(item: ProductCategory) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                val intent = Intent(context, ProductsActivity::class.java).apply {
                    putExtra("idcategoria", item.idcategoria)
                    putExtra("nombre", item.nombre)
                }
                context.startActivity(intent)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = "https://servicios.campus.pe/${item.foto}",
                contentDescription = "Imagen de Categoría",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.7f))
            )
            Row (
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 30.dp)
            ) {
                Text(
                    text = item.idcategoria,
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White,
                    modifier = Modifier
                        .width(50.dp)
                        .align(Alignment.CenterVertically)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = item.nombre,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                    Text(
                        text = item.descripcion,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                    Text(
                        text = "Se encontraron: ${item.total}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            }
        }
    }
}