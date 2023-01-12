package Ex2_2;


import java.util.concurrent.Callable;

import static Ex2_2.TaskType.OTHER;

public class Task<T> implements Comparable
        <Task<T>> , Callable<T> {
    private static final TaskType defaultType = OTHER;
    private TaskType taskType;
    private Callable<T> callable;

    /**
     * Constructor for the Task object.
     * @param taskCallable hold Callable object for treading and return value.
     * @param taskType hold taskType for sorting the task in the CustomExecutor Queue.
     */
    public Task(Callable<T> taskCallable, TaskType taskType) {
        this.callable = taskCallable;
        this.taskType = taskType;
    }
    /**
     * This is a factory method.
     * @param callable get a callable task to do.
     * @param <T> get a generic object to return.
     * @return new task object.
     */
    public static <T> Task createTask(Callable<T> callable){

        return new Task(callable , defaultType);
    }

    /**
     * This is a factory method.
     * @param taskCallable callable get a callable task to do.
     * @param taskType get the priority of the new task.
     * @param <T> <T> get a generic object to return.
     * @return new task object.
     */
    public static <T> Task createTask(Callable<T> taskCallable, TaskType taskType ){
        return new Task(taskCallable , taskType);
    }



    /**
     * @param otherTask the object to be compared.
     * @return
     * 1. 0 == diff: This compares the value of diff to 0 using the equality operator ==. <br>If diff is equal to 0, this expression returns true. Otherwise, it returns false.<br>
     *       2. 0: This is the value returned if the boolean expression in the first step is true.<br>
     *       3. 0 > diff: This compares the value of diff to 0 using the greater than operator >.<br>
     *       If diff is less than 0, this expression returns true. Otherwise, it returns false.<br>
     *       4. -1: This is the value returned if the boolean expression in the third step is true.<br>
     *       5. 1: This is the value returned if the boolean expression in the third step is false.<br>
     */
    @Override
    public int compareTo(Task<T> otherTask) {
        int diff = otherTask.taskType.getPriorityValue() - taskType.getPriorityValue();
        // If true return 0, if this* is more important return 1, else return -1
        return 0 == diff ? 0 : 0 > diff ? 1 : -1;
    }

    /**
     *
     * This a method of lang.Object class, and it is used to compare two objects.<br>
     * To compare two objects that whether they are the same, it compares the values of both the object's attributes.
     * @param o other object to check.
     * @return True or False
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task<?> task)) return false;
        return taskType == task.taskType && callable.equals(task.callable);
    }
    /**
     * Represents a task that can be executed concurrently and can return a value of type 'T'.
     * The methode may throw an exception if unable to do so.
     * @return computed result.
     */
    @Override
    public T call() {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
}