package Ex2_2;

import java.util.concurrent.*;

import static Ex2_2.Task.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class CustomExecutor extends ThreadPoolExecutor {

    PriorityData priorityData =  new PriorityData();
    static int numCores = Runtime.getRuntime().availableProcessors();

    /**
     * Constructor to CustomExecutor.<br>
     * This constructor build a thread-pool that Set the number of threads to keep in the pool,<br>
     * even if they are idle to be half the number of processors available for the Java Virtual Machine (JVM).<br>
     * Set the maximum number of threads to allow in the pool to be on the number of processors
     * available for the Java Virtual Machine (JVM) minus 1.
     *
     */
    public CustomExecutor() {
        super(numCores / 2, numCores - 1, 300, MILLISECONDS,
                new PriorityBlockingQueue<>());
    }

    /**
     * The gracefullyTerminate allow the activity of a CustomExecutor instance to be terminated as follows:<br>
     * a. Do not allow the introduction of additional tasks to the queue.<br>
     * b. Completing all tasks remaining in the queue.<br>
     * c. Termination of all the tasks that are currently being executed in the collection of threads of the CustomExecutor.<br>
     */
    public void gracefullyTerminate() {
        try {
            super.awaitTermination(300 , MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        shutdown();
    }



    /**
     *
     * @param task the task to submit
     * @return a new Task.
     */
    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return submit(createTask(task));
    }

    /**
     *
     * @param task the task to generate.
     * @param taskType the type of the Priority.
     * @return a new task.
     */
    public <T> Future<T> submit(Callable<T> task, TaskType taskType) {
        if (taskType != null) {
            task = createTask(task, taskType);
        }
        return submit(task);
    }

    /**
     * Update the array about the number of task we have in each priority.
     * @param task the task to generate.
     * @return a {@code RunnableFuture} for the given callable task.
     */
    public <T> Future<T> submit(Task<T> task) {
        if (task == null){
            throw new NullPointerException();
        }

        int taskTypeValid = task.getTaskType().getPriorityValue();
        if (priorityData.validatePriority(taskTypeValid)) priorityData.arrayOfPriority[taskTypeValid-1]++;

        RunnableFuture<T> futureTask = newTaskFor(task);
        execute(futureTask);
        return futureTask;
    }

    /**
     * Method invoked prior to executing the given Runnable in the given thread.<br>
     * This method is invoked by thread t that will execute task r,<br>
     * and may be used to re-initialize ThreadLocals, or to perform logging.<br>
     * @param t the thread that will run task {@code r}
     * @param r the task that will be executed
     */
    protected void beforeExecute(Thread t, Runnable r) {
        int update = priorityData.getCurrentMax();
        priorityData.beforeExecuteUpdate(update);
    }

    /**
     * @return the number of the max priority in the array, each index represent a number of priority.<br>
     * Index 0 for COMPUTATIONAL, if is 0 continue.<br>
     * Index 1 for IO, if is 0 continue.<br>
     * Index 2 for OTHER, if is 0 continue.<br>
     * Repeat until the last index.<br>
     * Else return 0;
     */
    public int getCurrentMax() {
        return priorityData.getCurrentMax();
    }
}
