import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

class ParallelSum extends RecursiveTask<Long> {
    private static final int SEQUENTIAL_THRESHOLD = 50000;
    private final int[] array;
    private final int low;
    private final int high;

    public ParallelSum(int[] array, int low, int high) {
        this.array = array;
        this.low = low;
        this.high = high;
    }

    @Override
    protected Long compute() {
        if (high - low <= SEQUENTIAL_THRESHOLD) {
            long sum = 0;
            for (int i = low; i < high; i++) {
                sum += array[i];
            }
            return sum;
        } else {
            int mid = low + (high - low) / 2;
            ParallelSum left = new ParallelSum(array, low, mid);
            ParallelSum right = new ParallelSum(array, mid, high);
            left.fork();
            long rightResult = right.compute();
            long leftResult = left.join();
            return leftResult + rightResult;
        }
    }

    public static long parallelSum(int[] array) {
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(new ParallelSum(array, 0, array.length));
    }
}

public class ParallelSumMain {
    public static void main(String[] args) {
        final int N = 100_000_000;
        int[] vetor = ThreadLocalRandom.current().ints(N, 1, 10).toArray();

        long startTime = System.nanoTime();

        long sum = ParallelSum.parallelSum(vetor);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;

        System.out.println("Soma Paralela: " + sum);
        System.out.println("Tempo de Execução: " + duration + " ms");
    }
}
