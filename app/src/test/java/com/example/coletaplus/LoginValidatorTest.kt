package com.example.coletaplus

import com.example.coletaplus.validators.EmailValidator
import com.example.coletaplus.validators.LoginValidator
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Testes unitários para a classe LoginValidator.
 * Inclui a solução para o problema 'Patterns.EMAIL_ADDRESS is null' e os novos
 * testes para as regras de senha forte (letra maiúscula e número).
 */
class LoginValidatorTest {

    // Criamos nosso validador de email "falso" (mock) para os testes.
    // A lógica é simples e robusta para o ambiente de teste.
    private val fakeEmailValidator = object : EmailValidator {
        override fun isValid(email: String): Boolean {

            return email.contains("@")
        }
    }


    @Before
    fun setup() {
        // Injetamos nosso validador falso na classe LoginValidator.
        // A partir daqui, durante os testes, LoginValidator usará nosso código,
        // e não o do Android que causa o crash.
        LoginValidator.emailValidator = fakeEmailValidator
    }

    // --- Teste Unitário: Cenário de Sucesso---
    @Test
    fun `validate com email e senha validos retorna SUCCESS`() {
        // Arrange (Preparar)
        val emailValido = "teste@exemplo.com"
        // A senha agora precisa ter no mínimo 6 chars, uma maiúscula e um número.
        val senhaValida = "SenhaValida123" // Senha que cumpre todas as regras

        // Act (Agir)
        val resultado = LoginValidator.validate(emailValido, senhaValida)

        // Assert (Verificar)
        assertEquals(
            "A validação deveria passar para um email e senha que cumprem todas as regras",
            LoginValidator.ValidationResult.SUCCESS,
            resultado
        )
    }

    // --- Teste Unitário: Cenários de Falha ---
    @Test
    fun `validate com entradas invalidas retorna erro especifico correto`() {
        // Cenário 1: Email com formato inválido
        assertEquals(
            "Deveria falhar para email sem '@'",
            LoginValidator.ValidationResult.INVALID_EMAIL_FORMAT,
            LoginValidator.validate("emailinvalido.com", "SenhaValida123")
        )

        // Cenário 2: Senha muito curta
        assertEquals(
            "Deveria falhar para senha com menos de 6 caracteres",
            LoginValidator.ValidationResult.PASSWORD_TOO_SHORT,
            LoginValidator.validate("teste@exemplo.com", "S1")
        )

        // Cenário 3: Email vazio
        assertEquals(
            "Deveria falhar para email em branco",
            LoginValidator.ValidationResult.EMPTY_EMAIL,
            LoginValidator.validate("   ", "SenhaValida123")
        )

        // Cenário 4: Senha vazia
        assertEquals(
            "Deveria falhar para senha em branco",
            LoginValidator.ValidationResult.EMPTY_PASSWORD,
            LoginValidator.validate("teste@exemplo.com", "  ")
        )


        // Cenário 5: Senha sem letra maiúscula
        assertEquals(
            "Deveria falhar para senha sem letra maiúscula",
            LoginValidator.ValidationResult.PASSWORD_NEEDS_UPPERCASE,
            LoginValidator.validate("teste@exemplo.com", "senhavalida123")
        )

        // Cenário 6: Senha sem número
        assertEquals(
            "Deveria falhar para senha sem número",
            LoginValidator.ValidationResult.PASSWORD_NEEDS_DIGIT,
            LoginValidator.validate("teste@exemplo.com", "SenhaValidaSemNumero")
        )
    }
}
