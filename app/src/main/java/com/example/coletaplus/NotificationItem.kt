package com.example.coletaplus

data class NotificationItem(
    val title: String,
    val content: String,
    val time: String,
    val type: NotificationType
)

enum class NotificationType {
    ALERT,
    FRIEND,
    SYSTEM
}