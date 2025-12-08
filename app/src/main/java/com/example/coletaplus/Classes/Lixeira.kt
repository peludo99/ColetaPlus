package com.example.coletaplus.Classes

import org.osmdroid.util.GeoPoint

// O único campo novo é 'id'. Mantenha os outros que você já tem.
data class Lixeira(
    val id: String, // <-- ADICIONE ESTE CAMPO
    val cor: String,
    val loc: GeoPoint,
    val descricao: String
)
