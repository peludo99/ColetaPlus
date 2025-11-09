package com.example.coletaplus

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PostagemActivity : AppCompatActivity() {

    private val REQUEST_IMAGE_CAPTURE = 1 // Código de requisição da câmera/galeria

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_postagem)

        val btnPublish = findViewById<Button>(R.id.btn_publish)
        val framePhotoContainer = findViewById<FrameLayout>(R.id.frame_photo_container)

        // 1. Lógica do clique na área da foto
        framePhotoContainer.setOnClickListener {
            // Chamada para abrir a câmera ou galeria.
            // Aqui você usaria um Intent para a câmera ou para a galeria (MediaStore.ACTION_IMAGE_CAPTURE)
            Toast.makeText(this, "Abrindo Câmera/Galeria...", Toast.LENGTH_SHORT).show()
        }

        // 2. Lógica do clique no botão Publicar
        btnPublish.setOnClickListener {
            // TODO:
            // 1. Validar campos (Título e Foto)
            // 2. Fazer upload da foto e enviar dados para o servidor/repositório
            // 3. Mostrar mensagem de sucesso (Toast ou Dialog)
            // 4. Finalizar a Activity (finish())
            Toast.makeText(this, "Publicando atividade...", Toast.LENGTH_LONG).show()
            finish()
        }

        // 3. Lógica para Data e Hora
        // Você precisaria de um DatePickerDialog e um TimePickerDialog aqui
        // findViewById<Button>(R.id.btn_select_date).setOnClickListener { /* ... */ }
        // findViewById<Button>(R.id.btn_select_time).setOnClickListener { /* ... */ }
    }

    // Método para receber o resultado da câmera/galeria
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // Lógica para pegar a foto, exibir no ImageView e esconder o placeholder
            // Toast.makeText(this, "Foto capturada e pronta!", Toast.LENGTH_SHORT).show()
        }
    }
}