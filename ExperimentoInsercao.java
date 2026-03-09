import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class ExperimentoInsercao {
    private static final int CAPACIDADE = 100_000;
    private static final int EXECUCOES = 100;

    public static void main(String[] args) {
        try {
            executarExperimento();
            System.out.println("Experimento concluído com sucesso.");
            System.out.println("Arquivo gerado: resultados_insercao.csv");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os resultados: " + e.getMessage());
        }
    }

    private static void executarExperimento() throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter("resultados_insercao.csv"));

        writer.println("ordenacao,entrada,execucao,tempo_ns");

        processarCenario(writer, true, "crescente");
        processarCenario(writer, true, "decrescente");
        processarCenario(writer, true, "aleatoria");

        processarCenario(writer, false, "crescente");
        processarCenario(writer, false, "decrescente");
        processarCenario(writer, false, "aleatoria");

        writer.println();
        writer.println("ordenacao,entrada,media_ns,desvio_padrao_ns");

        resumirCenario(writer, true, "crescente");
        resumirCenario(writer, true, "decrescente");
        resumirCenario(writer, true, "aleatoria");

        resumirCenario(writer, false, "crescente");
        resumirCenario(writer, false, "decrescente");
        resumirCenario(writer, false, "aleatoria");

        writer.close();
    }

    private static long[][] tempos = new long[6][EXECUCOES];

    private static void processarCenario(PrintWriter writer, boolean crescente, String entrada) {
        int indice = indiceCenario(crescente, entrada);

        for (int execucao = 0; execucao < EXECUCOES; execucao++) {
            ArranjoOrdenado arranjo = new ArranjoOrdenado(CAPACIDADE, crescente);

            long t1 = System.nanoTime();
            inserirDados(arranjo, entrada);
            long t2 = System.nanoTime();

            long total = t2 - t1;
            tempos[indice][execucao] = total;

            String tipoOrdenacao = crescente ? "crescente" : "decrescente";
            writer.printf("%s,%s,%d,%d%n", tipoOrdenacao, entrada, execucao + 1, total);
        }
    }

    private static void resumirCenario(PrintWriter writer, boolean crescente, String entrada) {
        int indice = indiceCenario(crescente, entrada);
        long[] valores = tempos[indice];

        double media = calcularMedia(valores);
        double desvioPadrao = calcularDesvioPadrao(valores, media);

        String tipoOrdenacao = crescente ? "crescente" : "decrescente";

        writer.printf("%s,%s,%.2f,%.2f%n", tipoOrdenacao, entrada, media, desvioPadrao);

        System.out.println("------------------------------------------");
        System.out.println("Ordenação: " + tipoOrdenacao);
        System.out.println("Entrada: " + entrada);
        System.out.printf("Média: %.2f ns%n", media);
        System.out.printf("Desvio padrão: %.2f ns%n", desvioPadrao);
    }

    private static void inserirDados(ArranjoOrdenado arranjo, String entrada) {
        Random random = new Random();

        if (entrada.equals("crescente")) {
            for (int i = 0; i < CAPACIDADE; i++) {
                arranjo.inserir(i);
            }
        } else if (entrada.equals("decrescente")) {
            for (int i = CAPACIDADE; i > 0; i--) {
                arranjo.inserir(i);
            }
        } else if (entrada.equals("aleatoria")) {
            for (int i = 0; i < CAPACIDADE; i++) {
                arranjo.inserir(random.nextInt());
            }
        }
    }

    private static int indiceCenario(boolean crescente, String entrada) {
        if (crescente && entrada.equals("crescente")) return 0;
        if (crescente && entrada.equals("decrescente")) return 1;
        if (crescente && entrada.equals("aleatoria")) return 2;
        if (!crescente && entrada.equals("crescente")) return 3;
        if (!crescente && entrada.equals("decrescente")) return 4;
        return 5;
    }

    private static double calcularMedia(long[] valores) {
        double soma = 0.0;

        for (long valor : valores) {
            soma += valor;
        }

        return soma / valores.length;
    }

    private static double calcularDesvioPadrao(long[] valores, double media) {
        double soma = 0.0;

        for (long valor : valores) {
            double diferenca = valor - media;
            soma += diferenca * diferenca;
        }

        return Math.sqrt(soma / valores.length);
    }
}