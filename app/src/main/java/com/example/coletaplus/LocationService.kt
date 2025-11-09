package com.example.coletaplus

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat



class LocationService : Service() {

    // Canal de notificação necessário para Android 8.0 (API 26) e superior
    private val NOTIFICATION_CHANNEL_ID = "LocationServiceChannel"

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("LocationService", "Serviço em segundo plano iniciado.")

        // 1. Crie a notificação persistente
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("ColetaPlus está ativo")
            .setContentText("Rastreando sua localização em segundo plano.")
            .setSmallIcon(R.drawable.logo) // Use um ícone seu! (ex: ic_map ou ic_launcher)
            .build()

        // 2. Inicie o serviço em primeiro plano
        // O número '1' é um ID único para esta notificação.
        startForeground(1, notification)

        // **AQUI VOCÊ COLOCARIA A LÓGICA DE RASTREAMENTO DE LOCALIZAÇÃO**
        // Por exemplo, usando FusedLocationProviderClient para obter atualizações periódicas.
        // Por enquanto, vamos apenas simular que ele está rodando.

        // Retorna START_STICKY para que o sistema tente recriar o serviço se ele for encerrado.
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        // Não estamos usando "binding", então retornamos null.
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LocationService", "Serviço em segundo plano encerrado.")
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Canal do Serviço de Localização",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}
