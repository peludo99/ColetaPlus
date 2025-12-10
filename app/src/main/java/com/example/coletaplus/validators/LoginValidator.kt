package com.example.coletaplus.validators

import android.util.Patterns

// Interface do validador que podemos simular ("mockar") nos testes
interface EmailValidator {
    fun isValid(email: String): Boolean
}

// O validador real que usa o código do Android e só funciona no ambiente do app
object AndroidEmailValidator : EmailValidator {
    override fun isValid(email: String): Boolean {
        // Usamos o operador '?' para evitar o crash no ambiente de teste
        return Patterns.EMAIL_ADDRESS?.matcher(email)?.matches() ?: false
    }
}

/**
 * Objeto responsável por validar os campos de entrada de um formulário de login.
 * Usa um EmailValidator que pode ser substituído nos testes para evitar crashes.
 */
object LoginValidator {

    // A classe agora pode usar um validador diferente para testes
    var emailValidator: EmailValidator = AndroidEmailValidator

    /**
     * Enum que representa todos os possíveis resultados da validação.
     */
    enum class ValidationResult {
        SUCCESS,                // Passou em todas as validações
        EMPTY_EMAIL,            // O campo de email está vazio ou só tem espaços
        INVALID_EMAIL_FORMAT,   // O formato do email é inválido (ex: não tem @)
        EMPTY_PASSWORD,         // O campo de senha está vazio ou só tem espaços
        PASSWORD_TOO_SHORT,     // A senha é mais curta que o mínimo permitido
        PASSWORD_NEEDS_UPPERCASE, // Nova Regra: Falta letra maiúscula
        PASSWORD_NEEDS_DIGIT      // Nova Regra: Falta número
    }

    // Define o tamanho mínimo da senha em um só lugar.
    private const val MIN_PASSWORD_LENGTH = 6

    /**
     * Executa a validação do email e da senha com todas as regras de negócio.
     * @return Um enum `ValidationResult` com o resultado da operação.
     */
    fun validate(email: String, password: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult.EMPTY_EMAIL
        }
        // Usamos nosso validador injetável em vez de chamar Patterns diretamente
        if (!emailValidator.isValid(email)) {
            return ValidationResult.INVALID_EMAIL_FORMAT
        }
        if (password.isBlank()) {
            return ValidationResult.EMPTY_PASSWORD
        }
        if (password.length < MIN_PASSWORD_LENGTH) {
            return ValidationResult.PASSWORD_TOO_SHORT
        }

        // --- NOVA LÓGICA DE VALIDAÇÃO DE SENHA FORTE ---

        // Regex para verificar se a string contém pelo menos um dígito (0-9)
        val hasDigit = Regex(".*[0-9].*")
        // Regex para verificar se a string contém pelo menos uma letra maiúscula (A-Z)
        val hasUppercase = Regex(".*[A-Z].*")

        // 1. Verifica se tem letra maiúscula
        if (!password.matches(hasUppercase)) {
            return ValidationResult.PASSWORD_NEEDS_UPPERCASE
        }

        // 2. Verifica se tem número
        if (!password.matches(hasDigit)) {
            return ValidationResult.PASSWORD_NEEDS_DIGIT
        }

        // Se passou por TODAS as verificações, o resultado é sucesso!
        return ValidationResult.SUCCESS
    }
}
