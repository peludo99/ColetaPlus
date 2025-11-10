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
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import com.example.coletaplus.Classes.Lixeira
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import com.google.firebase.database.*

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
        btnuser.setOnClickListener {
            val intent = Intent(this, HubActivity::class.java)
            startActivity(intent)
        }

        botaoFecharPainel.setOnClickListener {
            painelLixeira.animate()
                .alpha(0f)
                .setDuration(300)
                .withEndAction { painelLixeira.visibility = View.GONE }
                .start()
        }

        Configuration.getInstance().load(applicationContext, getSharedPreferences("osmdroid", MODE_PRIVATE))
        mapview = findViewById(R.id.mapViewSimpleloc)
        creatMap()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.let { controller ->
                controller.hide(
                    android.view.WindowInsets.Type.statusBars() or
                            android.view.WindowInsets.Type.navigationBars()
                )
                controller.systemBarsBehavior =
                    android.view.WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                    )
        }
    }

    private fun creatMap() {
        mapview!!.setTileSource(TileSourceFactory.MAPNIK)
        mapview!!.setMultiTouchControls(true)
        mapview!!.controller.setZoom(18.5)
        mapview!!.zoomController.setVisibility(org.osmdroid.views.CustomZoomButtonsController.Visibility.NEVER)
        mapview!!.setMultiTouchControls(true)

        val database = FirebaseDatabase.getInstance()
        val lixeirasRef = database.getReference("lixeiras")

        lixeirasRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (lixeiraSnapshot in snapshot.children) {
                    val cor = lixeiraSnapshot.child("cor").getValue(String::class.java) ?: "Azul"
                    val latitude = lixeiraSnapshot.child("latitude").getValue(Double::class.java) ?: 0.0
                    val longitude = lixeiraSnapshot.child("longitude").getValue(Double::class.java) ?: 0.0
                    val descricao = lixeiraSnapshot.child("descricao").getValue(String::class.java) ?: ""

                    val lixeira = Lixeira(cor, GeoPoint(latitude, longitude), descricao)

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
                    marcador.relatedObject = lixeira

                    marcador.setOnMarkerClickListener { marker, _ ->
                        val lixeiraClicada = marker.relatedObject as? Lixeira ?: return@setOnMarkerClickListener true
                        val iconePainel = painelLixeira.findViewById<ImageView>(R.id.imageView2)
                        val tituloPainel = painelLixeira.findViewById<TextView>(R.id.textView2)
                        tituloPainel.text = "Lixeira ${lixeiraClicada.cor}"
                        when (lixeiraClicada.cor) {
                            "Azul" -> iconePainel.setBackgroundResource(R.drawable.trash_azul)
                            "Verde" -> iconePainel.setBackgroundResource(R.drawable.trash_verde)
                            "Vermelha" -> iconePainel.setBackgroundResource(R.drawable.trash_vermelha)
                            else -> iconePainel.setBackgroundResource(R.drawable.ic_launcher_background)
                        }
                        painelLixeira.alpha = 0f
                        painelLixeira.visibility = View.VISIBLE
                        painelLixeira.animate().alpha(1f).setDuration(300).start()
                        true
                    }

                    mapview!!.overlays.add(marcador)
                }

                mapview!!.controller.setCenter(GeoPoint(-8.017621, -34.944313))
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@TelaInicialActivity, "Erro ao carregar lixeiras: ${error.message}", Toast.LENGTH_LONG).show()
            }
        })
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
            ActivityCompat.checkSelfPermission(this, permissions[1]) != PackageManager.PERMISSION_GRANTED
        ) {
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
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissões concedidas", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permissões negadas", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
