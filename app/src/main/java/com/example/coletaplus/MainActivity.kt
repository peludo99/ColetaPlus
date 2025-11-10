package com.example.coletaplus

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.coletaplus.ui.theme.ColetaPlusTheme
import kotlinx.coroutines.delay

import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = FirebaseDatabase.getInstance()
        val testRef: DatabaseReference = database.getReference("testConnection")

        testRef.setValue("Conexão bem-sucedida!")
            .addOnSuccessListener {
                Toast.makeText(this, "Conexão com Firebase OK!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Falha na conexão com Firebase", Toast.LENGTH_SHORT).show()
            }

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                SplashScreen()
            }
        }
    }

    @Composable
    fun SplashScreen() {
        val context = LocalContext.current
        LaunchedEffect(true) {
            delay(5000)
            val intent = Intent(context, CadastroActivity::class.java)
            context.startActivity(intent)
            (context as? ComponentActivity)?.finish()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun SplashScreenPreview() {
        ColetaPlusTheme {
            SplashScreen()
        }
    }
}
