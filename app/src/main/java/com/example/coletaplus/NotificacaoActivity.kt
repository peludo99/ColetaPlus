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
        val listaDeNotificacoes = obterNotificacoesFalsas()
        for (notificacao in listaDeNotificacoes) {
            adicionarViewDeNotificacao(notificacao)
        }
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
    private fun obterNotificacoesFalsas(): List<Notificacao> {
        val agora = LocalDateTime.now()
        return listOf(
            Notificacao("Dica da Semana", "Você sabia que garrafas PET podem ser transformadas em tecido? Este processo não só economiza recursos, como também reduz o desperdício em aterros sanitários. Continue reciclando para apoiar essa causa!", agora.minusMinutes(5)),
            Notificacao("Lembrete de Coleta", "Não se esqueça, sua coleta de plásticos está agendada para amanhã às 10h. Por favor, deixe os materiais em um local visível e de fácil acesso para nossos coletores. Agradecemos sua colaboração!", agora.minusHours(2)),
            Notificacao("Nova Conquista", "Parabéns! Você alcançou o nível 'Iniciante' por sua primeira coleta. Continue participando para desbloquear novas recompensas e subir no ranking de sustentabilidade.", agora.minusDays(1)),
            Notificacao("Bem-vindo!", "Seu registro em nossa plataforma foi concluído com sucesso. Explore o aplicativo para encontrar pontos de coleta, agendar retiradas e acompanhar seu impacto ambiental. Estamos felizes em tê-lo conosco!", agora.minusWeeks(1))
        )
    }
}

// A data class para estruturar os dados de uma notificação
@RequiresApi(Build.VERSION_CODES.O)
data class Notificacao(
    val titulo: String,
    val mensagem: String,
    val dataHora: LocalDateTime
)
