import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class SequentialSum {
    public static void main(String[] args) {
        final int N = 100_000_000;
        int[] vetor = ThreadLocalRandom.current().ints(N, 1, 10).toArray();

        long startTime = System.nanoTime();

        long sum = 0;
        for (int value : vetor) {
            sum += value;
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;

        System.out.println("Soma Sequencial: " + sum);
        System.out.println("Tempo de Execução: " + duration + " ms");
    }
}
