package com.example.coletaplus

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment

// Certifique-se que ActivityDetailDialogFragment está no pacote principal
import com.example.coletaplus.ActivityDetailDialogFragment

// O construtor vincula a classe ao arquivo de layout XML
class HubFragment : Fragment(R.layout.fragment_hub) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Encontra e configura os botões de Atividade

        // Botão "Semana do plástico"
        val btnSemanaPlastico = view.findViewById<ImageButton>(R.id.btnSemanaPlastico)
        btnSemanaPlastico.setOnClickListener {
            // Abre o modal de detalhe para a Atividade 1
            showActivityDetail(activityId = 1)
        }

        // Botão "Leitor"
        val btnLeitor = view.findViewById<ImageButton>(R.id.btnLeitor)
        btnLeitor.setOnClickListener {
            // Abre o modal de detalhe para a Atividade 2
            showActivityDetail(activityId = 2)
        }

        // Botão "Olhos de água"
        val btnOlhosAgua = view.findViewById<ImageButton>(R.id.btnOlhosAgua)
        btnOlhosAgua.setOnClickListener {
            // Abre o modal de detalhe para a Atividade 3
            showActivityDetail(activityId = 3)
        }

        // 2. Lógica para carregar os dados do usuário

        // Aqui você adicionaria a lógica para carregar o nome, nível, XP e pontuação
        // dos elementos TextView e ProgressBar que estão no seu fragment_hub.xml.
    }

    /**
     * Função auxiliar para abrir o modal de detalhe da atividade.
     * @param activityId O ID da atividade para carregar os dados específicos no modal.
     */
    private fun showActivityDetail(activityId: Int) {
        // Cria uma nova instância do DialogFragment, passando o ID da atividade
        val dialog = ActivityDetailDialogFragment.newInstance(activityId)

        // Exibe o modal
        dialog.show(parentFragmentManager, ActivityDetailDialogFragment.TAG)
    }
}