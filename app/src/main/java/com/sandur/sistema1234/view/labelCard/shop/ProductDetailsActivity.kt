package com.sandur.sistema1234.view.labelCard.shop

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sandur.sistema1234.model.ProductDetails
import com.sandur.sistema1234.ui.theme.Sistema1234Theme
import com.sandur.sistema1234.view.components.DrawProductDetails
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.ArrowLeftSolid
import kotlinx.coroutines.delay
import org.json.JSONArray

class ProductDetailsActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras
        val idProducto = bundle?.getString("idproducto")
        val nombre = bundle?.getString("nombre")

        enableEdgeToEdge()
        setContent {
            Sistema1234Theme {
                val isLoading = remember { mutableStateOf(true) }
                val errorMessage = remember { mutableStateOf<String?>(null) }
                var product by remember { mutableStateOf<ProductDetails?>(null) }

                LaunchedEffect(idProducto) {
                    delay(1000)
                    readService(idProducto.orEmpty(), isLoading, errorMessage) { producto ->
                        product = producto
                    }
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = nombre.orEmpty()) },
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
                    }
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        when {
                            isLoading.value -> {
                                Text(text = "Cargando información del producto...", modifier = Modifier.align(Alignment.CenterHorizontally))
                            }
                            errorMessage.value != null -> {
                                Text(
                                    text = "Error: ${errorMessage.value}",
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                            product != null -> {
                                DrawProductDetails(product!!)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun readService(
        idProducto: String,
        isLoading: MutableState<Boolean>,
        errorMessage: MutableState<String?>,
        onProductLoaded: (ProductDetails) -> Unit
    ) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/productos.php?idproducto=$idProducto"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("Volley", response)
                val product = fillProduct(response)
                onProductLoaded(product)
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

    private fun fillProduct(response: String): ProductDetails {
        val jsonArray = JSONArray(response)
        val jsonObject = jsonArray.getJSONObject(0)

        return ProductDetails(
            idproducto = jsonObject.getString("idproducto"),
            nombre = jsonObject.getString("nombre"),
            idproveedor = jsonObject.optString("idproveedor", ""),
            idcategoria = jsonObject.optString("idcategoria", ""),
            detalle = jsonObject.optString("detalle", ""),
            precio = jsonObject.optDouble("precio"),
            preciorebajado = jsonObject.optDouble("preciorebajado"),
            unidadesenexistencia = jsonObject.optInt("unidadesenexistencia"),
            unidadesenpedido = jsonObject.optString("unidadesenpedido", ""),
            nivelnuevopedido = jsonObject.optString("nivelnuevopedido", ""),
            enoferta = jsonObject.optString("enoferta", ""),
            imagenchica = jsonObject.optString("imagenchica", ""),
            imagengrande = jsonObject.optString("imagengrande", ""),
            habilitado = jsonObject.optString("habilitado", ""),
            descripcion = jsonObject.optString("descripcion", "")
        )
    }
}
