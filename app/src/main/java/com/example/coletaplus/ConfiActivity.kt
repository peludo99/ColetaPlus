package com.example.coletaplus

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ConfiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_confi)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }











            // Encontre o seu ImageView pelo ID
                    val botaoVoltar: ImageView = findViewById(R.id.botaovoltar)

            // Configure o listener de clique
                    botaoVoltar.setOnClickListener {
                        // 1. Crie o Intent para a sua tela inicial
                        val intent = Intent(this, TelaInicialActivity::class.java)

                        // 2. Adicione as Flags para limpar a pilha de navegação
                        // FLAG_ACTIVITY_CLEAR_TOP: Se a TelaInicialActivity já existir na pilha,
                        // todas as atividades acima dela serão finalizadas.
                        // FLAG_ACTIVITY_NEW_TASK: Garante que a atividade seja iniciada em uma nova
                        // tarefa, o que funciona bem em conjunto com CLEAR_TOP.
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                        // 3. Inicie a Activity
                        startActivity(intent)

                        // 4. (Opcional, mas recomendado) Finalize a tela atual
                        // A flag CLEAR_TASK já faz isso, mas por garantia, podemos adicionar.
                        finish()
        }
    }
}