package com.example.coletaplus

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
// **IMPORTAÇÕES NECESSÁRIAS**
import com.example.coletaplus.RankingAdapter
import com.example.coletaplus.RankingItem

// Vinculamos a classe ao arquivo de layout XML fragment_gamification
class GamificationFragment : Fragment(R.layout.fragment_gamification) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Assegura que o ID recycler_ranking existe no fragment_gamification.xml
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_ranking)

        val dummyList = generateDummyRanking()

        recyclerView.layoutManager = LinearLayoutManager(context)
        // O RankingAdapter agora é reconhecido graças à importação
        recyclerView.adapter = RankingAdapter(dummyList)
    }

    private fun generateDummyRanking(): List<RankingItem> {
        return listOf(
            RankingItem(1, "Thiago", 670),
            RankingItem(2, "Sucena", 650),
            RankingItem(3, "Gabriel", 570),
            RankingItem(4, "Paulo", 480),
            RankingItem(5, "Rodrigo", 310)
        )
    }
}