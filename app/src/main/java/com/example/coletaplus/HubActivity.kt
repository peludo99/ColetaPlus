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

class HubActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hub)

        // --- Referências para os elementos da tela ---
        // Abas e Indicadores
        val tabGerenciamentoContainer = findViewById<LinearLayout>(R.id.tabGerenciamentoContainer)
        val tvTabGerenciamento = findViewById<TextView>(R.id.tvTabGerenciamento)
        val indicatorGerenciamento = findViewById<View>(R.id.indicatorGerenciamento)

        val tabCriacaoContainer = findViewById<LinearLayout>(R.id.tabCriacaoContainer)
        val tvTabCriacao = findViewById<TextView>(R.id.tvTabCriacao)
        val indicatorCriacao = findViewById<View>(R.id.indicatorCriacao)

        // Conteúdos que serão alternados
        val layoutContentGerenciamento = findViewById<View>(R.id.layoutContentGerenciamento)
        val layoutContentCriacao = findViewById<View>(R.id.layoutContentCriacao)

        // Cores (para facilitar a alternância)
        // Dica: Se tiver definido essas cores no colors.xml, use ContextCompat.getColor(this, R.color.nome_da_cor)
        val colorActive = Color.parseColor("#1B5E20") // green_card_dark
        val colorInactive = Color.parseColor("#9FA8DA") // cor cinza/azulada

        // --- Ação do Clique na Aba GERENCIAMENTO ---
        tabGerenciamentoContainer.setOnClickListener {
            // 1. Atualiza cores dos textos e visibilidade dos indicadores
            tvTabGerenciamento.setTextColor(colorActive)
            indicatorGerenciamento.visibility = View.VISIBLE

            tvTabCriacao.setTextColor(colorInactive)
            indicatorCriacao.visibility = View.INVISIBLE

            // 2. Alterna qual conteúdo está visível
            layoutContentGerenciamento.visibility = View.VISIBLE
            layoutContentCriacao.visibility = View.GONE
        }

        // --- Ação do Clique na Aba CRIAÇÃO ---
        tabCriacaoContainer.setOnClickListener {
            // 1. Atualiza cores e indicadores (inverso do acima)
            tvTabGerenciamento.setTextColor(colorInactive)
            indicatorGerenciamento.visibility = View.INVISIBLE

            tvTabCriacao.setTextColor(colorActive)
            indicatorCriacao.visibility = View.VISIBLE

            // 2. Alterna os conteúdos
            layoutContentGerenciamento.visibility = View.GONE
            layoutContentCriacao.visibility = View.VISIBLE
        }

        // Opcional: Adicionar o clique na insígnia para abrir o Ranking (igual fizemos antes)
        val btnInsignia1 = findViewById<LinearLayout>(R.id.btnInsignia1)
        btnInsignia1.setOnClickListener {
            // Código para abrir a Activity de Ranking (gamificação)
             val intent = Intent(this, GamificationRankingActivity::class.java)
             startActivity(intent)
        }


        //navegação

        val btnmap = findViewById<ImageView>(R.id.muser)


        btnmap.setOnClickListener {

            val intent = Intent(this, TelaInicialActivity::class.java)


            startActivity(intent)

        }

    }
}