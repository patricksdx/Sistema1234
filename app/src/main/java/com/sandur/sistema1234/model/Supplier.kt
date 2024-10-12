package com.sandur.sistema1234.model

data class Supplier(
    val idproveedor: String,
    val nombreempresa: String,
    val nombrecontacto: String,
    val cargocontacto: String,
    val direccion: String,
    val ciudad: String,
    val region: String?, // Es nullable porque puede ser null
    val codigopostal: String,
    val pais: String,
    val telefono: String,
    val fax: String?
)
