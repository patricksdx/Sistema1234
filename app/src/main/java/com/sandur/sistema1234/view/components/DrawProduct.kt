package com.sandur.sistema1234.view.components

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sandur.sistema1234.model.Product
import com.sandur.sistema1234.view.labelCard.shop.ProductDetailsActivity
import java.text.DecimalFormat

@Composable
fun DrawProductItem(producto: Product) {
    val context = LocalContext.current

    // Formato para mostrar los precios con dos decimales
    val decimalFormat = DecimalFormat("#,##0.00")

    // Asegúrate de que precioRebajado no sea null y calcular el porcentaje de descuento
    val porcentajeDescuento = if (producto.precioRebajado != null && producto.precioRebajado > 0) {
        ((producto.precio - producto.precioRebajado) / producto.precio) * 100
    } else {
        0.0
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .height(310.dp)
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
                .padding(15.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                if (producto.imagen != "null") {
                    AsyncImage(
                        model = "https://servicios.campus.pe/" + producto.imagen,
                        contentDescription = "Imagen de ${producto.nombre}",
                        modifier = Modifier
                            .fillMaxWidth()
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

                if (porcentajeDescuento > 0) {
                    Text(
                        text = "${decimalFormat.format(porcentajeDescuento)}%",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .background(
                                color = MaterialTheme.colorScheme.error,
                                shape = RoundedCornerShape(50)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 15.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(5.dp))

            if (producto.precioRebajado != null && producto.precioRebajado > 0) {
                Text(
                    text = "S/ ${decimalFormat.format(producto.precio)}",
                    style = MaterialTheme.typography.titleMedium.copy(textDecoration = TextDecoration.LineThrough),
                    fontSize = 13.sp,
                )
                Text(
                    text = "S/ ${decimalFormat.format(producto.precioRebajado)}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 13.sp
                )
            } else {
                Text(
                    text = "S/ ${decimalFormat.format(producto.precio)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 13.sp,
                )
            }
        }
    }
}