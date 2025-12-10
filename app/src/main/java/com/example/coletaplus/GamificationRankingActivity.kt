package com.example.coletaplus

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coletaplus.Classes.Usuario
import com.example.coletaplus.adapters.RankingAdapter

class GamificationRankingActivity : AppCompatActivity() {

    private lateinit var rvRanking: RecyclerView
    private lateinit var adapter: RankingAdapter
    private var listaDeUsuarios = mutableListOf<Usuario>()

    // Views do Pódio (Top 3)
    private lateinit var rank1Container: LinearLayout
    private lateinit var rank2Container: LinearLayout
    private lateinit var rank3Container: LinearLayout
    private lateinit var tvNameRank1: TextView
    private lateinit var tvScoreRank1: TextView
    private lateinit var tvNameRank2: TextView
    private lateinit var tvScoreRank2: TextView
    private lateinit var tvNameRank3: TextView
    private lateinit var tvScoreRank3: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gamification_ranking)

        inicializarViews()

        rvRanking.layoutManager = LinearLayoutManager(this)

        // Botão de voltar
        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }

        // ======================= MUDANÇA PRINCIPAL =======================
        // Em vez de chamar o Firebase, carregamos os dados simulados
        carregarRankingSimulado()
        // ===============================================================
    }

    private fun inicializarViews() {
        rvRanking = findViewById(R.id.rvRanking)
        rank1Container = findViewById(R.id.llRank1)
        rank2Container = findViewById(R.id.llRank2)
        rank3Container = findViewById(R.id.llRank3)
        tvNameRank1 = findViewById(R.id.tvNameRank1)
        tvScoreRank1 = findViewById(R.id.tvScoreRank1)
        tvNameRank2 = findViewById(R.id.tvNameRank2)
        tvScoreRank2 = findViewById(R.id.tvScoreRank2)
        tvNameRank3 = findViewById(R.id.tvNameRank3)
        tvScoreRank3 = findViewById(R.id.tvScoreRank3)
    }

    /**
     * Simula a busca de dados de um banco de dados criando uma lista local.
     */
    private fun carregarRankingSimulado() {
        // Crie quantos usuários quiser para testar
        val dadosSimulados = listOf(
            Usuario(id = "1", nome = "Beatriz", pontos = 450),
            Usuario(id = "2", nome = "Pedro", pontos = 788),
            Usuario(id = "3", nome = "Lucas", pontos = 210),
            Usuario(id = "4", nome = "Caio", pontos = 600),
            Usuario(id = "5", nome = "Mariana", pontos = 350),
            Usuario(id = "6", nome = "Ronald", pontos = 488),
            Usuario(id = "7", nome = "Júlia", pontos = 150),
            Usuario(id = "8", nome = "Fernanda", pontos = 520)
        )

        // Limpa a lista principal e adiciona os dados simulados
        listaDeUsuarios.clear()
        listaDeUsuarios.addAll(dadosSimulados)

        // ✨ PONTO-CHAVE: Ordena a lista de usuários pela pontuação, do maior para o menor
        listaDeUsuarios.sortByDescending { it.pontos }

        // Chama a função que distribui os dados para a interface
        popularInterfaceComRanking()
    }

    /**
     * Preenche o pódio e o RecyclerView com a lista de usuários já ordenada.
     * Esta função não precisa saber de onde os dados vieram (Firebase ou simulação).
     */
    private fun popularInterfaceComRanking() {
        // Garante que todos os containers do pódio estão invisíveis por padrão
        rank1Container.visibility = View.INVISIBLE
        rank2Container.visibility = View.INVISIBLE
        rank3Container.visibility = View.INVISIBLE

        // Popula o 1º Lugar
        if (listaDeUsuarios.isNotEmpty()) {
            val user1 = listaDeUsuarios[0]
            tvNameRank1.text = user1.nome
            tvScoreRank1.text = "${user1.pontos} pts"
            rank1Container.visibility = View.VISIBLE
        }

        // Popula o 2º Lugar
        if (listaDeUsuarios.size >= 2) {
            val user2 = listaDeUsuarios[1]
            tvNameRank2.text = user2.nome
            tvScoreRank2.text = "${user2.pontos} pts"
            rank2Container.visibility = View.VISIBLE
        }

        // Popula o 3º Lugar
        if (listaDeUsuarios.size >= 3) {
            val user3 = listaDeUsuarios[2]
            tvNameRank3.text = user3.nome
            tvScoreRank3.text = "${user3.pontos} pts"
            rank3Container.visibility = View.VISIBLE
        }

        // Popula o RecyclerView com o restante da lista (a partir do 4º lugar)
        if (listaDeUsuarios.size > 3) {
            val listaRestante = listaDeUsuarios.subList(3, listaDeUsuarios.size)
            // O offset é 4, pois o primeiro item da lista (índice 0) é o 4º colocado.
            adapter = RankingAdapter(listaRestante, rankingOffset = 4)
            rvRanking.adapter = adapter
        } else {
            // Se houver 3 ou menos usuários, a lista do RecyclerView fica vazia
            rvRanking.adapter = RankingAdapter(emptyList(), 0)
        }
    }
}
