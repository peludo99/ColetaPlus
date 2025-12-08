package com.example.coletaplus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.activity.enableEdgeToEdge


import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coletaplus.Classes.RepositorioDados
import com.google.firebase.database.FirebaseDatabase
import com.google.android.material.switchmaterial.SwitchMaterial // <-- IMPORT CORRIGIDO

// Constantes para SharedPreferences
const val PREFS_NAME = "ConfiguracoesApp"
const val PREF_NOTIFICACOES_ATIVAS = "notificacoes_ativas"

class ConfiActivity : AppCompatActivity() {

    // O tipo da variável foi alterado para SwitchMaterial para corresponder ao layout.
    private lateinit var notificacaoToggle: SwitchMaterial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_confi)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- Lógica existente (inalterada) ---
        val botaoVoltar: ImageView = findViewById(R.id.botaovoltar)
        botaoVoltar.setOnClickListener {

            val intent = Intent(this, TelaMainActivity::class.java)
            startActivity(intent)
            finish()

        }

        val usuario = RepositorioDados.usuarioLogado
        val tvNome = findViewById<TextView>(R.id.textView)
        val etEmail = findViewById<EditText>(R.id.edit_text_email)
        val etSenha = findViewById<EditText>(R.id.edit_text_senha)

        etEmail.isEnabled = false
        etSenha.isEnabled = false
        tvNome.text = usuario?.nome ?: ""
        etEmail.setText(usuario?.email ?: "")
        etSenha.setText(usuario?.senha ?: "")

        val botaoSair = findViewById<TextView>(R.id.btn_sair1)

        fun fazerLogout() {
            RepositorioDados.usuarioLogado = null
            val prefs = getSharedPreferences("ColetaPlusPrefs", MODE_PRIVATE)
            prefs.edit().clear().apply()
            val intent = Intent(this, TelaMainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
        botaoSair.setOnClickListener {
            fazerLogout()
            Toast.makeText(this, "Usuário deslogado.", Toast.LENGTH_SHORT).show()
        }

        val botaoDeletar = findViewById<TextView>(R.id.btn_deletar_conta)
        botaoDeletar.setOnClickListener {
            val usuarioAtual = RepositorioDados.usuarioLogado ?: return@setOnClickListener
            val ref = FirebaseDatabase.getInstance().getReference("usuarios")
            ref.get().addOnSuccessListener { snapshot ->
                val chaveUsuarioParaDeletar = snapshot.children
                    .firstOrNull { it.child("email").value?.toString() == usuarioAtual.email }
                    ?.key
                if (chaveUsuarioParaDeletar == null) {
                    Toast.makeText(this, "Usuário não encontrado no banco.", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }
                ref.child(chaveUsuarioParaDeletar).removeValue()
                    .addOnSuccessListener {
                        Toast.makeText(this, "Conta deletada com sucesso.", Toast.LENGTH_SHORT).show()
                        fazerLogout()
                    }
                    .addOnFailureListener { erro ->
                        Toast.makeText(this, "Erro ao deletar conta: ${erro.message}", Toast.LENGTH_LONG).show()
                    }
            }.addOnFailureListener {
                Toast.makeText(this, "Erro ao acessar o banco.", Toast.LENGTH_SHORT).show()
            }
        }

        // --- Lógica para o Switch de Notificações ---
        notificacaoToggle = findViewById(R.id.notificacaotogle) // Agora encontra um SwitchMaterial
        carregarEstadoDoToggle()
        notificacaoToggle.setOnCheckedChangeListener { _, isChecked ->
            salvarEstadoDoToggle(isChecked)
        }
    }

    private fun carregarEstadoDoToggle() {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val notificacoesAtivas = sharedPreferences.getBoolean(PREF_NOTIFICACOES_ATIVAS, true)
        notificacaoToggle.isChecked = notificacoesAtivas
    }

    private fun salvarEstadoDoToggle(isChecked: Boolean) {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(PREF_NOTIFICACOES_ATIVAS, isChecked)
        editor.apply()

        if (isChecked) {
            Toast.makeText(this, "Notificações ativadas", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Notificações desativadas", Toast.LENGTH_SHORT).show()
        }
    }
}
