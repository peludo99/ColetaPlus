package com.example.coletaplus

import android.os.Bundle
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars


import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.semantics.text

import androidx.core.content.ContextCompat
import com.example.coletaplus.Classes.Lixeira
import com.example.coletaplus.ui.TelaAlunoActivity
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker


class TelaInicialActivity : AppCompatActivity() {


    private var mapview: MapView? = null
    private lateinit var painelLixeira: FrameLayout
    private lateinit var botaoFecharPainel: View




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_inicial)
        painelLixeira = findViewById(R.id.view3)
        botaoFecharPainel = findViewById(R.id.button1)



        val btnuser = findViewById<ImageView>(R.id.tuser)

        val btnuserconfi = findViewById<View>(R.id.configuracoes)


        btnuserconfi.setOnClickListener {

            val intent = Intent(this, ConfiActivity::class.java)


            startActivity(intent)


        }


        btnuser.setOnClickListener {

            val intent = Intent(this, HubActivity::class.java)


            startActivity(intent)

        }


        botaoFecharPainel.setOnClickListener {
            // Esconde o painel com uma animação suave
            painelLixeira.animate()
                .alpha(0f)
                .setDuration(300)
                .withEndAction {
                    painelLixeira.visibility = View.GONE
                }
                .start()
        }





        // Inicializa o osmdroid
        Configuration.getInstance().load(applicationContext, getSharedPreferences("osmdroid", MODE_PRIVATE))

        mapview = findViewById(R.id.mapViewSimpleloc)
        creatMap()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        // Garante que o modo imersivo seja reativado quando a janela ganhar foco
        if (hasFocus) {
            hideSystemUI()
        }
    }

    private fun hideSystemUI() {        // Esta função esconde as barras do sistema
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Para Android 11 (API 30) e superior
            window.insetsController?.let { controller ->
                // CORREÇÃO: Usamos o caminho completo para evitar ambiguidade com o Compose
                controller.hide(
                    android.view.WindowInsets.Type.statusBars() or
                            android.view.WindowInsets.Type.navigationBars()
                )
                controller.systemBarsBehavior =
                    android.view.WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            // Para versões mais antigas do Android (antes do 11)
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // Esconde a barra de navegação
                            or View.SYSTEM_UI_FLAG_FULLSCREEN // Esconde a barra de status
                    )
        }
    }

    private fun creatMap()
    {
        mapview!!.setTileSource(TileSourceFactory.MAPNIK)
        mapview!!.setMultiTouchControls(true)
        mapview!!.controller.setZoom(18.5)

        mapview!!.zoomController.setVisibility(org.osmdroid.views.CustomZoomButtonsController.Visibility.NEVER)


        mapview!!.setMultiTouchControls(true)


        val listaDeLixeiras = listOf(
            Lixeira(
                cor = "Azul",
                loc = GeoPoint(-8.017779, -34.944761),
                descricao = "Coleta de papel e papelão."
            ), Lixeira(
                cor = "Verde",
                loc = GeoPoint(-8.017759, -34.944761),
                descricao = "Coleta de papel e papelão."
            ),
            Lixeira(
                cor = "Vermelha",
                loc = GeoPoint(-8.017749, -34.944761),
                descricao = "Coleta de papel e papelão."
            ),
            Lixeira(
                cor = "Azul",
                loc = GeoPoint(-8.017740, -34.944608),
                descricao = "Coleta de vidro."
            ), Lixeira(
                cor = "Verde",
                loc = GeoPoint(-8.017740, -34.944628),
                descricao = "Coleta de vidro."
            ),
            Lixeira(
                cor = "Vermelha",
                loc = GeoPoint(-8.017740, -34.944648),
                descricao = "Coleta de vidro."
            ),
            Lixeira(
                cor = "Azul",
                loc = GeoPoint(-8.017234, -34.945035),
                descricao = "Coleta de plástico."
            ),Lixeira(
                cor = "Verde",
                loc = GeoPoint(-8.017234, -34.945055),
                descricao = "Coleta de plástico."
            ),Lixeira(
                cor = "Vermelha",
                loc = GeoPoint(-8.017234, -34.945075),
                descricao = "Coleta de plástico."
            )

            ,
            Lixeira(
                cor = "Azul",
                loc = GeoPoint(  -8.016186567497662, -34.944943656798515),
                descricao = "Coleta de plástico."
            ),Lixeira(
                cor = "Verde",
                loc = GeoPoint(  -8.016186567497682, -34.944943656798515),
                descricao = "Coleta de plástico."
            ),Lixeira(
                cor = "Vermelha",
                loc = GeoPoint(  -8.016186567497692, -34.944943656798515),
                descricao = "Coleta de plástico."
            )

            ,
            Lixeira(
                cor = "Azul",
                loc = GeoPoint(    -8.018296, -34.944729),
                descricao = "Coleta de plástico."
            ),Lixeira(
                cor = "Verde",
                loc = GeoPoint(    -8.018296, -34.944749),
                descricao = "Coleta de plástico."
            ),Lixeira(
                cor = "Vermelha",
                loc = GeoPoint(    -8.018296, -34.944769),
                descricao = "Coleta de plástico."
            )

            ,
            Lixeira(
                cor = "Azul",
                loc = GeoPoint(      -8.018725, -34.944431),
                descricao = "Coleta de plástico."
            ),Lixeira(
                cor = "Verde",
                loc = GeoPoint(      -8.018725, -34.944451),
                descricao = "Coleta de plástico."
            ),Lixeira(
                cor = "Vermelha",
                loc = GeoPoint(      -8.018725, -34.94471),
                descricao = "Coleta de plástico."
            )

            ,
            Lixeira(
                cor = "Azul",
                loc = GeoPoint(       -8.014836, -34.947638),
                descricao = "Coleta de plástico."
            ),Lixeira(
                cor = "Verde",
                loc = GeoPoint(       -8.014836, -34.947658),
                descricao = "Coleta de plástico."
            ),Lixeira(
                cor = "Vermelha",
                loc = GeoPoint(       -8.014836, -34.947678),
                descricao = "Coleta de plástico."
            )
        )












        // Dentro da sua função creatMap()

        for (lixeira in listaDeLixeiras) {
            val marcador = Marker(mapview)
            marcador.position = lixeira.loc
            marcador.title = "Lixeira ${lixeira.cor}"
            marcador.subDescription = lixeira.descricao

            // Define o ícone baseado na cor
            when(lixeira.cor) {
                "Azul" -> marcador.setIcon(ContextCompat.getDrawable(this, R.drawable.trash_azul))
                "Verde" -> marcador.setIcon(ContextCompat.getDrawable(this, R.drawable.trash_verde))
                "Vermelha" -> marcador.setIcon(ContextCompat.getDrawable(this, R.drawable.trash_vermelha))
            }

            marcador.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)


            marcador.relatedObject = lixeira

            marcador.setOnMarkerClickListener { marker, _ ->
                // 1. Agora esta linha vai funcionar, pois o relatedObject existe!
                val lixeiraClicada = marker.relatedObject as? Lixeira
                if (lixeiraClicada == null) {
                    // Esta verificação de segurança continua sendo uma boa prática.
                    return@setOnMarkerClickListener true
                }

                // 2. Encontra os componentes de UI dentro do painel
                val iconePainel = painelLixeira.findViewById<ImageView>(R.id.imageView2)
                val tituloPainel = painelLixeira.findViewById<TextView>(R.id.textView2)

                // 3. Atualiza o título do painel
                tituloPainel.text = "Lixeira ${lixeiraClicada.cor}"

                // 4. Define o ícone correto baseado na cor
                when (lixeiraClicada.cor) {
                    "Azul" -> iconePainel.setBackgroundResource(R.drawable.trash_azul)
                    "Verde" -> iconePainel.setBackgroundResource(R.drawable.trash_verde)
                    "Vermelha" -> iconePainel.setBackgroundResource(R.drawable.trash_vermelha)
                    else -> iconePainel.setBackgroundResource(R.drawable.ic_launcher_background) // Ícone padrão
                }

                // 5. Torna o painel visível
                painelLixeira.alpha = 0f
                painelLixeira.visibility = View.VISIBLE
                painelLixeira.animate().alpha(1f).setDuration(300).start()

                // Retorna true para indicar que o clique foi tratado
                true
            }

            mapview!!.overlays.add(marcador)
        }




        val startpoit = GeoPoint(-8.017621, -34.944313)


        mapview!!.controller.setCenter(startpoit)








    }


    override fun onPause() {
        super.onPause()
        mapview!!.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapview!!.onResume()
    }

    private fun requestForPermissions() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )


        if (ActivityCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, permissions[1]) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, permissions, 100)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String?>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)


        if(requestCode == 100)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permissões concedidas", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(this, "Permissões negadas", Toast.LENGTH_SHORT).show()
            }
        }
    }



























}


