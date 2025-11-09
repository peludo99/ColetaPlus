package com.example.coletaplus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment

class ActivityDetailDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Usa o layout que acabamos de criar
        return inflater.inflate(R.layout.dialog_atividade_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura o botão de fechar
        val btnClose: ImageButton = view.findViewById(R.id.btn_close)
        btnClose.setOnClickListener {
            dismiss() // Fecha o modal
        }

        // Aqui você configuraria o título, descrição e ícone da atividade
        // com base nos argumentos (Bundle) passados ao DialogFragment.

        // Exemplo de como configurar a ação principal
        /*
        val btnAction: Button = view.findViewById(R.id.btn_main_action)
        btnAction.setOnClickListener {
            // Lógica para iniciar a atividade ou ir para a tela de ação
        }
        */
    }

    // Estilo para o Dialog (Opcional: Garante que o fundo não seja cinza escuro)
    // NOVO CÓDIGO para a função getTheme()
    // NOVO CÓDIGO para a função getTheme()
    override fun getTheme(): Int {
        // Isso usa o tema padrão do Android para diálogos
        return android.R.style.Theme_DeviceDefault_Dialog
        // OU Theme_AppCompat_Dialog (Se você estiver usando AppCompat)
    }

    companion object {
        const val TAG = "ActivityDetailDialog"
        fun newInstance(activityId: Int): ActivityDetailDialogFragment {
            // Use o newInstance para passar argumentos, se necessário
            val args = Bundle().apply {
                putInt("ACTIVITY_ID", activityId)
            }
            return ActivityDetailDialogFragment().apply {
                arguments = args
            }
        }
    }
}