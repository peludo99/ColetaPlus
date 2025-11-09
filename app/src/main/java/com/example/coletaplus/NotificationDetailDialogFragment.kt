package com.example.coletaplus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class NotificationDetailDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Assume que você criará este layout em res/layout/dialog_notification_detail.xml
        return inflater.inflate(R.layout.dialog_notification_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        if (args != null) {
            // 1. Recupera os dados passados pelo Fragment
            val title = args.getString(KEY_TITLE)
            val content = args.getString(KEY_CONTENT)
            val time = args.getString(KEY_TIME)

            // 2. Configura as Views
            view.findViewById<TextView>(R.id.text_detail_title).text = title
            view.findViewById<TextView>(R.id.text_detail_content).text = content
            view.findViewById<TextView>(R.id.text_detail_time).text = time
            // Configura o botão de fechar
            view.findViewById<ImageButton>(R.id.btn_close_detail).setOnClickListener {
                dismiss()
            }
        }
    }

    // Opcional: Define um estilo para o DialogFragment (para tela cheia ou fundo transparente)
    // NOVO CÓDIGO SUGERIDO para NotificationDetailDialogFragment.kt
    override fun getTheme(): Int {
        // Se você estiver usando o androidx.appcompat (suporte a temas antigos)
        return androidx.appcompat.R.style.Theme_AppCompat_Dialog

    }

    companion object {
        const val TAG = "NotificationDetailDialog"
        private const val KEY_TITLE = "title"
        private const val KEY_CONTENT = "content"
        private const val KEY_TIME = "time"

        /**
         * Método seguro para criar uma instância e passar dados via Bundle.
         */
        fun newInstance(title: String, content: String, time: String): NotificationDetailDialogFragment {
            val args = Bundle().apply {
                putString(KEY_TITLE, title)
                putString(KEY_CONTENT, content)
                putString(KEY_TIME, time)
            }
            return NotificationDetailDialogFragment().apply {
                arguments = args
            }
        }
    }
}