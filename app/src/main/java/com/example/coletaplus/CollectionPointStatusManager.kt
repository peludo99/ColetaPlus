/**
 * Classe que centraliza a lógica para determinar o status de um ponto de coleta.
 * Este é o "cérebro" que toma decisões com base nos reportes dos usuários.
 */
object CollectionPointStatusManager {

    /**
     * Define os possíveis status que um ponto de coleta pode ter.
     */
    enum class PointStatus {
        AVAILABLE,       // Disponível
        FULL,            // Cheio (confirmado)
        NEEDS_CHECKING,  // Precisa de verificação (reportes conflitantes)
        IN_MAINTENANCE   // Em manutenção
    }

    /**
     * A unidade de lógica que vamos testar: decide o novo status de um ponto de coleta.
     *
     * @param currentStatus O status atual do ponto de coleta.
     * @param recentFullReports A quantidade de reportes "cheio" recentes.
     * @param recentAvailableReports A quantidade de reportes "disponível" recentes.
     * @return O novo status que o ponto de coleta deve ter.
     */
    fun determineNewStatus(
        currentStatus: PointStatus,
        recentFullReports: Int,
        recentAvailableReports: Int
    ): PointStatus {

        // --- CORREÇÃO APLICADA AQUI ---
        // REGRA DE MAIOR PRIORIDADE: Se um ponto está em manutenção, ele NÃO PODE ter seu status
        // alterado por reportes de usuários. Essa verificação deve vir primeiro.
        if (currentStatus == PointStatus.IN_MAINTENANCE) {
            return PointStatus.IN_MAINTENANCE
        }

        // Regra 1: Se 3 ou mais usuários reportam "cheio" e ninguém reporta "disponível", muda para CHEIO.
        if (recentFullReports >= 3 && recentAvailableReports == 0) {
            return PointStatus.FULL
        }

        // Regra 2: Se há muitos reportes misturados (ex: 2 de 'cheio' e 1 de 'disponível'), precisa de verificação.
        if (recentFullReports >= 2 && recentAvailableReports >= 1) {
            return PointStatus.NEEDS_CHECKING
        }

        // Regra 3: Se estava CHEIO, mas agora 2 ou mais usuários dizem que está disponível (após um esvaziamento), volta a ser DISPONÍVEL.
        if (currentStatus == PointStatus.FULL && recentAvailableReports >= 2) {
            return PointStatus.AVAILABLE
        }

        // Se nenhuma das regras de mudança se aplica, o status atual é mantido.
        return currentStatus
    }
}
