package com.sandur.sistema1234.model

data class User(
    val idcliente: String,
    val usuario: String,
    val empresa: String,
    val nombres: String,
    val correo: String,
    val cargo: String,
    val direccion: String,
    val ciudad: String,
    val region: String?,
    val clave: String,
    val pais: String,
    val telefono: String,
    val fax: String
)
