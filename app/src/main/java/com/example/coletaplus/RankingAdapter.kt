package com.example.coletaplus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Nota: Agora usa com.example.coletaplus.RankingItem
class RankingAdapter(private val rankingList: List<RankingItem>) :
    RecyclerView.Adapter<RankingAdapter.RankingViewHolder>() {

    class RankingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // IDs assumidos do seu item_ranking.xml
        val textPosition: TextView = view.findViewById(R.id.text_position)
        val textName: TextView = view.findViewById(R.id.text_name)
        val textScore: TextView = view.findViewById(R.id.text_score)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ranking, parent, false)
        return RankingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val currentItem = rankingList[position]

        holder.textPosition.text = currentItem.position.toString()
        holder.textName.text = currentItem.name
        holder.textScore.text = currentItem.score.toString()
    }

    override fun getItemCount() = rankingList.size
}