import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ExecutionTimes {
    public static void main(String[] args) throws IOException {
        final int N = 100_000_000;
        int[] vetor = ThreadLocalRandom.current().ints(N, 1, 10).toArray();
        List<Long> sequentialTimes = new ArrayList<>();
        List<Long> parallelTimes = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            // Executar soma sequencial e medir o tempo
            long startTime = System.nanoTime();
            long sum = 0;
            for (int value : vetor) {
                sum += value;
            }
            long endTime = System.nanoTime();
            sequentialTimes.add((endTime - startTime) / 1_000_000);

            // Executar soma paralela e medir o tempo
            startTime = System.nanoTime();
            sum = ParallelSum.parallelSum(vetor);
            endTime = System.nanoTime();
            parallelTimes.add((endTime - startTime) / 1_000_000);
        }

        // Exportar os tempos para CSV
        exportToCSV("sequential_times.csv", sequentialTimes);
        exportToCSV("parallel_times.csv", parallelTimes);

        // Adicione os tempos de execução às listas... double sequentialMean = calculateMean(sequentialTimes); double sequentialStdDev = calculateStandardDeviation(sequentialTimes, sequentialMean); double parallelMean = calculateMean(parallelTimes); double parallelStdDev = calculateStandardDeviation(parallelTimes, parallelMean); System.out.println("Média dos tempos de execução (Sequencial): " + sequentialMean + " ms"); System.out.println("Desvio padrão dos tempos de execução (Sequencial): " + sequentialStdDev + " ms"); System.out.println("Média dos tempos de execução (Paralelo): " + parallelMean + " ms"); System.out.println("Desvio padrão dos tempos de execução (Paralelo): " + parallelStdDev + " ms");


        // Cálculo da média e desvio padrão
        calculateStatistics("Sequencial", sequentialTimes);
        calculateStatistics("Paralelo", parallelTimes);
    }

    public static void exportToCSV(String fileName, List<Long> times) throws IOException {
        FileWriter csvWriter = new FileWriter(fileName);
        csvWriter.append("Execução,Tempo\n");
        for (int i = 0; i < times.size(); i++) {
            csvWriter.append(i + 1 + "," + times.get(i) + "\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }

    public static void calculateStatistics(String label, List<Long> times) {
        double mean = times.stream().mapToLong(Long::longValue).average().orElse(0);
        double variance = times.stream().mapToDouble(time -> Math.pow(time - mean, 2)).sum() / times.size();
        double stdDeviation = Math.sqrt(variance);

        System.out.println("Média dos tempos de execução (" + label + "): " + mean + " ms");
        System.out.println("Desvio padrão dos tempos de execução (" + label + "): " + stdDeviation + " ms");
    }
    public static double calculateMean(List<Long> times) {
        double sum = 0.0; for (Long time : times) { sum += time; } return sum / times.size();
    }
    public static double calculateStandardDeviation(List<Long> times, double mean) {
        double sum = 0.0; for (Long time : times) { sum += Math.pow(time - mean, 2);
        }
        double variance = sum / times.size(); return Math.sqrt(variance);
    }
}
