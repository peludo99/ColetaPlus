package com.example.coletaplus.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.coletaplus.R
import com.example.coletaplus.databinding.ActivityTelaAlunoBinding
import com.example.coletaplus.GamificationFragment
import com.example.coletaplus.HubFragment // Assumindo que este Fragment também está na raiz
import com.example.coletaplus.NotificationsFragment



class TelaAlunoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTelaAlunoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaAlunoBinding.inflate(layoutInflater)
        setContentView(binding.root) }}

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
