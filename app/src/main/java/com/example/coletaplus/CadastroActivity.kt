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
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coletaplus.Classes.Pessoa
import com.example.coletaplus.Classes.RepositorioDados
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase

class CadastroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro)

        val mainView = findViewById<android.view.View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val divisorTextView = findViewById<Button>(R.id.buttonIrParaLogin)
        val fullText = "Já possui uma conta? Acesse"
        val spannableString = SpannableString(fullText)
        val wordToColor = "Acesse"
        val startIndex = fullText.indexOf(wordToColor)
        val endIndex = startIndex + wordToColor.length
        val color = ContextCompat.getColor(this, R.color.verde)
        spannableString.setSpan(
            ForegroundColorSpan(color),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        divisorTextView.text = spannableString

        val campoNome = findViewById<TextInputEditText>(R.id.edit_text_nome)
        val campoEmail = findViewById<TextInputEditText>(R.id.edit_text_email)
        val campoSenha = findViewById<TextInputEditText>(R.id.edit_text_senha)
        val campoSenhaConfirme = findViewById<TextInputEditText>(R.id.edit_text_senhaconfirme)
        val campoNumero = findViewById<TextInputEditText>(R.id.edit_text_numero)
        val botaoCadastrar = findViewById<Button>(R.id.buttonCadastrar)
        val botaoIrParaLogin = findViewById<Button>(R.id.buttonIrParaLogin)

        botaoCadastrar.setOnClickListener {
            val nomeDigitado = campoNome.text.toString().trim()
            val emailDigitado = campoEmail.text.toString().trim()
            val senhaDigitada = campoSenha.text.toString()

            if (nomeDigitado.isBlank() || emailDigitado.isBlank() || senhaDigitada.isBlank()) {
                if (nomeDigitado.isBlank()) campoNome.error = "Preencha este campo"
                if (emailDigitado.isBlank()) campoEmail.error = "Preencha este campo"
                if (senhaDigitada.isBlank()) campoSenha.error = "Preencha este campo"
                return@setOnClickListener
            }

            val novaPessoa = Pessoa(nome = nomeDigitado, email = emailDigitado, senha = senhaDigitada)
            RepositorioDados.listaPessoas.add(novaPessoa)

            Log.d("CadastroActivity", " Nova Pessoa Cadastrada: ${novaPessoa.nome}")
            Log.d("CadastroActivity", "Total na lista global: ${RepositorioDados.listaPessoas.size}")

            val database = FirebaseDatabase.getInstance()
            val usersRef = database.getReference("usuarios")
            val userId = usersRef.push().key
            if (userId != null) {
                usersRef.child(userId).setValue(novaPessoa)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Usuário ${novaPessoa.nome} cadastrado e salvo!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Falha ao salvar no Firebase", Toast.LENGTH_SHORT).show()
                    }
            }

            campoNome.text?.clear()
            campoEmail.text?.clear()
            campoSenha.text?.clear()
            campoNumero.text?.clear()
            campoSenhaConfirme.text?.clear()
        }

        botaoIrParaLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
