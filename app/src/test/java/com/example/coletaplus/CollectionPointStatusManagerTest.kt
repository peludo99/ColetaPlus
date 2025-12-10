import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Esta é a classe de teste para o nosso "cérebro" (CollectionPointStatusManager).
 * Cada função com '@Test' é um teste independente que verifica uma regra específica.
 */
class CollectionPointStatusManagerTest {

    /**
     * TESTE 1: A REGRA DO "PONTO CHEIO"
     * História: Uma lixeira está disponível, mas 3 usuários avisam que ela encheu.
     * O que deve acontecer? O status dela precisa mudar para CHEIO (FULL).
     */
    @Test
    fun `determineNewStatus DEVE mudar para FULL se 3 ou mais reportes de 'cheio' forem recebidos`() {
        // ARRANGE (Arrumar o cenário)
        val statusAtual = CollectionPointStatusManager.PointStatus.AVAILABLE // Estava disponível
        val reportesDeCheio = 3                                           // 3 pessoas disseram que está cheia
        val reportesDeDisponivel = 0                                      // Ninguém disse que está vazia

        // ACT (Executar a lógica)
        // Pedimos ao "cérebro" para decidir o novo status com base no cenário acima.
        val novoStatus = CollectionPointStatusManager.determineNewStatus(statusAtual, reportesDeCheio, reportesDeDisponivel)

        // ASSERT (Verificar o resultado)
        // Afirmamos que o 'novoStatus' DEVE ser igual a 'FULL'. Se for, o teste passa.
        assertEquals(
            "Com 3 reportes, o status deveria ser FULL",
            CollectionPointStatusManager.PointStatus.FULL,
            novoStatus
        )
    }

    /**
     * TESTE 2: A REGRA DA "CONFUSÃO"
     * História: Alguns usuários dizem que a lixeira está cheia, outros dizem que está disponível.
     * O que deve acontecer? O sistema fica "confuso" e marca a lixeira para verificação (NEEDS_CHECKING).
     */
    @Test
    fun `determineNewStatus DEVE mudar para NEEDS_CHECKING com reportes mistos`() {
        // ARRANGE
        val statusAtual = CollectionPointStatusManager.PointStatus.AVAILABLE
        val reportesDeCheio = 2        // 2 pessoas disseram 'cheia'
        val reportesDeDisponivel = 1   // 1 pessoa disse 'disponível'

        // ACT
        val novoStatus = CollectionPointStatusManager.determineNewStatus(statusAtual, reportesDeCheio, reportesDeDisponivel)

        // ASSERT
        // Afirmamos que o 'novoStatus' DEVE ser igual a 'NEEDS_CHECKING'.
        assertEquals(
            "Com reportes conflitantes, o status deveria ser NEEDS_CHECKING",
            CollectionPointStatusManager.PointStatus.NEEDS_CHECKING,
            novoStatus
        )
    }

    /**
     * TESTE 3: A REGRA DO "JÁ FOI LIMPA"
     * História: A lixeira estava marcada como CHEIA. Um servidor a esvaziou, e agora 2 usuários avisam que ela está disponível.
     * O que deve acontecer? O status dela deve voltar a ser DISPONÍVEL (AVAILABLE).
     */
    @Test
    fun `determineNewStatus DEVE mudar para AVAILABLE se estava 'cheio' e recebe 2+ reportes de 'disponível'`() {
        // ARRANGE
        val statusAtual = CollectionPointStatusManager.PointStatus.FULL // Antes, estava cheia
        val reportesDeCheio = 0
        val reportesDeDisponivel = 2 // 2 pessoas confirmaram que está vazia

        // ACT
        val novoStatus = CollectionPointStatusManager.determineNewStatus(statusAtual, reportesDeCheio, reportesDeDisponivel)

        // ASSERT
        // Afirmamos que o 'novoStatus' DEVE ser igual a 'AVAILABLE'.
        assertEquals(
            "Após ser limpa e confirmada, o status deveria ser AVAILABLE",
            CollectionPointStatusManager.PointStatus.AVAILABLE,
            novoStatus
        )
    }

    /**
     * TESTE 4: A REGRA DA "MANUTENÇÃO"
     * História: A lixeira está marcada como EM MANUTENÇÃO. Vários usuários interagem com ela.
     * O que deve acontecer? Nada. Ela deve continuar EM MANUTENÇÃO até que um administrador mude o status.
     */
    @Test
    fun `determineNewStatus DEVE permanecer IN_MAINTENANCE independentemente dos reportes`() {
        // ARRANGE
        val statusAtual = CollectionPointStatusManager.PointStatus.IN_MAINTENANCE
        val reportesDeCheio = 5       // Muitos reportes...
        val reportesDeDisponivel = 5  // ...não devem importar.

        // ACT
        val novoStatus = CollectionPointStatusManager.determineNewStatus(statusAtual, reportesDeCheio, reportesDeDisponivel)

        // ASSERT
        // Afirmamos que o 'novoStatus' DEVE continuar sendo 'IN_MAINTENANCE'.
        assertEquals(
            "O status não deveria mudar se estiver IN_MAINTENANCE",
            CollectionPointStatusManager.PointStatus.IN_MAINTENANCE,
            novoStatus
        )
    }

    /**
     * TESTE 5: A REGRA DO "NADA ACONTECE"
     * História: Uma lixeira está disponível e apenas uma pessoa avisa que ela está cheia.
     * O que deve acontecer? Nada ainda. Um único reporte não é suficiente para mudar o status.
     */
    @Test
    fun `determineNewStatus DEVE manter o status atual se nenhuma regra for atendida`() {
        // ARRANGE
        val statusAtual = CollectionPointStatusManager.PointStatus.AVAILABLE
        val reportesDeCheio = 1       // Apenas 1 reporte
        val reportesDeDisponivel = 0

        // ACT
        val novoStatus = CollectionPointStatusManager.determineNewStatus(statusAtual, reportesDeCheio, reportesDeDisponivel)

        // ASSERT
        // Afirmamos que o 'novoStatus' DEVE ser o mesmo que o 'statusAtual'.
        assertEquals(
            "Com poucos reportes, o status deveria permanecer o mesmo",
            CollectionPointStatusManager.PointStatus.AVAILABLE,
            novoStatus
        )
    }
}
