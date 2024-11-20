package com.sandur.sistema1234.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import com.sandur.sistema1234.ui.theme.Sistema1234Theme
import com.sandur.sistema1234.view.components.EtiquetaCard
import com.sandur.sistema1234.view.labelCard.directors.DirectorsActivity
import com.sandur.sistema1234.view.labelCard.employees.EmployeesActivity
import com.sandur.sistema1234.view.labelCard.login.LoginActivity
import com.sandur.sistema1234.view.labelCard.shop.ShopActivity
import com.sandur.sistema1234.view.labelCard.suppliers.SuppliersActivity
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.AddressBook
import compose.icons.lineawesomeicons.ShoppingBagSolid
import compose.icons.lineawesomeicons.TimesSolid
import compose.icons.lineawesomeicons.TvSolid
import compose.icons.lineawesomeicons.User
import compose.icons.lineawesomeicons.UserAltSolid

class HomeActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val etiquetas = arrayOf("Proveedores", "Empleados", "Tienda","Login","Directores","Salir")
        val iconos = arrayOf(
            LineAwesomeIcons.AddressBook,
            LineAwesomeIcons.UserAltSolid,
            LineAwesomeIcons.ShoppingBagSolid,
            LineAwesomeIcons.User,
            LineAwesomeIcons.TvSolid,
            LineAwesomeIcons.TimesSolid,
        )

        enableEdgeToEdge()
        setContent {
            Sistema1234Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("Inicio")
                            }
                        )
                    },
                    content = { paddingValues ->
                        Column(
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                            ) {
                                items(etiquetas.size) { index ->
                                    EtiquetaCard(
                                        etiqueta = etiquetas[index],
                                        icono = iconos[index],
                                        onClick = { mostrarVentana(index) }
                                    )
                                }
                            }
                        }
                    }
                )
            }
        }
    }

    private fun mostrarVentana(index: Int) {
        Log.d("PRUEBA", "Se ha seleccionado la opción $index")
        when(index){
            0 -> startActivity(Intent(this, SuppliersActivity::class.java))
            1 -> startActivity(Intent(this, EmployeesActivity::class.java))
            2 -> startActivity(Intent(this, ShopActivity::class.java))
            3 -> startActivity(Intent(this, LoginActivity::class.java))
            4 -> startActivity(Intent(this, DirectorsActivity::class.java))
            else -> finish()
        }
    }
}
