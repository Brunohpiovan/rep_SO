import java.util.concurrent.ThreadLocalRandom;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExecutionTimes {
    private static final int EXECUTIONS = 30;  // Número de execuções para medir

    public static void main(String[] args) {
        final int N = 100_000_000;  // 100 milhões de elementos
        int[] array = ThreadLocalRandom.current().ints(N, 1, 100).toArray();
        List<Long> parallelTimes = new ArrayList<>();

        for (int i = 0; i < EXECUTIONS; i++) {
            // Medição do tempo para a implementação paralela
            long startTime = System.nanoTime();
            long sum = ParallelSum.parallelSum(array);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1_000_000;  // Converter para milissegundos
            parallelTimes.add(duration);
            System.out.println("Execução " + (i+1) + ": Soma Paralela = " + sum + ", Tempo = " + duration + " ms");
        }

        // Calcular e imprimir a média e o desvio padrão
        double mean = calculateMean(parallelTimes);
        double stdDev = calculateStandardDeviation(parallelTimes, mean);

        System.out.println("Média dos tempos de execução: " + mean + " ms");
        System.out.println("Desvio padrão dos tempos de execução: " + stdDev + " ms");

        // Gerar arquivo CSV
        generateCSV("execution_times.csv", parallelTimes, mean, stdDev);
    }

    public static double calculateMean(List<Long> times) {
        double sum = 0.0;
        for (Long time : times) {
            sum += time;
        }
        return sum / times.size();
    }

    public static double calculateStandardDeviation(List<Long> times, double mean) {
        double sum = 0.0;
        for (Long time : times) {
            sum += Math.pow(time - mean, 2);
        }
        double variance = sum / times.size();
        return Math.sqrt(variance);
    }

    public static void generateCSV(String fileName, List<Long> times, double mean, double stdDev) {
        try (FileWriter csvWriter = new FileWriter(fileName)) {
            csvWriter.append("Execução,Tempo (ms)\n");
            for (int i = 0; i < times.size(); i++) {
                csvWriter.append((i + 1) + "," + times.get(i) + "\n");
            }
            csvWriter.append("Média," + mean + "\n");
            csvWriter.append("Desvio Padrão," + stdDev + "\n");
            System.out.println("Arquivo CSV gerado com sucesso: " + fileName);
        } catch (IOException e) {
            System.out.println("Erro ao gerar o arquivo CSV: " + e.getMessage());
        }
    }
}
