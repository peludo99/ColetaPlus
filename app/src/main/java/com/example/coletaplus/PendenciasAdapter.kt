package com.example.coletaplus

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Dados de cada item
data class PendenciaItem(
    val titulo: String,
    val problema: String,
    val dataHora: String,
    val tempoRelativo: String, // ex: "• há 2 dias"
    val corIconeHex: String    // ex: "#FF0000"
)

class PendenciasAdapter(private val items: List<PendenciaItem>) :
    RecyclerView.Adapter<PendenciasAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.tvTitulo)
        val problema: TextView = view.findViewById(R.id.tvProblema)
        val data: TextView = view.findViewById(R.id.tvData)
        val tempoRelativo: TextView = view.findViewById(R.id.tvTempoRelativo)
        val iconContainer: FrameLayout = view.findViewById(R.id.flIconContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pendencia, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.titulo.text = item.titulo
        holder.problema.text = item.problema
        holder.data.text = item.dataHora
        holder.tempoRelativo.text = item.tempoRelativo

        holder.iconContainer.backgroundTintList = ColorStateList.valueOf(
            Color.parseColor(item.corIconeHex)
        )
    }

    override fun getItemCount() = items.size
}