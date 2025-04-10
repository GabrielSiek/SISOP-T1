
public class Processo {

    String id;
    int tempoES;
    int tempoTotalCPU;
    int tempoBloqueado;
    int tempoExecucao;
    int surtoCPU;
    int ordem;
    int prioridade;
    int creditosRestantes;
    boolean estaBloqueado;
    boolean finalizado;


    public Processo(String id, int surtoCPU, int tempoES, int tempoTotalCPU, int ordem, int prioridade) {
        this.id = id;
        this.surtoCPU = surtoCPU;
        this.tempoES = tempoES;
        this.tempoTotalCPU = tempoTotalCPU;
        this.tempoExecucao = 0;
        this.ordem = ordem;
        this.prioridade = prioridade;
        this.creditosRestantes = prioridade;
        this.tempoBloqueado = 0;
        this.estaBloqueado = false;
        this.finalizado = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTempoES() {
        return tempoES;
    }

    public void setTempoES(int tempoES) {
        this.tempoES = tempoES;
    }

    public int getTempoTotalCPU() {
        return tempoTotalCPU;
    }

    public void setTempoTotalCPU(int tempoTotalCPU) {
        this.tempoTotalCPU = tempoTotalCPU;
    }

    public int getTempoBloqueado() {
        return tempoBloqueado;
    }

    public void setTempoBloqueado(int tempoBloqueado) {
        this.tempoBloqueado = tempoBloqueado;
    }

    public int getTempoExecucao() {
        return tempoExecucao;
    }

    public void setTempoExecucao(int tempoExecucao) {
        this.tempoExecucao = tempoExecucao;
    }

    public int getSurtoCPU() {
        return surtoCPU;
    }

    public void setSurtoCPU(int surtoCPU) {
        this.surtoCPU = surtoCPU;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public int getCreditosRestantes() {
        return creditosRestantes;
    }

    public void setCreditosRestantes(int creditosRestantes) {
        this.creditosRestantes = creditosRestantes;
    }

    public boolean isEstaBloqueado() {
        return estaBloqueado;
    }

    public void setEstaBloqueado(boolean estaBloqueado) {
        this.estaBloqueado = estaBloqueado;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public void executar() {
        if (creditosRestantes > 0 && !estaBloqueado) {
            creditosRestantes--;
            tempoTotalCPU--;
            tempoExecucao++;
        }
    }



    public boolean getEstaPronto() {
        return !estaBloqueado && tempoTotalCPU > 0;
    }

    public boolean getEstaTerminado() {
        return tempoTotalCPU <= 0;
    }

    public boolean getEstaBloqueado() {
        return estaBloqueado;
    }


    public void bloquear() {
        if (tempoExecucao >= surtoCPU && !estaBloqueado && tempoES > 0) {
            estaBloqueado = true;
            tempoBloqueado = tempoES;
            System.out.println(id + " está bloqueado por " + tempoBloqueado + " unidades de tempo.");
            tempoExecucao = 0;
        }
    }

    public void desbloquear() {
        estaBloqueado = false;

    }

    public void atualizarEstado() {
        if (tempoTotalCPU <= 0) {
            finalizado = true;
        }
    }


    public void print(boolean isRunning) {

        String estado = "";

        if (isRunning) {
            estado = "Running";
        } else if (estaBloqueado) {
            estado = "Blocked";
        } else if (getEstaTerminado()) {
            estado = "Exit";
        } else {
            estado = "Ready";
        }

        System.out.println("Nome: " + id
                + " | Estado: " + estado
                + " | Prioridade: " + prioridade
                + " | Créditos: " + creditosRestantes
                + " | TempoCPU: " + tempoTotalCPU
                + " | TempoES: " + tempoES
                + " | SurtoCPU: " + surtoCPU
                + " | TempoBloqueado: " + tempoBloqueado);
    }

}