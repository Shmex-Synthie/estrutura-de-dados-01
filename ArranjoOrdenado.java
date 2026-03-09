import java.util.function.Consumer;

public class ArranjoOrdenado {
    private int[] vetor;
    private int capacidade;
    private int tamanho;
    private boolean crescente;

    public ArranjoOrdenado(int capacidade, boolean crescente) {
        this.capacidade = capacidade;
        this.vetor = new int[capacidade];
        this.tamanho = 0;
        this.crescente = crescente;
    }

    public void inserir(int valor) {
        if (tamanho == capacidade) {
            throw new IllegalStateException("Arranjo cheio.");
        }
        int pos = 0;
        if (crescente) {
            while (pos < tamanho && vetor[pos] < valor) {
                pos++;
            }
        } else {
            while (pos < tamanho && vetor[pos] > valor) {
                pos++;
            }
        }
        for (int i = tamanho; i > pos; i--) {
            vetor[i] = vetor[i - 1];
        }
        vetor[pos] = valor;
        tamanho++;
    }

    public void deletar(int valor) {
        int pos = buscar(valor);
        if (pos == -1) {
            return;
        }
        for (int i = pos; i < tamanho - 1; i++) {
            vetor[i] = vetor[i + 1];
        }

        tamanho--;
    }
    public int buscar(int valor) {
        for (int i = 0; i < tamanho; i++) {
            if (vetor[i] == valor) {
                return i;
            }
        }
        return -1;
    }
    public void percorrer(Consumer<Integer> consumer) {
        for (int i = 0; i < tamanho; i++) {
            consumer.accept(vetor[i]);
        }
    }
    public int get(int indice) {
        if (indice < 0 || indice >= tamanho) {
            throw new IndexOutOfBoundsException("Índice inválido.");
        }
        return vetor[indice];
    }
    public int getTamanho() {
        return tamanho;
    }
    public int getCapacidade() {
        return capacidade;
    }
    public boolean isCrescente() {
        return crescente;
    }
    public void setCrescente(boolean crescente) {
        this.crescente = crescente;
        reordenar();
    }
    private void reordenar() {
        for (int i = 0; i < tamanho - 1; i++) {
            for (int j = 0; j < tamanho - 1 - i; j++) {
                boolean foraDeOrdem;

                if (crescente) {
                    foraDeOrdem = vetor[j] > vetor[j + 1];
                } else {
                    foraDeOrdem = vetor[j] < vetor[j + 1];
                }

                if (foraDeOrdem) {
                    int temp = vetor[j];
                    vetor[j] = vetor[j + 1];
                    vetor[j + 1] = temp;
                }
            }
        }
    }
}