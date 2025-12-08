//package com.example.coletaplus
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//// Adicionado o lambda (onClick: (NotificationItem) -> Unit) no construtor
//class NotificationsAdapter(
//    private val notificationList: List<NotificationItem>,
//    private val onClick: (NotificationItem) -> Unit
//) :
//    RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder>() {
//
//    class NotificationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val textTitle: TextView = view.findViewById(R.id.text_notification_title)
//        val textContent: TextView = view.findViewById(R.id.text_notification_content)
//        val textTime: TextView = view.findViewById(R.id.text_notification_time)
//
//        // Função para ligar o item e configurar o clique
//        fun bind(item: NotificationItem, onClick: (NotificationItem) -> Unit) {
//            textTitle.text = item.title
//            textContent.text = item.content
//            textTime.text = item.time
//
//            // Configura o clique no item completo
//            itemView.setOnClickListener {
//                onClick(item)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_notification, parent, false)
//        return NotificationViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
//        val currentItem = notificationList[position]
//        // Chamamos o método bind, passando o item e a função de clique
//        holder.bind(currentItem, onClick)
//    }
//
//    override fun getItemCount() = notificationList.size
//}