package com.example.coletaplus

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.semantics.text
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coletaplus.Classes.Pessoa
import com.example.coletaplus.Classes.RepositorioDados // <-- NOVO IMPORT DO SINGLETON!
import com.google.android.material.textfield.TextInputEditText

class CadastroActivity : AppCompatActivity() {

    // REMOVIDO: A lista não é mais local, ela vive no RepositorioDados
    // public val listaPessoas = ArrayList<Pessoa>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro)

        // ... código de Insets (mantido)
        val mainView = findViewById<android.view.View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
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

        // --- Inicialização das Views de Cadastro ---
        val campoNome = findViewById<TextInputEditText>(R.id.edit_text_nome)
        val campoEmail = findViewById<TextInputEditText>(R.id.edit_text_email)
        val campoSenha = findViewById<TextInputEditText>(R.id.edit_text_senha)
        val campoSenhaConfirme = findViewById<TextInputEditText>(R.id.edit_text_senhaconfirme)
        val campoNumero = findViewById<TextInputEditText>(R.id.edit_text_numero)

        val botaoCadastrar = findViewById<Button>(R.id.buttonCadastrar)
        val botaoIrParaLogin = findViewById<Button>(R.id.buttonIrParaLogin)

        // Lógica do botão CADASTRAR (AGORA USANDO O REPOSITÓRIO GLOBAL)
        botaoCadastrar.setOnClickListener {
            val nomeDigitado = campoNome.text.toString().trim()
            val emailDigitado = campoEmail.text.toString().trim()
            val senhaDigitada = campoSenha.text.toString()

            // 1. Validação (mantida)
            if (nomeDigitado.isBlank() || emailDigitado.isBlank() || senhaDigitada.isBlank()) {
                if (nomeDigitado.isBlank()) campoNome.error = "Preencha este campo"
                if (emailDigitado.isBlank()) campoEmail.error = "Preencha este campo"
                if (senhaDigitada.isBlank()) campoSenha.error = "Preencha este campo"
                return@setOnClickListener
            }

            // 2. Criação do Objeto
            val novaPessoa = Pessoa(nome = nomeDigitado, email = emailDigitado, senha = senhaDigitada)

            // 3. ADIÇÃO AO SINGLETON COMPARTILHADO (RepositorioDados)
            RepositorioDados.listaPessoas.add(novaPessoa) // <-- MUDANÇA ESSENCIAL

            // 4. Feedback e Log
            Log.d("CadastroActivity", " Nova Pessoa Cadastrada: ${novaPessoa.nome}")
            // Logando o total da lista compartilhada
            Log.d("CadastroActivity", "Total na lista global: ${RepositorioDados.listaPessoas.size}")

            Toast.makeText(this, "Usuário ${novaPessoa.nome} cadastrado com sucesso!", Toast.LENGTH_SHORT).show()

            campoNome.text?.clear()
            campoEmail.text?.clear()
            campoSenha.text?.clear()
            campoNumero.text?.clear()
            campoSenhaConfirme.text?.clear()
        }

        // Lógica do botão IR PARA LOGIN (MANTIDA)
        botaoIrParaLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}