import java.util.ArrayList;
import java.util.List;

public class Escalonador {
    private List<Processo> processos = new ArrayList<>();
    private int tempo = 1;
    private int limiteTempoTotal = 0;
    private Processo processoExecutando;
    private int indiceAtual = -1;

    public void adicionarProcesso(Processo p) {
        processos.add(p);
        limiteTempoTotal += p.tempoTotalCPU;
    }

    public void executarEscalonamento() {

        while (tempo < limiteTempoTotal && processos.stream().anyMatch(p -> !p.getEstaTerminado())) {

            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Tempo: " + tempo);

            if (todosProcessosSemCredito()) {
                redistribuirCreditos();
                System.out.println("Todos os processos sem crédito. Redistribuindo créditos...");
                processoExecutando = selecionarProximoProcesso();

                continue;
            }

            if (processoExecutando == null || processoExecutando.getEstaTerminado() || processoExecutando.getEstaBloqueado()
                    || processoExecutando.creditosRestantes == 0) {
                processoExecutando = selecionarProximoProcesso();
            }

            if (processoExecutando == null) {
                System.out.println("Nenhum processo pronto para ser executado.");
            } else {
                System.out.println("Processo selecionado: " + processoExecutando.id);
            }

            if (processoExecutando != null) {
                processoExecutando.executar();
                processoExecutando.bloquear();
                processoExecutando.atualizarEstado();
            }

            for (Processo processo : processos) {
                if (processo != processoExecutando && processo.estaBloqueado) {
                    processo.tempoBloqueado--;
                    if (processo.tempoBloqueado == 0) {
                        processo.desbloquear();
                    }
                }


            }

                mostraProcessos(processoExecutando);

            tempo++;
            System.out.println();
        }
    }

    private Processo selecionarProximoProcesso() {
        int numProcessos = processos.size();
        int tentativas = 0;

        while (tentativas < numProcessos) {
            indiceAtual = (indiceAtual + 1) % numProcessos;
            Processo candidato = processos.get(indiceAtual);

            if (candidato.getEstaPronto() && candidato.creditosRestantes > 0) {
                return candidato;
            }

            tentativas++;
        }

        return null;
    }

    private boolean todosProcessosSemCredito() {
        return processos.stream().noneMatch(p -> p.creditosRestantes > 0 && !p.estaBloqueado && !p.getEstaTerminado());
    }

    private void redistribuirCreditos() {
        for (Processo p : processos) {
            if (!p.getEstaTerminado()) {
                p.creditosRestantes = p.creditosRestantes / 2 + p.prioridade;
            }
        }
    }



    public void mostraProcessos(Processo processoAtual) {
        System.out.println("\nTempo: " + tempo + " | Executando: "
                + (processoAtual != null ? processoAtual.getId() : "Nenhum"));

        String estado = "";

        if (processoAtual.getEstaPronto()) {
            estado = "Ready";
        } else if (processoAtual.estaBloqueado) {
            estado = "Blocked";
        } else if (processoAtual.getEstaTerminado()) {
            estado = "Finished";
        } else {
            estado = "Running";
        }

        for (Processo p : processos) {
            System.out.println("Nome: " + p.getId()
                    + " | Estado: " + estado
                    + " | Prioridade: " + p.getPrioridade()
                    + " | Créditos: " + p.getCreditosRestantes()
                    + " | TempoCPU: " + p.getTempoTotalCPU()
                    + " | TempoES: " + p.getTempoES()
                    + " | SurtoCPU: " + p.getSurtoCPU()
                    + " | TempoBloqueado: " + p.getTempoBloqueado());
        }
    }
}
