package com.sandur.sistema1234.model

data class Product(
    val idproducto: String,
    val nombre: String,
    val precio: Double,
    val imagen: String,
    val precioRebajado: Double?
)
