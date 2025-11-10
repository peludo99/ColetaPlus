package com.example.coletaplus

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GamificationRankingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gamification_ranking)

        //nav

        val btnmap = findViewById<ImageView>(R.id.muser)


        btnmap.setOnClickListener {

            val intent = Intent(this, TelaInicialActivity::class.java)


            startActivity(intent)

        }

        val btnuser = findViewById<ImageView>(R.id.tuser)


        btnuser.setOnClickListener {

            val intent = Intent(this, HubActivity::class.java)


            startActivity(intent)

        }

        val rvRanking = findViewById<RecyclerView>(R.id.rvRanking)
        rvRanking.layoutManager = LinearLayoutManager(this)

        val listaFalsa = List(10) { index ->
            RankingItem(
                position = index + 4,
                name = "Usuário ${index + 4}",
                score = 500 - (index * 10)
            )
        }
        rvRanking.adapter = RankingAdapter(listaFalsa)

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