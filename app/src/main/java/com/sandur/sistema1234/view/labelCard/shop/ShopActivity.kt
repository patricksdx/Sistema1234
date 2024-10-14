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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.sandur.sistema1234.model.ProductCategory
import com.sandur.sistema1234.ui.theme.Sistema1234Theme
import com.sandur.sistema1234.utils.Url
import com.sandur.sistema1234.view.components.DrawCategory
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.ArrowLeftSolid
import kotlinx.coroutines.delay
import org.json.JSONArray

class ShopActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sistema1234Theme {
                val categories = remember { mutableStateOf<List<ProductCategory>>(emptyList()) }
                val isLoading = remember { mutableStateOf(true) }
                val errorMessage = remember { mutableStateOf<String?>(null) }

                LaunchedEffect(Unit) {
                    delay(1000)
                    loadCategories(categories, isLoading, errorMessage)
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Tienda") },
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
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when {
                            isLoading.value -> {
                                Text(
                                    text = "Cargando Categorias...",
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
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
                                    columns = GridCells.Fixed(1),
                                    modifier = Modifier.fillMaxSize(),
                                    contentPadding = PaddingValues(4.dp)
                                ) {
                                    items(categories.value) { category ->
                                        DrawCategory(item = category)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loadCategories(
        categoriesState: MutableState<List<ProductCategory>>,
        isLoading: MutableState<Boolean>,
        errorMessage: MutableState<String?>
    ) {
        val queue = Volley.newRequestQueue(this)
        val url = Url.BASE_URL+"categorias.php"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("Volley", response)
                val categories = processCategoriesResponse(response)
                categoriesState.value = categories
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

    private fun processCategoriesResponse(response: String): List<ProductCategory> {
        val jsonArray = JSONArray(response)
        val categories = mutableListOf<ProductCategory>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val idcategoria = jsonObject.optString("idcategoria")
            val nombre = jsonObject.optString("nombre", "N/A")
            val descripcion = jsonObject.optString("descripcion", "N/A")
            val total = jsonObject.optString("total", "0")
            val foto = jsonObject.optString("foto", "")

            val categoria = ProductCategory(
                idcategoria = idcategoria,
                nombre = nombre,
                descripcion = descripcion,
                total = total,
                foto = foto
            )
            categories.add(categoria)
        }
        return categories
    }
}
