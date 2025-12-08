// Arquivo: DetalheNotificacaoDialogFragment.kt
package com.example.coletaplus

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager // NOVO: Import necessário
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class DetalheNotificacaoDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Infla o layout do nosso modal.
        return inflater.inflate(R.layout.dialog_notificacao_detalhe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Pega os dados enviados pela Activity.
        val titulo = arguments?.getString(ARG_TITULO)
        val mensagem = arguments?.getString(ARG_MENSAGEM)
        val dataHoraString = arguments?.getString(ARG_DATA_HORA)

        // Encontra os componentes da UI.
        val tvTituloDetalhe: TextView = view.findViewById(R.id.tvTituloDetalhe)
        val tvDataHoraDetalhe: TextView = view.findViewById(R.id.tvDataHoraDetalhe)
        val tvMensagemDetalhe: TextView = view.findViewById(R.id.tvMensagemDetalhe)
        val botaoFechar: Button = view.findViewById(R.id.botaoFecharModal)

        // Preenche os componentes com os dados.
        tvTituloDetalhe.text = titulo
        tvMensagemDetalhe.text = mensagem

        // Converte a data de String para LocalDateTime e formata.
        if (dataHoraString != null) {
            val dataHora = LocalDateTime.parse(dataHoraString)
            val formatador = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy, HH:mm")
            tvDataHoraDetalhe.text = dataHora.format(formatador)
        }

        // Define a ação para fechar o modal.
        botaoFechar.setOnClickListener {
            dismiss()
        }
    }

    // ===================================================================
    // ✨ CÓDIGO ADICIONADO PARA RESOLVER O PROBLEMA DO LAYOUT ESPREMIDO ✨
    // ===================================================================
    override fun onStart() {
        super.onStart()
        dialog?.window?.let { window ->
            val layoutParams = WindowManager.LayoutParams()
            // Copia os atributos da janela atual
            layoutParams.copyFrom(window.attributes)
            // Define a largura como 90% da tela e a altura como adaptável ao conteúdo
            layoutParams.width = (resources.displayMetrics.widthPixels * 0.90).toInt()
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            // Aplica os novos atributos
            window.attributes = layoutParams
        }
    }
    // ===================================================================

    // Objeto companheiro para criar a instância do fragmento de forma segura.
    companion object {
        private const val ARG_TITULO = "arg_titulo"
        private const val ARG_MENSAGEM = "arg_mensagem"
        private const val ARG_DATA_HORA = "arg_data_hora"

        // Método fábrica: cria o fragmento e passa os dados via Bundle.
        fun newInstance(titulo: String, mensagem: String, dataHora: LocalDateTime): DetalheNotificacaoDialogFragment {
            val fragment = DetalheNotificacaoDialogFragment()
            val args = Bundle().apply {
                putString(ARG_TITULO, titulo)
                putString(ARG_MENSAGEM, mensagem)
                putString(ARG_DATA_HORA, dataHora.toString())
            }
            fragment.arguments = args
            return fragment
        }
    }
}
