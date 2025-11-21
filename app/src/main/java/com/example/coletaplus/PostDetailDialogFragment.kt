package com.example.coletaplus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class PostDetailDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Usa o layout que acabamos de criar
        return inflater.inflate(R.layout.activity_ranking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        if (args != null) {
            // 1. Recupera os dados (ex: passados de um Feed ou da PostagemActivity)
            val author = args.getString(KEY_AUTHOR)
            val description = args.getString(KEY_DESCRIPTION)
            val score = args.getInt(KEY_SCORE, 0)
            val date = args.getString(KEY_DATE)
            // Nota: Lógica para carregar a imagem (via URL/Uri) iria aqui (ex: Glide/Picasso)

            // 2. Configura as Views
            view.findViewById<TextView>(R.id.text_post_author).text = "Postagem de $author"
            view.findViewById<TextView>(R.id.text_post_description).text = description
            view.findViewById<TextView>(R.id.text_post_score).text = "🏆 +$score Pontos"
            view.findViewById<TextView>(R.id.text_post_date).text = "Postado em $date"

            // 3. Configura o botão de fechar
            view.findViewById<ImageButton>(R.id.btn_close_post_detail).setOnClickListener {
                dismiss()
            }
        }
    }

    companion object {
        const val TAG = "PostDetailDialog"
        private const val KEY_AUTHOR = "author"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_SCORE = "score"
        private const val KEY_DATE = "date"

        /**
         * Método seguro para criar uma instância e passar dados via Bundle.
         */
        fun newInstance(author: String, description: String, score: Int, date: String): PostDetailDialogFragment {
            val args = Bundle().apply {
                putString(KEY_AUTHOR, author)
                putString(KEY_DESCRIPTION, description)
                putInt(KEY_SCORE, score)
                putString(KEY_DATE, date)
            }
            return PostDetailDialogFragment().apply {
                arguments = args
            }
        }
    }
}