// Classes/RepositorioDados.kt
package com.example.coletaplus.Classes

object RepositorioDados {
    val listaPessoas = ArrayList<Pessoa>()

    var usuarioLogado: Pessoa? = null

    init {
        listaPessoas.add(Pessoa("Admin", "admin@coleta.com", "123456"))
    }
}
