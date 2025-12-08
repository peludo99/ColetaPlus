package com.example.coletaplus

import android.content.Intent

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater

import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

// A data class ItemLoja não muda
data class ItemLoja(
    val id: Int,
    val nome: String,
    val descricao: String,
    val valor: Int,
    val iconeResId: Int
)

class LojaActivity : AppCompatActivity() {

    private lateinit var containerDeItens: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loja)

        containerDeItens = findViewById(R.id.containerDeItensGrid)
        val listaDeItens = carregarItens()

        for (item in listaDeItens) {
            adicionarViewDeItem(item)
        }
    }

    private fun adicionarViewDeItem(item: ItemLoja) {
        val inflater = LayoutInflater.from(this)
        val viewDoItem = inflater.inflate(R.layout.item_loja, containerDeItens, false)

        // Configuração dos parâmetros da grade
        val params = GridLayout.LayoutParams()
        params.height = GridLayout.LayoutParams.WRAP_CONTENT
        params.width = 0
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
        val margin = (8 * resources.displayMetrics.density).toInt()
        params.setMargins(margin, margin, margin, margin)
        viewDoItem.layoutParams = params

        // Encontra os componentes visuais do card
        val ivIcone: ImageView = viewDoItem.findViewById(R.id.ivIconeItem)
        val tvNome: TextView = viewDoItem.findViewById(R.id.tvNomeItem)
        val tvDescricao: TextView = viewDoItem.findViewById(R.id.tvDescricaoItem)
        val btnResgatar: MaterialButton = viewDoItem.findViewById(R.id.btnResgatar)



        val btnvoltar = findViewById<ImageButton>(R.id.button_voltar)
        btnvoltar.setOnClickListener {
            val intent = Intent(this, TelaAlunoActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Popula os dados
        ivIcone.setImageResource(item.iconeResId)
        tvNome.text = item.nome

        // ======================================================================
        // ✨ MUDANÇA PRINCIPAL: Criando e colorindo o texto da descrição ✨
        // ======================================================================

        // 1. Define as duas partes do texto
        val parte1 = "${item.descricao} por "
        val parte2 = "${item.valor} pontos"

        // 2. Cria um SpannableStringBuilder para juntar as partes com estilo
        val builder = SpannableStringBuilder(parte1 + parte2)

        // 3. Define a cor que será aplicada (use sua cor personalizada)
        // Você pode usar uma cor pré-definida como Color.GREEN ou a sua
        val corVerde = resources.getColor(R.color.verdeescuro, null) // Recomendado

        // 4. Aplica o estilo de cor APENAS na segunda parte do texto
        builder.setSpan(
            ForegroundColorSpan(corVerde),
            parte1.length, // Início da aplicação do estilo
            builder.length, // Fim da aplicação do estilo
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // 5. Define o texto estilizado no TextView
        tvDescricao.text = builder
        // ======================================================================

        btnResgatar.setOnClickListener {
            Toast.makeText(this, "Resgatando ${item.nome}...", Toast.LENGTH_SHORT).show()
        }

        containerDeItens.addView(viewDoItem)
    }

    // A função carregarItens() permanece a mesma
    private fun carregarItens(): List<ItemLoja> {
        return listOf(
            ItemLoja(1, "Voucher R$ 10", "Use em lojas parceiras", 1500, R.drawable.logo),
            ItemLoja(2, "Ingresso Cinema", "Válido para qualquer filme", 3000, R.drawable.logo),
            ItemLoja(3, "Wallpaper Exclusivo", "Baixe para seu celular", 500, R.drawable.logo),
            ItemLoja(4, "Desconto 20%", "Em produtos selecionados", 2200, R.drawable.logo)
        )
    }
}
