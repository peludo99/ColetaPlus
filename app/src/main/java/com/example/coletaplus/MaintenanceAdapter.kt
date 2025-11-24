package com.example.coletaplus

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Classe de dados simples para representar cada solicitação
data class MaintenanceItem(
    val title: String,
    val date: String,
    val colorHex: String // Ex: "#FFD600" para amarelo
)

class MaintenanceAdapter(
    private val items: List<MaintenanceItem>
) : RecyclerView.Adapter<MaintenanceAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvTituloPonto)
        val date: TextView = view.findViewById(R.id.tvDataHora)
        val iconContainer: FrameLayout = view.findViewById(R.id.flIconContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_manutencao, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.title.text = item.title
        holder.date.text = item.date

        // Aplica a cor dinâmica no fundo do ícone
        holder.iconContainer.backgroundTintList = android.content.res.ColorStateList.valueOf(
            Color.parseColor(item.colorHex)
        )
    }

    override fun getItemCount() = items.size
}