package com.example.coletaplus

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView // <--- Não esqueça de importar o TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class ManutencoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manutencoes)

        // Configurar botão de voltar
        val ivBack = findViewById<ImageView>(R.id.ivBack)
        ivBack.setOnClickListener {
            finish()
        }

        // Configurar RecyclerView
        val rvManutencoes = findViewById<RecyclerView>(R.id.rvManutencoes)

        // Dados Fictícios (adicionar ou tirar itens para testar a contagem)
        val listaDeManutencoes = listOf(
            MaintenanceItem("Ponto plástico - 3", "07/11/2025 às 08:30", "#FDD835"),
            MaintenanceItem("Ponto vidro - 7", "01/11/2025 às 20:12", "#FF3D00"),
            MaintenanceItem("Ponto vidro - 7", "01/11/2025 às 20:12", "#2E7D32"),
            MaintenanceItem("Ponto vidro - 7", "01/11/2025 às 20:12", "#4285F4"),
            MaintenanceItem("Ponto vidro - 7", "01/11/2025 às 20:12", "#5D4037")
        )

        val adapter = MaintenanceAdapter(listaDeManutencoes)
        rvManutencoes.adapter = adapter

        // 1. Encontramos o TextView do subtítulo
        val tvSubtitle = findViewById<TextView>(R.id.tvSubtitle)

        // 2. Pegamos o tamanho da lista (.size)
        val quantidade = listaDeManutencoes.size

        // 3. Definimos o texto usando essa quantidade
        tvSubtitle.text = "$quantidade solicitações pendentes"
    }
}