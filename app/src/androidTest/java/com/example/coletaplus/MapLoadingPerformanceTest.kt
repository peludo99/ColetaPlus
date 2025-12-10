package com.example.coletaplus

import android.util.Log

import androidx.test.core.app.ActivityScenario

// Importa as funções principais do framework Espresso para interagir com a UI.
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId

// Importa o executor de testes do AndroidX, que permite que o teste rode em um dispositivo.
import androidx.test.ext.junit.runners.AndroidJUnit4

// Importa a anotação @Test do JUnit, que marca uma função como um caso de teste executável.
import org.junit.Test

// Importa a anotação @RunWith do JUnit, que define qual executor de teste será usado.
import org.junit.runner.RunWith

// ----------------------------------------------------------------------------------
// FIM DO BLOCO DE IMPORTAÇÕES
// ----------------------------------------------------------------------------------


/**
 * Teste de UI (Instrumentado) para validar o Requisito Não Funcional RNF02.
 *
 * RNF02: O sistema deve carregar o mapa de pontos de coleta em até 3 segundos.
 *
 * Este teste mede o tempo que leva desde o lançamento da Activity do mapa
 * até que o componente do mapa esteja efetivamente visível para o usuário.
 */
@RunWith(AndroidJUnit4::class) // Define que este teste deve ser executado no ambiente Android.
class MapLoadingPerformanceTest {

    @Test // Marca esta função como um teste que o sistema deve executar.
    fun testMapLoadingTime_RNF02_comMedicao() {

        // --- 1. PREPARAÇÃO (Arrange) ---


        val idDoComponenteDoMapa = R.id.mapViewSimpleloc
        val classeDaActivityDoMapa = TelaInicialActivity::class.java



        val limiteDeTempoMs = 3000L
        // --- 2. AÇÃO E MEDIÇÃO (Act & Measure) ---

        Log.d("PerformanceTest", "Iniciando teste de desempenho RNF02...")

        // Marca o tempo de início, EXATAMENTE antes de iniciar a Activity.
        val startTime = System.currentTimeMillis()

        // Lança a Activity manualmente. A partir deste momento, o tempo está contando.
        val scenario = ActivityScenario.launch(classeDaActivityDoMapa)

        // --- 3. VERIFICAÇÃO E CONCLUSÃO (Assert) ---


        onView(withId(idDoComponenteDoMapa)).check(matches(isDisplayed()))

        // Se o código chegou até aqui, significa que o mapa está visível. Marcamos o tempo de fim.
        val endTime = System.currentTimeMillis()

        // Calculamos a duração total da operação em milissegundos.
        val duration = endTime - startTime

        // Registramos o resultado no Logcat para análise posterior.
        // Você pode ver esta mensagem na aba "Logcat" do Android Studio,
        // filtrando pela tag "PerformanceTest".
        Log.i("PerformanceTest", "RNF02 - Tempo de Carregamento Automático do Mapa: $duration ms")

        // Verificação final e explícita do requisito de tempo.
        // Esta é a verificação mais importante do teste. O teste falhará se a duração exceder nosso limite.
        assert(duration < limiteDeTempoMs) {
            "FALHA NO TESTE DE DESEMPENHO: O tempo de carregamento do mapa ($duration ms) excedeu o limite de $limiteDeTempoMs ms."
        }

        // Se o código chegar até esta linha, o teste passou em todos os quesitos.
        Log.i("PerformanceTest", "SUCESSO: O desempenho do mapa está dentro do limite esperado.")

        // Limpa e fecha a activity para garantir que ela não interfira em outros testes.
        scenario.close()
    }
}
