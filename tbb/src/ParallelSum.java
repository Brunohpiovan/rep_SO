import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class ParallelSum extends RecursiveTask<Long> {
    private static final int SEQUENTIAL_THRESHOLD = 50000;  // Limiar para processamento sequencial
    private final int[] array;  // Array de inteiros a ser somado
    private final int low;  // Índice inicial
    private final int high;  // Índice final

    public ParallelSum(int[] array, int low, int high) {
        this.array = array;
        this.low = low;
        this.high = high;
    }

    @Override
    protected Long compute() {
        if (high - low <= SEQUENTIAL_THRESHOLD) {  // Se a tarefa for pequena o suficiente, processar sequencialmente
            long sum = 0;
            for (int i = low; i < high; i++) {
                sum += array[i];
            }
            return sum;
        } else {  // Caso contrário, dividir a tarefa
            int mid = low + (high - low) / 2;
            ParallelSum left = new ParallelSum(array, low, mid);
            ParallelSum right = new ParallelSum(array, mid, high);
            left.fork();  // Processar a subtarefa à esquerda em paralelo
            long rightResult = right.compute();  // Processar a subtarefa à direita
            long leftResult = left.join();  // Esperar pela conclusão da subtarefa à esquerda
            return leftResult + rightResult;  // Combinar os resultados
        }
    }

    public static long parallelSum(int[] array) {
        ForkJoinPool pool = new ForkJoinPool();  // Criar um pool de threads
        return pool.invoke(new ParallelSum(array, 0, array.length));  // Invocar a tarefa paralela
    }
}
