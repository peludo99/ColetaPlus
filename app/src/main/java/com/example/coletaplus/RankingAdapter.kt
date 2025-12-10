package com.example.coletaplus.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coletaplus.Classes.Usuario
import com.example.coletaplus.R

class RankingAdapter(
    private val listaDeUsuarios: List<Usuario>,
    private val rankingOffset: Int
) : RecyclerView.Adapter<RankingAdapter.RankingViewHolder>() {

    class RankingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPosicao: TextView = itemView.findViewById(R.id.tvRank)
        val tvNomeUsuario: TextView = itemView.findViewById(R.id.tvName)
        val tvPontos: TextView = itemView.findViewById(R.id.tvPoints)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ranking_row, parent, false)
        return RankingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val usuario = listaDeUsuarios[position]
        val posicaoReal = position + rankingOffset
        holder.tvPosicao.text = "$posicaoReal"
        holder.tvNomeUsuario.text = usuario.nome
        holder.tvPontos.text = usuario.pontos.toString()
    }

    override fun getItemCount(): Int {
        return listaDeUsuarios.size
    }
}
