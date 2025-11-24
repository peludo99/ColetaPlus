package com.example.coletaplus

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GamificationRankingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gamification_ranking)

        val tituloRecebido = intent.getStringExtra("EXTRA_TITULO") ?: "Atividade"
        val tvActivityTitle = findViewById<TextView>(R.id.tvActivityTitle)
        tvActivityTitle.text = tituloRecebido

        val btnmap = findViewById<ImageView>(R.id.muser)
        btnmap.setOnClickListener {
            startActivity(Intent(this, TelaInicialActivity::class.java))
        }

        val btnuser = findViewById<ImageView>(R.id.tuser)
        btnuser.setOnClickListener {
            startActivity(Intent(this, HubActivity::class.java))
        }

        val ivBack = findViewById<ImageView>(R.id.ivBack)
        ivBack.setOnClickListener {
            finish()
        }

        val todosUsuarios = mutableListOf<RankingItem>()

        todosUsuarios.add(RankingItem(0, "Pedro", 950))
        todosUsuarios.add(RankingItem(0, "Ana", 820))
        todosUsuarios.add(RankingItem(0, "João", 450))
        todosUsuarios.add(RankingItem(0, "Maria", 1200))
        todosUsuarios.add(RankingItem(0, "Carlos", 600))
        todosUsuarios.add(RankingItem(0, "Fernanda", 300))
        todosUsuarios.add(RankingItem(0, "Roberto", 780))
        todosUsuarios.add(RankingItem(0, "Julia", 1100))
        todosUsuarios.add(RankingItem(0, "Lucas", 550))
        todosUsuarios.add(RankingItem(0, "Beatriz", 400))
        todosUsuarios.add(RankingItem(0, "Gabriel", 900))
        todosUsuarios.add(RankingItem(0, "Larissa", 350))

        val listaOrdenada = todosUsuarios.sortedByDescending { it.score }

        val top1 = listaOrdenada.getOrNull(0)
        val top2 = listaOrdenada.getOrNull(1)
        val top3 = listaOrdenada.getOrNull(2)

        if (top1 != null) {
            findViewById<TextView>(R.id.tvNameRank1).text = top1.name
            findViewById<TextView>(R.id.tvScoreRank1).text = top1.score.toString()
            findViewById<LinearLayout>(R.id.llRank1).setOnClickListener {
                abrirHistorico(top1.name, top1.score.toString())
            }
        }

        if (top2 != null) {
            findViewById<TextView>(R.id.tvNameRank2).text = top2.name
            findViewById<TextView>(R.id.tvScoreRank2).text = top2.score.toString()
            findViewById<LinearLayout>(R.id.llRank2).setOnClickListener {
                abrirHistorico(top2.name, top2.score.toString())
            }
        }

        if (top3 != null) {
            findViewById<TextView>(R.id.tvNameRank3).text = top3.name
            findViewById<TextView>(R.id.tvScoreRank3).text = top3.score.toString()
            findViewById<LinearLayout>(R.id.llRank3).setOnClickListener {
                abrirHistorico(top3.name, top3.score.toString())
            }
        }

        val listaParaRecyclerView = listaOrdenada.drop(3).mapIndexed { index, item ->
            RankingItem(position = index + 4, name = item.name, score = item.score)
        }

        val rvRanking = findViewById<RecyclerView>(R.id.rvRanking)
        rvRanking.layoutManager = LinearLayoutManager(this)
        rvRanking.adapter = RankingAdapter(listaParaRecyclerView)
    }

    private fun abrirHistorico(nome: String, pontos: String) {
        val bottomSheet = UserHistoryBottomSheet.newInstance(nome, pontos)
        bottomSheet.show(supportFragmentManager, UserHistoryBottomSheet.TAG)
    }
}