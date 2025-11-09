package com.example.coletaplus

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coletaplus.NotificationItem
import com.example.coletaplus.NotificationType
import com.example.coletaplus.NotificationsAdapter
import com.example.coletaplus.NotificationDetailDialogFragment // Novo modal de detalhe

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Assegura que o ID recycler_notifications existe no fragment_notifications.xml
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_notifications)

        // 1. Gera a lista de notificações
        val dummyList = generateDummyNotifications()

        // 2. Configura o RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        // 3. Cria e configura o Adapter, passando a função de clique
        val adapter = NotificationsAdapter(dummyList) { notification ->
            // Abre o modal de detalhe da notificação quando um item é clicado
            showNotificationDetail(notification)
        }
        recyclerView.adapter = adapter
    }

    /**
     * Função auxiliar para abrir o modal de detalhe da notificação.
     */
    private fun showNotificationDetail(notification: NotificationItem) {
        val dialog = NotificationDetailDialogFragment.newInstance(
            notification.title,
            notification.content,
            notification.time
        )
        // Usa childFragmentManager pois estamos em um Fragment
        dialog.show(childFragmentManager, NotificationDetailDialogFragment.TAG)
    }

    /**
     * Função para gerar dados de notificação de exemplo.
     */
    private fun generateDummyNotifications(): List<NotificationItem> {
        return listOf(
            NotificationItem("Alerta de Missão", "Sua Missão 'Semana do Plástico' expira amanhã!", "1h atrás", NotificationType.ALERT),
            NotificationItem("Conquista!", "Você ganhou o distintivo 'Bom Samaritano' por 500 pontos.", "3h atrás", NotificationType.SYSTEM),
            NotificationItem("Novo Amigo", "Tiago te adicionou como amigo. Clique para aceitar.", "1d atrás", NotificationType.FRIEND),
            NotificationItem("Alerta de Missão", "Faltam 2 dias para o fim da 'Semana do Metal'.", "2d atrás", NotificationType.ALERT)
        )
    }
}