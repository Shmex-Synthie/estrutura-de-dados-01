import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class ExperimentoExclusao {

    private static final int CAPACIDADE = 100000;
    private static final int EXECUCOES = 100;
    private static long[][] tempos = new long[6][EXECUCOES];
    private static Random random = new Random();

    public static void main(String[] args) {
        try {
            executarExperimento();
            System.out.println("Experimento de exclusão concluído.");
            System.out.println("Arquivo gerado: resultados_exclusao.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void executarExperimento() throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter("resultados_exclusao.csv"));

        writer.println("ordenacao,entrada,execucao,tempo_ns");

        processarCenario(writer, true, "crescente");
        processarCenario(writer, true, "decrescente");
        processarCenario(writer, true, "aleatoria");
        processarCenario(writer, false, "crescente");
        processarCenario(writer, false, "decrescente");
        processarCenario(writer, false, "aleatoria");

        writer.println();
        writer.println("ordenacao,entrada,media_ns,desvio_padrao_ns");

        resumir(writer, true, "crescente");
        resumir(writer, true, "decrescente");
        resumir(writer, true, "aleatoria");
        resumir(writer, false, "crescente");
        resumir(writer, false, "decrescente");
        resumir(writer, false, "aleatoria");

        writer.close();
    }

    private static void processarCenario(PrintWriter writer, boolean crescente, String entrada) {
        int indice = indiceCenario(crescente, entrada);

        for (int execucao = 0; execucao < EXECUCOES; execucao++) {
            ArranjoOrdenado arr = new ArranjoOrdenado(CAPACIDADE, crescente);
            int[] valores = gerarValores(entrada);

            for (int v : valores) {
                arr.inserir(v);
            }

            long t1 = System.nanoTime();

            for (int v : valores) {
                arr.deletar(v);
            }

            long t2 = System.nanoTime();
            long total = t2 - t1;

            tempos[indice][execucao] = total;

            String tipoOrdenacao = crescente ? "crescente" : "decrescente";
            writer.printf("%s,%s,%d,%d%n", tipoOrdenacao, entrada, execucao + 1, total);

            System.out.println(
                    "Ordenação=" + tipoOrdenacao +
                    " | Entrada=" + entrada +
                    " | Execução=" + (execucao + 1) +
                    " | Tempo=" + total + " ns");
        }
    }

    private static int[] gerarValores(String entrada) {
        int[] valores = new int[CAPACIDADE];

        if (entrada.equals("crescente")) {
            for (int i = 0; i < CAPACIDADE; i++) {
                valores[i] = i;
            }
        } else if (entrada.equals("decrescente")) {
            for (int i = 0; i < CAPACIDADE; i++) {
                valores[i] = CAPACIDADE - i;
            }
        } else {
            for (int i = 0; i < CAPACIDADE; i++) {
                valores[i] = random.nextInt();
            }
        }

        return valores;
    }

    private static int indiceCenario(boolean crescente, String entrada) {
        if (crescente && entrada.equals("crescente")) return 0;
        if (crescente && entrada.equals("decrescente")) return 1;
        if (crescente && entrada.equals("aleatoria")) return 2;
        if (!crescente && entrada.equals("crescente")) return 3;
        if (!crescente && entrada.equals("decrescente")) return 4;
        return 5;
    }

    private static void resumir(PrintWriter writer, boolean crescente, String entrada) {
        int indice = indiceCenario(crescente, entrada);
        long[] valores = tempos[indice];

        double media = media(valores);
        double desvio = desvioPadrao(valores, media);

        writer.printf("%s,%s,%.2f,%.2f%n",
                crescente ? "crescente" : "decrescente",
                entrada,
                media,
                desvio);
        System.out.println("------------------------------------------");
        System.out.println("Ordenação: " + (crescente ? "crescente" : "decrescente"));
        System.out.println("Entrada: " + entrada);
        System.out.println("Média: " + media + " ns");
        System.out.println("Desvio padrão: " + desvio + " ns");
    }

    private static double media(long[] valores) {
        double soma = 0;
        for (long v : valores) soma += v;
        return soma / valores.length;
    }

    private static double desvioPadrao(long[] valores, double media) {
        double soma = 0;
        for (long v : valores) {
            double d = v - media;
            soma += d * d;
        }
        return Math.sqrt(soma / valores.length);
    }
} 