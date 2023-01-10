package Ex2_2;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.*;

import static Ex2_2.Task.*;
import static Ex2_2.TaskType.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class CustomExecutor extends ThreadPoolExecutor {

    private int arrayOfPriority[]= {0,0,0};
    static int numCores = Runtime.getRuntime().availableProcessors();
    private final TaskType defaultType = OTHER;

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
     * Method invoked prior to executing the given Runnable in the given thread.<br>
     * This method is invoked by thread t that will execute task r,<br>
     * and may be used to re-initialize ThreadLocals, or to perform logging.<br>
     * @param t the thread that will run task {@code r}
     * @param r the task that will be executed
     */
    protected void beforeExecute(Thread t, Runnable r) {
        int updateCheck = getCurrentMax();
        arrayOfPriority[updateCheck-1]--;
    }

    /**
     *
     * @param task the task to submit
     * @return a new Task.
     */
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
        if (task.taskType == COMPUTATIONAL) arrayOfPriority[0]++;
        if (task.taskType == IO) arrayOfPriority[1]++;
        if (task.taskType == OTHER) arrayOfPriority[2]++;

        if (task == null){
            throw new NullPointerException();
        }
        RunnableFuture<T> futureTask = newTaskFor(task);
        execute(futureTask);
        return futureTask;
    }

    /**
     *
     * @return the number of the max priority in the array, each index represent a number of priority.<br>
     * Index 0 for COMPUTATIONAL, if is 0 continue.<br>
     * Index 1 for IO, if is 0 continue.<br>
     * Index 2 for OTHER, if is 0 continue.<br>
     * Else return 0;
     */
    public int getCurrentMax(){
        return arrayOfPriority[0] != 0 ? 1 : arrayOfPriority[1] != 0 ? 2 : arrayOfPriority[2] != 0 ? 3 : 0;
    }

    /**
     * This a method of lang.Object class, and it is used to compare two objects.<br>
     * To compare two objects that whether they are the same, it compares the values of both the object's attributes.
     * @param o other object to check.
     * @return True or False
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomExecutor that)) return false;
        return Arrays.equals(arrayOfPriority, that.arrayOfPriority) && defaultType == that.defaultType;
    }

    /**
     * The hashcode() method returns the same hash value when called on two objects, which are equal according to the equals() method.<br>
     * And if the objects are unequal, it usually returns different hash values.
     * @return result value.
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(defaultType);
        result = 31 * result + Arrays.hashCode(arrayOfPriority);
        return result;
    }
}
