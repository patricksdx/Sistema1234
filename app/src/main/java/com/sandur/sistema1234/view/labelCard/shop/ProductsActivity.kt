package com.sandur.sistema1234.view.labelCard.shop

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sandur.sistema1234.model.Product
import com.sandur.sistema1234.ui.theme.Sistema1234Theme
import com.sandur.sistema1234.view.components.DrawProductItem
import kotlinx.coroutines.delay
import org.json.JSONArray

class ProductsActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras
        val idCategoria = bundle?.getString("idcategoria")
        val nombreCategoria = bundle?.getString("nombre")

        enableEdgeToEdge()
        setContent {
            Sistema1234Theme {

                val productos = remember { mutableStateOf<List<Product>>(emptyList()) }
                val isLoading = remember { mutableStateOf(true) }
                val errorMessage = remember { mutableStateOf<String?>(null) }

                LaunchedEffect(idCategoria) {
                    delay(1000)
                    readService(idCategoria.orEmpty(), productos, isLoading, errorMessage)
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = nombreCategoria.orEmpty()) }
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
                                Text(text = "Cargando productos...", modifier = Modifier.align(Alignment.CenterHorizontally))
                            }
                            errorMessage.value != null -> {
                                Text(
                                    text = "Error: ${errorMessage.value}",
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                            else -> {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    modifier = Modifier.fillMaxSize(),
                                    contentPadding = PaddingValues(8.dp)
                                ) {
                                    items(productos.value) { producto ->
                                        DrawProductItem(producto)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun readService(
        idCategoria: String,
        productos: MutableState<List<Product>>,
        isLoading: MutableState<Boolean>,
        errorMessage: MutableState<String?>
    ) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/productos.php?idcategoria=$idCategoria"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("Volley", response)
                fillProducts(response, productos)
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

    private fun fillProducts(response: String, productos: MutableState<List<Product>>) {
        val jsonArray = JSONArray(response)
        val productList = mutableListOf<Product>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val producto = Product(
                idproducto = jsonObject.getString("idproducto"),
                nombre = jsonObject.getString("nombre"),
                precio = jsonObject.getString("precio"),
                imagen = jsonObject.getString("imagenchica"),
                precioRebajado = jsonObject.optString("preciorebajado", null)
            )
            productList.add(producto)
        }
        productos.value = productList
    }
}