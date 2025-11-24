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
import com.google.firebase.database.*

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
        val startIndex = fullText.indexOf("Acesse")
        val endIndex = startIndex + "Acesse".length
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
            val senhaConfirmadaDigitada = campoSenhaConfirme.text.toString()
            val numeroDigitado = campoNumero.text.toString()

            if (nomeDigitado.isBlank() || emailDigitado.isBlank() || senhaDigitada.isBlank()) {
                if (nomeDigitado.isBlank()) campoNome.error = "Preencha este campo"
                if (emailDigitado.isBlank()) campoEmail.error = "Preencha este campo"
                if (senhaDigitada.isBlank()) campoSenha.error = "Preencha este campo"
                return@setOnClickListener
            }

            if (!emailDigitado.contains("@") || !emailDigitado.contains(".")) {
                campoEmail.error = "E-mail inválido"
                return@setOnClickListener
            }

            if (senhaDigitada.length < 6 ||
                !senhaDigitada.any { it.isDigit() } ||
                !senhaDigitada.any { it.isLetter() }) {
                campoSenha.error = "A senha deve ter letras, números e no mínimo 6 caracteres"
                return@setOnClickListener
            }

            if (senhaDigitada != senhaConfirmadaDigitada) {
                campoSenhaConfirme.error = "As senhas não coincidem"
                return@setOnClickListener
            }

            val ref = FirebaseDatabase.getInstance().getReference("usuarios")
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (userSnapshot in snapshot.children) {
                        val email = userSnapshot.child("email").getValue(String::class.java)
                        if (email == emailDigitado) {
                            Toast.makeText(this@CadastroActivity, "E-mail já cadastrado", Toast.LENGTH_SHORT).show()
                            return
                        }
                    }

                    val novaPessoa = Pessoa(
                        nome = nomeDigitado,
                        numero = numeroDigitado,
                        email = emailDigitado,
                        senha = senhaDigitada
                    )

                    val userId = ref.push().key
                    if (userId != null) {
                        ref.child(userId).setValue(novaPessoa)
                            .addOnSuccessListener {
                                Toast.makeText(this@CadastroActivity, "Cadastro realizado!", Toast.LENGTH_SHORT).show()

                                RepositorioDados.usuarioLogado = novaPessoa

                                val prefs = getSharedPreferences("ColetaPlusPrefs", MODE_PRIVATE)
                                prefs.edit()
                                    .putString("usuarioEmail", novaPessoa.email)
                                    .putString("usuarioSenha", novaPessoa.senha)
                                    .apply()

                                val intent = Intent(this@CadastroActivity, TelaInicialActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }

        botaoIrParaLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
