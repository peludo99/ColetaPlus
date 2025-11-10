package com.example.coletaplus

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.coletaplus.Classes.RepositorioDados // 👈 import adicionado

class HubActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hub)

        // --- 🧠 Mostra o nome e e-mail do usuário logado ---
        val usuario = RepositorioDados.usuarioLogado
        val tvNomeUsuario = findViewById<TextView>(R.id.tvUserName) // ajuste o ID pro seu XML
        val tvEmailUsuario = findViewById<TextView>(R.id.tvUserEmail) // ajuste o ID também

        tvNomeUsuario?.text = usuario?.nome ?: "Usuário desconhecido"
        tvEmailUsuario?.text = usuario?.email ?: "E-mail não disponível"
        // ---------------------------------------------------

        // --- Referências para os elementos da tela ---
        val tabGerenciamentoContainer = findViewById<LinearLayout>(R.id.tabGerenciamentoContainer)
        val tvTabGerenciamento = findViewById<TextView>(R.id.tvTabGerenciamento)
        val indicatorGerenciamento = findViewById<View>(R.id.indicatorGerenciamento)

        val tabCriacaoContainer = findViewById<LinearLayout>(R.id.tabCriacaoContainer)
        val tvTabCriacao = findViewById<TextView>(R.id.tvTabCriacao)
        val indicatorCriacao = findViewById<View>(R.id.indicatorCriacao)

        val layoutContentGerenciamento = findViewById<View>(R.id.layoutContentGerenciamento)
        val layoutContentCriacao = findViewById<View>(R.id.layoutContentCriacao)

        val colorActive = Color.parseColor("#1B5E20")
        val colorInactive = Color.parseColor("#9FA8DA")

        // --- Ação do Clique na Aba GERENCIAMENTO ---
        tabGerenciamentoContainer.setOnClickListener {
            tvTabGerenciamento.setTextColor(colorActive)
            indicatorGerenciamento.visibility = View.VISIBLE

            tvTabCriacao.setTextColor(colorInactive)
            indicatorCriacao.visibility = View.INVISIBLE

            layoutContentGerenciamento.visibility = View.VISIBLE
            layoutContentCriacao.visibility = View.GONE
        }

        // --- Ação do Clique na Aba CRIAÇÃO ---
        tabCriacaoContainer.setOnClickListener {
            tvTabGerenciamento.setTextColor(colorInactive)
            indicatorGerenciamento.visibility = View.INVISIBLE

            tvTabCriacao.setTextColor(colorActive)
            indicatorCriacao.visibility = View.VISIBLE

            layoutContentGerenciamento.visibility = View.GONE
            layoutContentCriacao.visibility = View.VISIBLE
        }

        // --- Botão de insígnia (abre Ranking) ---
        val btnInsignia1 = findViewById<LinearLayout>(R.id.btnInsignia1)
        btnInsignia1.setOnClickListener {
            val intent = Intent(this, GamificationRankingActivity::class.java)
            startActivity(intent)
        }

        // --- Navegação ---
        val btnmap = findViewById<ImageView>(R.id.muser)
        btnmap.setOnClickListener {
            val intent = Intent(this, TelaInicialActivity::class.java)
            startActivity(intent)
        }
    }
}
