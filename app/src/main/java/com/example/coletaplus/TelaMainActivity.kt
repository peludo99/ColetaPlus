
package com.example.coletaplus

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class TelaMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_main)

        // Chama as funções para organizar o código
        configurarTextoDoTextView6()
        configurarTextoDoTextView9()
        configurarBotaoRegistrar()
    }

    /**
     * Configura o TextView com ID 'textView6', colorindo as palavras "mudança" e "precisa!".
     */
    private fun configurarTextoDoTextView6() {
        val meuTextView: TextView = findViewById(R.id.textView6)
        val textoCompleto = "A mudança que o nosso campus precisa!"
        val spannable = SpannableStringBuilder(textoCompleto)
        val corVerde: Int = ContextCompat.getColor(this, R.color.verde)

        // Palavras a serem coloridas
        val palavra1 = "mudança"
        val palavra2 = "precisa!"

        // Encontra a posição e aplica a cor na primeira palavra
        val inicioPalavra1 = textoCompleto.indexOf(palavra1)
        if (inicioPalavra1 != -1) {
            val fimPalavra1 = inicioPalavra1 + palavra1.length
            spannable.setSpan(
                ForegroundColorSpan(corVerde),
                inicioPalavra1,
                fimPalavra1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        // Encontra a posição e aplica a cor na segunda palavra
        val inicioPalavra2 = textoCompleto.indexOf(palavra2)
        if (inicioPalavra2 != -1) {
            val fimPalavra2 = inicioPalavra2 + palavra2.length
            spannable.setSpan(
                ForegroundColorSpan(corVerde),
                inicioPalavra2,
                fimPalavra2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        meuTextView.text = spannable
    }


    private fun configurarTextoDoTextView9() {
        val textViewLoginLink: TextView = findViewById(R.id.textView9)
        val textoCompleto = "Já possui uma conta? Log in"
        val spannable = SpannableStringBuilder(textoCompleto)
        val corVerde: Int = ContextCompat.getColor(this, R.color.verde)

        val palavraParaColorir = "Log in"
        val inicio = textoCompleto.indexOf(palavraParaColorir)

        if (inicio != -1) {


            val fim = inicio + palavraParaColorir.length
            // Aplica a cor verde
            spannable.setSpan(
                ForegroundColorSpan(corVerde),
                inicio,
                fim,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            // Aplica o estilo de negrito
            spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                inicio,
                fim,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        textViewLoginLink.text = spannable
    }

    /**
     * Configura o OnClickListener do botão de registro para navegar para a TelaDeRegistroActivity.
     */
    private fun configurarBotaoRegistrar() {
        val botaoRegistrar: Button = findViewById(R.id.button_registrar)

        botaoRegistrar.setOnClickListener {
            // Cria a intenção de navegar da tela atual (this) para a tela de registro.
            val intent = Intent(this, CadastroActivity::class.java)

            // Inicia a nova Activity.
            startActivity(intent)
        }


        val btnlogin: TextView = findViewById(R.id.textView9)
        btnlogin.setOnClickListener {  val intent = Intent(this, LoginActivity::class.java)

            // Inicia a nova Activity.
            startActivity(intent) }
    }
}
