package com.example.coletaplus

import android.os.Bundle
import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class TelaInicialActivity : AppCompatActivity() {


    private var mapview: MapView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        Configuration.getInstance().load(applicationContext, getSharedPreferences("osmdroid_settings",MODE_PRIVATE))
        setContentView(R.layout.activity_tela_inicial)

        requestForPermissions()

        mapview = findViewById<MapView>(R.id.mapViewSimpleloc)

        creatMap()



    }



    private fun creatMap()
    {
        mapview!!.setTileSource(TileSourceFactory.MAPNIK)
        mapview!!.setMultiTouchControls(true)
        mapview!!.controller.setZoom(19.0)


        val startpoit = GeoPoint(-8.017621, -34.944313)
        val lixeira = GeoPoint(-8.017779, -34.944761)
        val lixeira1 = GeoPoint(-8.017740, -34.944608)
        val lixeira2 = GeoPoint(-8.017234, -34.945035)

        mapview!!.controller.setCenter(startpoit)




        val lixeirastartmaker = Marker(mapview)
        lixeirastartmaker.position = lixeira
        lixeirastartmaker.title = "Lixeira"
        lixeirastartmaker.subDescription = "Azul"

        // CORREÇÃO: Use setIcon() para definir a imagem do marcador.
        lixeirastartmaker.setIcon(ContextCompat.getDrawable(this, R.drawable.trash_sharp))

        mapview!!.overlays.add(lixeirastartmaker)



        val lixeiras2tartmaker = Marker(mapview)
        lixeiras2tartmaker.position = lixeira2
        lixeiras2tartmaker.title = "Lixeira"
        lixeiras2tartmaker.subDescription = "vermelha"


        mapview!!.overlays.add(lixeiras2tartmaker)

        val lixeiras1tartmaker = Marker(mapview)
        lixeiras1tartmaker.position = lixeira1
        lixeiras1tartmaker.title = "Lixeira"
        lixeiras1tartmaker.subDescription = "Azul"


        mapview!!.overlays.add(lixeiras1tartmaker)







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