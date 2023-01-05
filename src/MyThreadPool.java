import java.util.concurrent.*;

public class MyThreadPool {
    public void runThreadPool(int threadsNumber , String[] fileNames) {
        // Create a thread pool with 4 threads
        ExecutorService executor = Executors.newFixedThreadPool(threadsNumber);

        // Define a task as a Callable object
        Callable<Integer> task = new Callable<Integer>() {
            public Integer call() {
                // Perform some computation
                int result = Ex2_1.getNumOfLines(fileNames);
                return result;
            }
        };

        // Submit the task to the thread pool
        Future<Integer> future = executor.submit(task);

        // Wait for the task to complete
        try {
            int result = future.get();
            System.out.println("Result: " + result);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }

        // Shut down the thread pool
        executor.shutdown();
    }


}
