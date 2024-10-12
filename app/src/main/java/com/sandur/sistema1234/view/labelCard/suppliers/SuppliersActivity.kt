package com.sandur.sistema1234.view.labelCard.suppliers

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sandur.sistema1234.R
import com.sandur.sistema1234.model.Supplier
import com.sandur.sistema1234.ui.theme.Sistema1234Theme
import com.sandur.sistema1234.view.components.headerImage
import kotlinx.coroutines.delay
import org.json.JSONArray

class SuppliersActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sistema1234Theme {

                val suppliers = remember { mutableStateOf<List<Supplier>>(emptyList()) }
                val isLoading = remember { mutableStateOf(true) }
                val errorMessage = remember { mutableStateOf<String?>(null) }

                LaunchedEffect(Unit) {
                    delay(1000)
                    loadSuppliers(suppliers, isLoading, errorMessage)
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Proveedores") }
                        )
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {
                        Box(
                            contentAlignment = Alignment.BottomEnd,
                            modifier = Modifier.height(200.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.5f))
                            )
                            headerImage(R.drawable.proveedores, "Mujer Comprando", "")
                        }
                        Column(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .padding(bottom = 30.dp)
                        ) {
                            if (isLoading.value) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ){
                                    Text(
                                        text = "Cargando proveedores...",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .padding(vertical = 20.dp),
                                    )
                                }

                            } else if (errorMessage.value != null) {
                                Text(text = "Error: ${errorMessage.value}", modifier = Modifier.align(Alignment.CenterHorizontally))
                            } else {
                                suppliers.value.forEach { supplier ->
                                    Card(
                                        modifier = Modifier
                                            .padding(top = 30.dp, start = 10.dp, end = 10.dp)
                                            .fillMaxWidth(),
                                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                                    ){
                                        Column(
                                            modifier = Modifier
                                                .padding(15.dp)
                                        ) {
                                            Text(text = "${supplier.nombreempresa}", style = MaterialTheme.typography.titleLarge)
                                            Text(text = "${supplier.nombrecontacto}")
                                            Text(text = "${supplier.cargocontacto}")
                                            Text(text = "${supplier.telefono}")
                                            Text(text = "${supplier.ciudad} / ${supplier.pais}")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loadSuppliers(
        suppliersState: MutableState<List<Supplier>>,
        isLoading: MutableState<Boolean>,
        errorMessage: MutableState<String?>
    ) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/proveedores.php"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("Volley", response)
                val suppliers = processSuppliersResponse(response)
                suppliersState.value = suppliers
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

    private fun processSuppliersResponse(response: String): List<Supplier> {
        val jsonArray = JSONArray(response)
        val suppliers = mutableListOf<Supplier>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val supplier = Supplier(
                idproveedor = jsonObject.optString("idproveedor", ""),
                nombreempresa = jsonObject.optString("nombreempresa", "N/A"),
                nombrecontacto = jsonObject.optString("nombrecontacto", "N/A"),
                cargocontacto = jsonObject.optString("cargocontacto", "N/A"),
                direccion = jsonObject.optString("direccion", "N/A"),
                ciudad = jsonObject.optString("ciudad", "N/A"),
                region = jsonObject.optString("region", null),
                codigopostal = jsonObject.optString("codigopostal", "N/A"),
                pais = jsonObject.optString("pais", "N/A"),
                telefono = jsonObject.optString("telefono", "N/A"),
                fax = jsonObject.optString("fax", null)
            )
            suppliers.add(supplier)
        }
        return suppliers
    }
}
