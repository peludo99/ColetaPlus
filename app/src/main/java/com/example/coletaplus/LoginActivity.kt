package com.example.coletaplus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
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
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
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

        val campoEmail = findViewById<TextInputEditText>(R.id.edit_text_email)
        val campoSenha = findViewById<TextInputEditText>(R.id.edit_text_senha)
        val botaoLogar = findViewById<AppCompatButton>(R.id.buttonCadastrar)

        botaoLogar.setOnClickListener {
            realizarLogin(campoEmail, campoSenha)
        }
    }

    private fun realizarLogin(campoEmail: TextInputEditText, campoSenha: TextInputEditText) {
        val emailDigitado = campoEmail.text.toString().trim()
        val senhaDigitada = campoSenha.text.toString()

        campoEmail.error = null
        campoSenha.error = null

        if (emailDigitado.isBlank() || senhaDigitada.isBlank()) {
            if (emailDigitado.isBlank()) campoEmail.error = "Campo obrigatório"
            if (senhaDigitada.isBlank()) campoSenha.error = "Campo obrigatório"
            Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val database = FirebaseDatabase.getInstance()
        val usuariosRef = database.getReference("usuarios")

        usuariosRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var usuarioEncontrado = false
                for (userSnapshot in snapshot.children) {
                    val email = userSnapshot.child("email").getValue(String::class.java)
                    val senha = userSnapshot.child("senha").getValue(String::class.java)
                    val nome = userSnapshot.child("nome").getValue(String::class.java)
                    val numero = userSnapshot.child("numero").getValue(String::class.java)
                    val tipo = userSnapshot.child("tipo").getValue(String::class.java)

                    if (email == emailDigitado && senha == senhaDigitada) {
                        usuarioEncontrado = true

                        RepositorioDados.usuarioLogado = Pessoa(nome ?: "", numero ?: "", email ?: "", senha ?: "", tipo ?: "ALUNO")

                        val prefs = getSharedPreferences("ColetaPlusPrefs", MODE_PRIVATE)
                        prefs.edit()
                            .putString("usuarioEmail", emailDigitado)
                            .putString("usuarioSenha", senhaDigitada)
                            .apply()

                        val intent = Intent(this@LoginActivity, TelaInicialActivity::class.java)
                        startActivity(intent)
                        finish()
                        break
                    }
                }

                if (!usuarioEncontrado) {
                    Toast.makeText(
                        this@LoginActivity,
                        "E-mail ou senha inválidos.",
                        Toast.LENGTH_LONG
                    ).show()
                    campoEmail.error = "Verifique seus dados"
                    campoSenha.error = "Verifique seus dados"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@LoginActivity,
                    "Erro ao acessar o banco: ${error.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
