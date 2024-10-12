package com.sandur.sistema1234.model

data class ProductDetails(
    val idproducto: String,
    val nombre: String,
    val idproveedor: String,
    val idcategoria: String,
    val detalle: String,
    val precio: String,
    val preciorebajado: String,
    val unidadesenexistencia: String,
    val unidadesenpedido: String,
    val nivelnuevopedido: String,
    val enoferta: String,
    val imagenchica: String,
    val imagengrande: String,
    val habilitado: String,
    val descripcion: String
)
