package com.example.coletaplus

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GamificationRankingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gamification_ranking)

        val rvRanking = findViewById<RecyclerView>(R.id.rvRanking)
        rvRanking.layoutManager = LinearLayoutManager(this)
        rvRanking.adapter = RankingAdapter(10)

        val rank1 = findViewById<LinearLayout>(R.id.llRank1)
        rank1.setOnClickListener {
            abrirHistorico("Pedro", "788")
        }

        val rank2 = findViewById<LinearLayout>(R.id.llRank2)
        rank2.setOnClickListener {
            abrirHistorico("Caio", "600")
        }

        val rank3 = findViewById<LinearLayout>(R.id.llRank3)
        rank3.setOnClickListener {
            abrirHistorico("Ronald", "488")
        }
    }

    private fun abrirHistorico(nome: String, pontos: String) {
        val bottomSheet = UserHistoryBottomSheet.newInstance(nome, pontos)
        bottomSheet.show(supportFragmentManager, UserHistoryBottomSheet.TAG)
    }
}