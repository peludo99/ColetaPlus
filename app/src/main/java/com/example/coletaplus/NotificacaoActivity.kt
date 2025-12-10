// Arquivo: NotificacaoActivity.kt
package com.example.coletaplus

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.google.firebase.database.FirebaseDatabase
import java.time.Instant
import java.time.ZoneId


@RequiresApi(Build.VERSION_CODES.O)
class NotificacaoActivity : AppCompatActivity() {

    private lateinit var containerDeNotificacoes: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // PONTO CRÍTICO: Certifique-se de que este nome de arquivo está 100% correto!
        setContentView(R.layout.activity_notificacao)

        // Lógica para ajustar o padding da tela (evita sobreposição com barras de sistema)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializa o contêiner e o botão de voltar
        containerDeNotificacoes = findViewById(R.id.containerDeNotificacoes)
        configurarBotaoVoltar()

        // Busca e exibe as notificações
        carregarNotificacoesDoFirebase()
    }

    private fun configurarBotaoVoltar() {
        val btnvoltar = findViewById<ImageButton>(R.id.button_voltar)
        btnvoltar.setOnClickListener {
            val intent = Intent(this, TelaInicialActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun adicionarViewDeNotificacao(notificacao: Notificacao) {
        val inflater = LayoutInflater.from(this)
        val viewDaNotificacao = inflater.inflate(R.layout.item_notificacao, containerDeNotificacoes, false)

        val tvTitulo: TextView = viewDaNotificacao.findViewById(R.id.tvTituloNotificacao)
        val tvMensagem: TextView = viewDaNotificacao.findViewById(R.id.tvMensagemNotificacao)
        val tvDataHora: TextView = viewDaNotificacao.findViewById(R.id.tvDataHoraNotificacao)
        viewDaNotificacao.findViewById<ImageView>(R.id.ivIconeNotificacao)

        tvTitulo.text = notificacao.titulo
        tvMensagem.text = notificacao.mensagem.take(80).let { if (notificacao.mensagem.length > 80) "$it..." else it }
        tvDataHora.text = notificacao.dataHora.format(DateTimeFormatter.ofPattern("dd MMM, HH:mm"))

        // Ao clicar, cria uma instância do DialogFragment e o exibe
        viewDaNotificacao.setOnClickListener {
            val modal = DetalheNotificacaoDialogFragment.newInstance(
                notificacao.titulo,
                notificacao.mensagem,
                notificacao.dataHora
            )
            modal.show(supportFragmentManager, "DetalheNotificacaoDialog")
        }

        containerDeNotificacoes.addView(viewDaNotificacao)
    }

    // Função para gerar dados de exemplo
    private fun carregarNotificacoesDoFirebase() {
        val dbRef = FirebaseDatabase.getInstance().getReference("notificacoes")

        dbRef.get().addOnSuccessListener { snapshot ->
            if (!snapshot.exists()) return@addOnSuccessListener

            val listaNotificacoes = mutableListOf<Notificacao>()

            for (notifSnap in snapshot.children) {
                val titulo = notifSnap.child("titulo").getValue(String::class.java) ?: continue
                val mensagem = notifSnap.child("mensagem").getValue(String::class.java) ?: continue
                val timestamp = notifSnap.child("dataHora").getValue(Long::class.java) ?: continue

                // Converter millis → LocalDateTime
                val dataHora = Instant.ofEpochMilli(timestamp)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()

                listaNotificacoes.add(Notificacao(titulo, mensagem, dataHora))
            }

            // Ordenar notificações da mais recente para a mais antiga
            listaNotificacoes.sortByDescending { it.dataHora }

            // Adicionar na tela
            for (notificacao in listaNotificacoes) {
                adicionarViewDeNotificacao(notificacao)
            }

        }.addOnFailureListener {
            println("ERRO AO CARREGAR NOTIFICAÇÕES: ${it.message}")
        }
    }

}

// A data class para estruturar os dados de uma notificação
@RequiresApi(Build.VERSION_CODES.O)
data class Notificacao(
    val titulo: String,
    val mensagem: String,
    val dataHora: LocalDateTime
)
