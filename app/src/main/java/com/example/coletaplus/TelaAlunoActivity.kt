package com.example.coletaplus

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.coletaplus.Classes.RepositorioDados
import com.example.coletaplus.R
import com.example.coletaplus.databinding.ActivityTelaAlunoBinding
import com.example.coletaplus.GamificationFragment
import com.example.coletaplus.HubFragment // Assumindo que este Fragment também está na raiz


class TelaAlunoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTelaAlunoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaAlunoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usuario = RepositorioDados.usuarioLogado
        val tvNomeUsuario = findViewById<TextView>(R.id.tvUserName)
        val tvEmailUsuario = findViewById<TextView>(R.id.tvUserEmail)

        val tabDisponiveisContainer = findViewById<LinearLayout>(R.id.tabDisponiveisContainer)
        val indicadorDisponiveis = findViewById<View>(R.id.indicatorDisponiveis)
        val indicadorInscritas = findViewById<View>(R.id.indicatorInscritas)
        val tvDisponiveis = findViewById<TextView>(R.id.tvTabDisponiveis)
        val tvTabGerenciamento = findViewById<TextView>(R.id.tvTabInscritas)
        val layoutContentGerenciamento = findViewById<LinearLayout>(R.id.layoutContentInscritas)
        val layoutContentCriacao = findViewById<LinearLayout>(R.id.layoutContentDisponiveis)
        val tabGerenciamentoContainer = findViewById<LinearLayout>(R.id.tabInscritasContainer)

        val colorActive = Color.parseColor("#1B5E20")
        val colorInactive = Color.parseColor("#9FA8DA")

        val ivSettings = findViewById<ImageView>(R.id.ivSettings)

        ivSettings.setOnClickListener {
            val intent = Intent(this, LojaActivity::class.java)
            startActivity(intent)
        }

        val btnnoti: ImageView = findViewById(R.id.nuser)
        btnnoti.setOnClickListener {
            val intent = Intent(this, NotificacaoActivity::class.java)
            startActivity(intent)
            finish()
        }








        tvNomeUsuario?.text = usuario?.nome ?: "Usuário desconhecido"
        tvEmailUsuario?.text = usuario?.email ?: "E-mail não disponível"

        val btnmap = findViewById<ImageView>(R.id.muser)
        btnmap.setOnClickListener {
            val intent = Intent(this, TelaInicialActivity::class.java)
            startActivity(intent)
        }


        tabGerenciamentoContainer.setOnClickListener {
            tvTabGerenciamento.setTextColor(colorActive)
            indicadorDisponiveis.visibility = View.VISIBLE

            tvDisponiveis.setTextColor(colorInactive)
            indicadorInscritas.visibility = View.INVISIBLE

            layoutContentGerenciamento.visibility = View.VISIBLE
            layoutContentCriacao.visibility = View.GONE
        }

        tabDisponiveisContainer.setOnClickListener {
            tvTabGerenciamento.setTextColor(colorInactive)
            indicadorDisponiveis.visibility = View.INVISIBLE

            tvDisponiveis.setTextColor(colorActive)
            indicadorInscritas.visibility = View.VISIBLE

            layoutContentGerenciamento.visibility = View.GONE
            layoutContentCriacao.visibility = View.VISIBLE
        }


    }
}
        // codigo comentado pois não esta rodando ( nada foi alterado apenas comentei) -cauan abraão

        /*    // Garante que o HubFragment seja o primeiro a ser carregado se não for o padrão
           if (savedInstanceState == null) {
               loadFragment(HubFragment())
           }

          binding.bottomNavigation.setOnItemSelectedListener { item ->
               when (item.itemId) {
                   R.id.nav_hub -> loadFragment(HubFragment())           // ID do menu
                   R.id.nav_gamification -> loadFragment(GamificationFragment()) // ID do menu
                   R.id.nav_notifications -> loadFragment(NotificationsFragment()) // ID do menu
                   else -> return@setOnItemSelectedListener false
               }
               true
           }
       }

       // Função utilitária para trocar o Fragment
       private fun loadFragment(fragment: Fragment) {
           supportFragmentManager.beginTransaction()
               .replace(R.id.fragment_container, fragment)
               .commit()
       } */
