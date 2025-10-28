package com.example.coletaplus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coletaplus.Classes.Pessoa
import com.example.coletaplus.Classes.RepositorioDados // <-- NOVO IMPORT DO SINGLETON!
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Configuração do Inset (mantida)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Encontre o TextView pelo ID
        val divisorTextView = findViewById<Button>(R.id.buttonIrParaLogin)

        // 2. Crie uma SpannableString a partir do texto original
        val fullText = "Já possui uma conta? Acesse"
        val spannableString = SpannableString(fullText)

        // 3. Encontre o índice de início e fim da palavra que você quer colorir
        val wordToColor = "Acesse"
        val startIndex = fullText.indexOf(wordToColor)
        val endIndex = startIndex + wordToColor.length

        // 4. Defina a cor que você quer usar (ex: a cor primária do seu app ou outra)
        val color = ContextCompat.getColor(this, R.color.verde) // Troque R.color.purple_500 pela sua cor desejada

        // 5. Aplique o estilo de cor à palavra
        spannableString.setSpan(
            ForegroundColorSpan(color),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // 6. Defina o texto estilizado no TextView
        divisorTextView.text = spannableString

        // --- 1. Inicialização das Views ---
        val campoEmail = findViewById<TextInputEditText>(R.id.edit_text_email)
        val campoSenha = findViewById<TextInputEditText>(R.id.edit_text_senha)
        val botaoLogar = findViewById<AppCompatButton>(R.id.buttonCadastrar)

        // --- 2. Configuração do Listener ---
        botaoLogar.setOnClickListener {
            realizarLogin(campoEmail, campoSenha)
        }

        //  Log para verificar quantos usuários estão no Repositório ao iniciar
        Log.d("LoginActivity", "Usuários disponíveis no Repositório: ${RepositorioDados.listaPessoas.size}")
    }


    private fun realizarLogin(campoEmail: TextInputEditText, campoSenha: TextInputEditText) {
        val emailDigitado = campoEmail.text.toString().trim()
        val senhaDigitada = campoSenha.text.toString()

        // Limpa erros anteriores
        campoEmail.error = null
        campoSenha.error = null

        // 1. Validação de Campos Vazios
        if (emailDigitado.isBlank() || senhaDigitada.isBlank()) {
            if (emailDigitado.isBlank()) campoEmail.error = "Campo obrigatório"
            if (senhaDigitada.isBlank()) campoSenha.error = "Campo obrigatório"
            Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        // 2. Lógica de Busca e Verificação
        val usuarioEncontrado = RepositorioDados.listaPessoas.find {
            it.email == emailDigitado && it.senha == senhaDigitada
        }

        if (usuarioEncontrado != null) {
            // Login Bem-Sucedido
            Log.d("LoginActivity", "✅ Login bem-sucedido para: ${usuarioEncontrado.nome}")
            Toast.makeText(this, "Bem-vindo(a), ${usuarioEncontrado.nome}!", Toast.LENGTH_LONG).show()

            // Navega para a tela inicial
            val intent = Intent(this, TelaInicialActivity::class.java) // <-- TROQUE AQUI
            startActivity(intent)
            finish() // Impede que o usuário volte para a tela de login

        } else {
            // Login Falhou
            Log.w("LoginActivity", "Falha de login: Credenciais inválidas.")
            Toast.makeText(this, "E-mail ou senha inválidos.", Toast.LENGTH_LONG).show()
            campoEmail.error = "Verifique seus dados"
            campoSenha.error = "Verifique seus dados"
        }
    }

}