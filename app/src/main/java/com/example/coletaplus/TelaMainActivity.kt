package com.example.coletaplus

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.coletaplus.Classes.Pessoa
import com.example.coletaplus.Classes.RepositorioDados
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class TelaMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("ColetaPlusPrefs", MODE_PRIVATE)
        val emailSalvo = prefs.getString("usuarioEmail", null)
        val senhaSalva = prefs.getString("usuarioSenha", null)

        if (emailSalvo != null && senhaSalva != null) {
            validarLoginNoFirebase(emailSalvo, senhaSalva)
            return
        }

        setContentView(R.layout.activity_tela_main)

        configurarTextoDoTextView6()
        configurarTextoDoTextView9()
        configurarBotaoRegistrar()
    }

    private fun validarLoginNoFirebase(email: String, senha: String) {
        val usuariosRef = FirebaseDatabase.getInstance().getReference("usuarios")

        usuariosRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (userSnapshot in snapshot.children) {
                    val emailBD = userSnapshot.child("email").getValue(String::class.java)
                    val senhaBD = userSnapshot.child("senha").getValue(String::class.java)

                    if (emailBD == email && senhaBD == senha) {
                        val nome = userSnapshot.child("nome").getValue(String::class.java)
                        val numero = userSnapshot.child("numero").getValue(String::class.java)

                        RepositorioDados.usuarioLogado =
                            Pessoa(nome ?: "", numero ?: "", emailBD ?: "", senhaBD ?: "")

                        startActivity(Intent(this@TelaMainActivity, TelaInicialActivity::class.java))
                        finish()
                        return
                    }
                }
                // Se der erro (senha/email não encontrados), volta pra tela de login
                val intent = Intent(this@TelaMainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun configurarTextoDoTextView6() {
        val meuTextView: TextView = findViewById(R.id.textView6)
        val textoCompleto = "A mudança que o nosso campus precisa!"
        val spannable = SpannableStringBuilder(textoCompleto)
        val corVerde: Int = ContextCompat.getColor(this, R.color.verde)

        val palavra1 = "mudança"
        val palavra2 = "precisa!"

        val inicio1 = textoCompleto.indexOf(palavra1)
        if (inicio1 != -1) {
            val fim1 = inicio1 + palavra1.length
            spannable.setSpan(ForegroundColorSpan(corVerde), inicio1, fim1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        val inicio2 = textoCompleto.indexOf(palavra2)
        if (inicio2 != -1) {
            val fim2 = inicio2 + palavra2.length
            spannable.setSpan(ForegroundColorSpan(corVerde), inicio2, fim2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        meuTextView.text = spannable
    }

    private fun configurarTextoDoTextView9() {
        val textViewLoginLink: TextView = findViewById(R.id.textView9)
        val texto = "Já possui uma conta? Log in"
        val spannable = SpannableStringBuilder(texto)
        val corVerde: Int = ContextCompat.getColor(this, R.color.verde)

        val inicio = texto.indexOf("Log in")
        val fim = inicio + "Log in".length

        if (inicio != -1) {
            spannable.setSpan(ForegroundColorSpan(corVerde), inicio, fim, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(StyleSpan(Typeface.BOLD), inicio, fim, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        textViewLoginLink.text = spannable

        textViewLoginLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun configurarBotaoRegistrar() {
        val botaoRegistrar: Button = findViewById(R.id.button_registrar)
        botaoRegistrar.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }
    }
}
