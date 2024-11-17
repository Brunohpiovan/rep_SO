import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

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

