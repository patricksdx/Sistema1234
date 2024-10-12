package com.sandur.sistema1234.view.labelCard.employees

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sandur.sistema1234.R
import com.sandur.sistema1234.model.Employee
import com.sandur.sistema1234.ui.theme.Sistema1234Theme
import com.sandur.sistema1234.view.components.EmployeeCard
import com.sandur.sistema1234.view.components.headerImage
import kotlinx.coroutines.delay
import org.json.JSONArray

class EmployeesActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sistema1234Theme {

                val employees = remember { mutableStateOf<List<Employee>>(emptyList()) }
                val isLoading = remember { mutableStateOf(true) }
                val errorMessage = remember { mutableStateOf<String?>(null) }

                LaunchedEffect(Unit) {
                    delay(1000)
                    loadEmployees(employees, isLoading, errorMessage)
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Empleados") }
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
                            headerImage(R.drawable.empleados, "Equipo de empleados", "")
                        }

                        if (isLoading.value) {
                            Text(
                                text = "Cargando empleados...",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(vertical = 20.dp),
                            )
                        } else if (errorMessage.value != null) {
                            Text(
                                text = "Error: ${errorMessage.value}",
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        } else {
                            LazyRow(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                items(employees.value) { employee ->
                                    EmployeeCard(employee = employee)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loadEmployees(
        employeesState: MutableState<List<Employee>>,
        isLoading: MutableState<Boolean>,
        errorMessage: MutableState<String?>
    ) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/empleados.php"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("Volley", response)
                val employees = processEmployeesResponse(response)
                employeesState.value = employees
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

    private fun processEmployeesResponse(response: String): List<Employee> {
        val jsonArray = JSONArray(response)
        val employees = mutableListOf<Employee>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val employee = Employee(
                idempleado = jsonObject.optString("idempleado", ""),
                apellidos = jsonObject.optString("apellidos", "N/A"),
                nombres = jsonObject.optString("nombres", "N/A"),
                cargo = jsonObject.optString("cargo", "N/A"),
                tratamiento = jsonObject.optString("tratamiento", "N/A"),
                fechanacimiento = jsonObject.optString("fechanacimiento", "N/A"),
                fechacontratacion = jsonObject.optString("fechacontratacion", "N/A"),
                direccion = jsonObject.optString("direccion", "N/A"),
                ciudad = jsonObject.optString("ciudad", "N/A"),
                usuario = jsonObject.optString("usuario", "N/A"),
                codigopostal = jsonObject.optString("codigopostal", "N/A"),
                pais = jsonObject.optString("pais", "N/A"),
                telefono = jsonObject.optString("telefono", "N/A"),
                clave = jsonObject.optString("clave", ""),
                foto = jsonObject.optString("foto", ""),
                jefe = jsonObject.optString("jefe", "")
            )
            employees.add(employee)
        }
        return employees
    }
}
