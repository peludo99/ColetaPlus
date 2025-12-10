package com.example.coletaplus

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class PendenciasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pendencias)

        val ivBack = findViewById<ImageView>(R.id.ivBack)
        ivBack.setOnClickListener { finish() }

        val rvPendencias = findViewById<RecyclerView>(R.id.rvPendencias)

        // Criando os dados IGUAIS à imagem
        val lista = listOf(
            PendenciaItem("Ponto vidro - 7", "Problema: Ponto cheio", "28/11 às 10:30", "• há 2 dias", "#FF3D00"), // Vermelho
            PendenciaItem("Ponto plástico - 8", "Problema: Ponto cheio", "26/11 às 13:30", "• há 4 dias", "#FFD600"), // Amarelo
            PendenciaItem("Ponto plástico - 21", "Problema: Manutenção pendente", "24/11 às 13:00", "• há 6 dias", "#FFD600"), // Amarelo
            PendenciaItem("Ponto papel - 21", "Problema: Manutenção pendente", "23/11 às 09:00", "• há 7 dias", "#80CBC4"), // Verde água/Teal
            PendenciaItem("Ponto metal - 5", "Problema: Quebrado", "20/11 às 15:00", "• há 10 dias", "#FF3D00") // Item extra pra testar o SCROLL
        )

        rvPendencias.adapter = PendenciasAdapter(lista)

        // Atualiza o texto de Total
        val tvTotal = findViewById<TextView>(R.id.tvTotalPendencias)
        tvTotal.text = "Total de pendências: ${lista.size}"
    }
}