package com.example.coletaplus

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater // Import necessário
import android.view.View
import android.widget.Button // Import necessário
import android.widget.EditText // Import necessário
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RadioButton // Import necessário
import android.widget.RadioGroup // Import necessário
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog // Import necessário
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.coletaplus.Classes.Lixeira // Garanta que o import da sua classe está correto
import com.example.coletaplus.Classes.RepositorioDados
import com.google.firebase.database.*
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

// Data class para o feedback (pode ficar aqui ou em um arquivo separado)
data class FeedbackLixeira(
    val idLixeira: String,
    val nivel: String,
    val descricao: String?,
    val timestamp: Long = System.currentTimeMillis()
)

class TelaInicialActivity : AppCompatActivity() {

    private var mapview: MapView? = null
    // As variáveis do painel não são mais estritamente necessárias para a nova lógica,
    // mas vamos mantê-las caso você ainda use o 'botaoFecharPainel' para outra coisa.
    private lateinit var painelLixeira: FrameLayout
    private lateinit var botaoFecharPainel: View

    // Lista para armazenar os feedbacks
    private val listaDeFeedbacks = ArrayList<FeedbackLixeira>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_inicial)

        // Inicialização das views
        painelLixeira = findViewById(R.id.view3)
        botaoFecharPainel = findViewById(R.id.button1) // Este botão agora está "órfão", mas não causa erro.

        val btnuser = findViewById<ImageView>(R.id.tuser)
        btnuser.setOnClickListener { irParaTelaDoUsuario() }


        val btnconfi: View = findViewById(R.id.configuracoes)
        btnconfi.setOnClickListener {
            val intent = Intent(this, ConfiActivity::class.java)
            startActivity(intent)
            finish()
        }


        // O painel não será mais aberto, então este botão não terá efeito visível
        botaoFecharPainel.setOnClickListener {
            painelLixeira.visibility = View.GONE
        }

        // Configuração do mapa
        Configuration.getInstance().load(applicationContext, getSharedPreferences("osmdroid", MODE_PRIVATE))
        mapview = findViewById(R.id.mapViewSimpleloc)
        creatMap()
    }

    private fun creatMap() {
        mapview!!.setTileSource(TileSourceFactory.MAPNIK)
        mapview!!.setMultiTouchControls(true)
        mapview!!.controller.setZoom(18.5)
        mapview!!.zoomController.setVisibility(org.osmdroid.views.CustomZoomButtonsController.Visibility.NEVER)

        val database = FirebaseDatabase.getInstance()
        val lixeirasRef = database.getReference("lixeiras")


        val btnnoti: ImageView = findViewById(R.id.nuser)
        btnnoti.setOnClickListener {
            val intent = Intent(this, NotificacaoActivity::class.java)
            startActivity(intent)
            finish()
        }

        lixeirasRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (lixeiraSnapshot in snapshot.children) {
                    val id = lixeiraSnapshot.key ?: continue // Pega o ID da lixeira

                    val cor = lixeiraSnapshot.child("cor").getValue(String::class.java) ?: "Azul"
                    val latitude = lixeiraSnapshot.child("latitude").getValue(Double::class.java) ?: 0.0
                    val longitude = lixeiraSnapshot.child("longitude").getValue(Double::class.java) ?: 0.0
                    val descricao = lixeiraSnapshot.child("descricao").getValue(String::class.java) ?: ""

                    // Cria o objeto Lixeira usando sua classe e passando o ID
                    val lixeira = Lixeira(id, cor, GeoPoint(latitude, longitude), descricao)

                    val marcador = Marker(mapview)
                    marcador.position = lixeira.loc
                    marcador.title = "Lixeira ${lixeira.cor}"
                    marcador.subDescription = lixeira.descricao
                    when (lixeira.cor) {
                        "Azul" -> marcador.setIcon(ContextCompat.getDrawable(this@TelaInicialActivity, R.drawable.trash_azul))
                        "Verde" -> marcador.setIcon(ContextCompat.getDrawable(this@TelaInicialActivity, R.drawable.trash_verde))
                        "Vermelha" -> marcador.setIcon(ContextCompat.getDrawable(this@TelaInicialActivity, R.drawable.trash_vermelha))
                    }
                    marcador.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    marcador.relatedObject = lixeira // Salva o objeto Lixeira completo no marcador

                    // =================================================================
                    // ✨ MUDANÇA PRINCIPAL: LÓGICA DO CLIQUE NO MARCADOR ✨
                    // =================================================================
                    marcador.setOnMarkerClickListener { marker, _ ->
                        // 1. Recupera o objeto Lixeira salvo no marcador
                        val lixeiraClicada = marker.relatedObject as? Lixeira ?: return@setOnMarkerClickListener true

                        // 2. Chama diretamente a função para abrir o modal de feedback
                        abrirModalDeFeedback(lixeiraClicada.id, "Lixeira ${lixeiraClicada.cor}")

                        // Retorna 'true' para indicar que o evento foi consumido e não deve mostrar o infowindow padrão
                        true
                    }
                    // =================================================================

                    mapview!!.overlays.add(marcador)
                }
                // Centraliza o mapa após carregar as lixeiras
                mapview!!.controller.setCenter(GeoPoint(-8.017621, -34.944313))
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@TelaInicialActivity, "Erro ao carregar lixeiras: ${error.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    // Função que cria e exibe o modal de feedback (sem alterações)
    private fun abrirModalDeFeedback(idLixeira: String, nomeLixeira: String) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_feedback_lixeira, null)

        val tituloModal: TextView = dialogView.findViewById(R.id.tvTituloModal)
        val rgNivel: RadioGroup = dialogView.findViewById(R.id.rgNivelLixeira)
        val etDescricao: EditText = dialogView.findViewById(R.id.etDescricao)
        val btnEnviar: Button = dialogView.findViewById(R.id.btnEnviar)
        val btnCancelar: Button = dialogView.findViewById(R.id.btnCancelar)

        tituloModal.text = "Feedback da $nomeLixeira"

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        btnEnviar.setOnClickListener {
            val idRadioButtonSelecionado = rgNivel.checkedRadioButtonId
            if (idRadioButtonSelecionado == -1) {
                Toast.makeText(this, "Por favor, selecione um nível.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val radioButtonSelecionado: RadioButton = dialogView.findViewById(idRadioButtonSelecionado)
            val nivelSelecionado = radioButtonSelecionado.text.toString()
            val descricaoInserida = etDescricao.text.toString().trim()

            val novoFeedback = FeedbackLixeira(
                idLixeira = idLixeira,
                nivel = nivelSelecionado,
                descricao = if (descricaoInserida.isNotEmpty()) descricaoInserida else null
            )

            listaDeFeedbacks.add(novoFeedback)
            Toast.makeText(this, "Feedback enviado com sucesso!", Toast.LENGTH_SHORT).show()
            println("Feedback para Lixeira ID $idLixeira: $novoFeedback") // Linha para debug
            dialog.dismiss()
        }

        btnCancelar.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    // --- Suas outras funções permanecem aqui (irParaTelaDoUsuario, onPause, onResume, etc.) ---
    private fun irParaTelaDoUsuario() {
        val usuario = RepositorioDados.usuarioLogado
        if (usuario == null) {
            Toast.makeText(this, "Usuário não logado", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, TelaMainActivity::class.java))
            return
        }

        val intent = when (usuario.tipo) {
            "SERVIDOR", "PROFESSOR" -> Intent(this, HubActivity::class.java)
            "ALUNO" -> Intent(this, TelaAlunoActivity::class.java)
            else -> Intent(this, TelaMainActivity::class.java)
        }
        startActivity(intent)
    }
    // ...
}
