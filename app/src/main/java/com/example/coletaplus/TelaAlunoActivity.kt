package com.example.coletaplus // Verifique se o nome do seu pacote está correto

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.semantics.text
import androidx.core.content.ContextCompat
import com.example.coletaplus.databinding.ActivityTelaAlunoBinding // A importação do seu View Binding
import com.google.firebase.auth.FirebaseAuth // <-- 1. IMPORTAÇÃO NECESSÁRIA PARA AUTENTICAÇÃO
import com.google.firebase.auth.FirebaseUser // <-- 2. IMPORTAÇÃO PARA O OBJETO DO USUÁRIO

class TelaAlunoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTelaAlunoBinding

    // --- NOVA VARIÁVEL ---
    // Variável para acessar a autenticação do Firebase.
    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        const val EXTRA_TITULO = "EXTRA_TITULO_ATIVIDADE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaAlunoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- NOVA INICIALIZAÇÃO ---
        // Inicializa a instância do Firebase Auth.
        firebaseAuth = FirebaseAuth.getInstance()

        // --- NOVAS CHAMADAS DE FUNÇÃO ---
        loadUserData() // Carrega os dados do usuário na UI.

        setupActivityClickListeners()
        setupTabListeners()
    }

    /**
     * --- FUNÇÃO TOTALMENTE NOVA ---
     * Verifica o usuário atualmente logado no Firebase e atualiza a UI com seus dados.
     */
    private fun loadUserData() {
        // 1. Pega o usuário que está atualmente logado.
        val firebaseUser: FirebaseUser? = firebaseAuth.currentUser

        if (firebaseUser != null) {
            // 2. Se existe um usuário logado, extrai suas informações.

            // O nome de exibição pode ser nulo se não foi definido durante o registro.
            val userName = firebaseUser.displayName
            val userEmail = firebaseUser.email

            // 3. Atualiza os TextViews com os dados, usando o binding.

            // Verifica se o nome não é nulo ou vazio antes de exibir.
            if (!userName.isNullOrEmpty()) {
                binding.tvUserName.text = userName
            } else {
                // Caso o nome não esteja disponível, você pode exibir uma parte do email.
                binding.tvUserName.text = userEmail?.split("@")?.get(0) ?: "Usuário"
            }

            binding.tvUserEmail.text = userEmail

        } else {
            // 4. Se NENHUM usuário estiver logado, é uma boa prática
            //    redirecionar para a tela de login para evitar crashes ou dados vazios.
            Toast.makeText(this, "Nenhum usuário logado.", Toast.LENGTH_SHORT).show()
            // Exemplo: startActivity(Intent(this, LoginActivity::class.java))
            // finish() // Fecha a TelaAlunoActivity para que o usuário não possa voltar para ela sem logar.
        }
    }

    /**
     * Centraliza a configuração dos listeners de clique para as atividades.
     */
    private fun setupActivityClickListeners() {
        // (Esta função permanece exatamente como estava)
        setupClickListener(binding.itemSemanaDoPlastico, binding.tvSemanaDoPlastico)
        setupClickListener(binding.itemLeitor, binding.tvLeitor)
        setupClickListener(binding.itemOlhosDeAguia, binding.tvOlhosDeAguia)
    }

    /**
     * Função auxiliar que define a ação de clique para um item de atividade.
     */
    private fun setupClickListener(itemClicavel: View, textViewFonte: TextView) {
        // (Esta função permanece exatamente como estava)
        itemClicavel.setOnClickListener {
            val titulo = textViewFonte.text.toString()
            val intent = Intent(this, GamificationRankingActivity::class.java)
            intent.putExtra(EXTRA_TITULO, titulo)
            startActivity(intent)
        }
    }

    /**
     * Configura os listeners de clique para as abas.
     */
    private fun setupTabListeners() {
        // (Esta função permanece exatamente como estava)
        binding.tabInscritasContainer.setOnClickListener {
            selectTab(isIncritasSelected = true)
        }
        binding.tabDisponiveisContainer.setOnClickListener {
            selectTab(isIncritasSelected = false)
        }
    }

    /**
     * Atualiza a aparência das abas e a visibilidade do conteúdo.
     */
    private fun selectTab(isIncritasSelected: Boolean) {
        // (Esta função permanece exatamente como estava)
        val activeColor = ContextCompat.getColor(this, R.color.green_card_dark)
        val inactiveColor = ContextCompat.getColor(this, R.color.grey)

        if (isIncritasSelected) {
            binding.tvTabInscritas.setTextColor(activeColor)
            binding.indicatorInscritas.visibility = View.VISIBLE
            binding.layoutContentInscritas.visibility = View.VISIBLE
            binding.tvTabDisponiveis.setTextColor(inactiveColor)
            binding.indicatorDisponiveis.visibility = View.INVISIBLE
            binding.layoutContentDisponiveis.visibility = View.INVISIBLE
        } else {
            binding.tvTabInscritas.setTextColor(inactiveColor)
            binding.indicatorInscritas.visibility = View.INVISIBLE
            binding.layoutContentInscritas.visibility = View.INVISIBLE
            binding.tvTabDisponiveis.setTextColor(activeColor)
            binding.indicatorDisponiveis.visibility = View.VISIBLE
            binding.layoutContentDisponiveis.visibility = View.VISIBLE
        }
    }
}
