public class ThreadSumApp {

    public static void main(String[] args) {
        // Definir o número de threads a serem usadas
        int numThreads = 4;

        // Definir o tamanho do vetor (deve ser múltiplo de numThreads)
        int arraySize = 20;

        // Criar e preencher o vetor com números
        int[] numbers = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            numbers[i] = i + 1; // Preencher com números de 1 a 20
        }

        // Calcular o número de elementos que cada thread vai processar
        int chunkSize = arraySize / numThreads;

        // Criar e iniciar as threads
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            // Calcular o índice inicial e final para o trabalho de cada thread
            int start = i * chunkSize;
            int end = (i + 1) * chunkSize;

            // Criar a thread e associá-la com a tarefa de soma
            threads[i] = new Thread(new ThreadSum(numbers, start, end, i));
            threads[i].start();
        }

        // Aguardar todas as threads terminarem
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Classe ThreadSum para realizar a soma de uma parte do vetor
class ThreadSum implements Runnable {
    private int[] numbers;
    private int start;
    private int end;
    private int threadId;

    // Construtor
    public ThreadSum(int[] numbers, int start, int end, int threadId) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
        this.threadId = threadId;
    }

    // Método run que será executado pela thread
    @Override
    public void run() {
        int sum = 0;

        // Imprimir a ID da thread e os elementos que ela vai somar
        System.out.println("Thread " + threadId + " começando a soma...");
        System.out.print("Thread " + threadId + " vai somar os seguintes elementos: ");
        for (int i = start; i < end; i++) {
            System.out.print(numbers[i] + " ");
            sum += numbers[i];
        }
        System.out.println();

        // Imprimir o resultado da soma da thread
        System.out.println("Thread " + threadId + " terminou com a soma parcial: " + sum);
    }
}
