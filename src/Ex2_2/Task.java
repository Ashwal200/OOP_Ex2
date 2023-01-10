package Ex2_2;


import java.util.concurrent.Callable;

import static Ex2_2.TaskType.OTHER;

public class Task<T> implements Comparable
        <Task<T>> , Callable<T> {
    private static final TaskType defaultType = OTHER;
    public TaskType taskType;
    public Callable<T> callable;
    public Task(Callable<T> taskCallable, TaskType taskType) {
        this.callable = taskCallable;
        this.taskType = taskType;
    }

    @Override
    public int hashCode() {
        return callable.hashCode() * taskType.hashCode();
    }


    public static <T> Task createTask(Callable<T> callable){

        return new Task(callable , defaultType);
    }
    public static <T> Task createTask(Callable<T> taskCallable, TaskType taskType ){
        return new Task(taskCallable , taskType);
    }



    /**
     * 1. 0 == diff: This compares the value of diff to 0 using the equality operator ==. If diff is equal to 0, this expression returns true. Otherwise, it returns false.<br>
     * 2. 0: This is the value returned if the boolean expression in the first step is true.<br>
     * 3. 0 > diff: This compares the value of diff to 0 using the greater than operator >.<br>
     * If diff is less than 0, this expression returns true. Otherwise, it returns false.<br>
     * 4. -1: This is the value returned if the boolean expression in the third step is true.<br>
     * 5. 1: This is the value returned if the boolean expression in the third step is false.<br>
     * @param otherTask the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Task<T> otherTask) {
        int diff = otherTask.taskType.getPriorityValue() - taskType.getPriorityValue();
        // If true return 0, if this* is more important return -1, else return 1
        return 0 == diff ? 0 : 0 > diff ? -1 : 1;
    }


    @Override
    public T call() {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}