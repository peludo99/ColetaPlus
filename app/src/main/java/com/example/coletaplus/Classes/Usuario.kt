package com.example.coletaplus.Classes

/**
 * Representa um usuário falso no aplicativo.
 *
 * O uso de 'data class' é ideal para classes que existem principalmente
 * para armazenar dados. O compilador gera automaticamente funções úteis
 * como equals(), hashCode(), toString(), etc.
 *
 * @param id O identificador único do usuário (geralmente vindo do Firebase Auth).
 * @param nome O nome de exibição do usuário.
 * @param email O email de login do usuário.
 * @param tipo O tipo de usuário (ex: "ALUNO", "SERVIDOR").
 * @param pontos A pontuação acumulada do usuário em atividades de gamificação.
 */
data class Usuario(
    val id: String = "",
    val nome: String = "",
    val email: String = "",
    val tipo: String = "",
    var pontos: Int = 0
)
