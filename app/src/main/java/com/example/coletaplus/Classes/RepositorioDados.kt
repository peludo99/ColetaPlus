// Classes/RepositorioDados.kt
package com.example.coletaplus.Classes

// Classe para manter a lista de forma global
object RepositorioDados {
    val listaPessoas = ArrayList<Pessoa>()

    // Opcional: Adiciona um usuário inicial de teste
    init {
        listaPessoas.add(Pessoa("Admin", "admin@coleta.com", "123456"))
    }
}