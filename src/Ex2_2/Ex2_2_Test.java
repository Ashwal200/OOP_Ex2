package Ex2_2;


import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.PriorityQueue;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class Ex2_2_Test {
    public static final Logger logger = LoggerFactory.getLogger(Ex2_2_Test.class);
    @Test
    public void partialTest(){
        CustomExecutor customExecutor = new CustomExecutor();
        var task = Task.createTask(()->{
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            }
            return sum;
        }, TaskType.COMPUTATIONAL);
        var sumTask = customExecutor.submit(task);
        final int sum;
        try {
            sum = (int) sumTask.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        logger.info(()-> "Sum of 1 through 10 = " + sum);
        Callable<Double> callable1 = ()-> {
            return 1000 * Math.pow(1.02, 5);
        };
        Callable<String> callable2 = ()-> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            return sb.reverse().toString();
        };
        // var is used to infer the declared type automatically
        var priceTask = customExecutor.submit(()-> {
            return 1000 * Math.pow(1.02, 5);
        }, TaskType.COMPUTATIONAL);
        var reverseTask = customExecutor.submit(callable2, TaskType.IO);
        final Double totalPrice;
        final String reversed;
        try {
            totalPrice = priceTask.get();
            reversed = reverseTask.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        logger.info(()-> "Reversed String = " + reversed);
        logger.info(()->String.valueOf("Total Price = " + totalPrice));
        logger.info(()-> "Current maximum priority = " +
                customExecutor.getCurrentMax());
        customExecutor.gracefullyTerminate();
    }

    @Test
    public void CustomExecutor(){
        CustomExecutor ce = new CustomExecutor();

        ce.submit(()->{
            int a = 208647701;
            int b = 208573139;
            return a+b;
        });

        ce.submit(()->{
                    String a = "Aviya";
                    String b = " & Ron";
                    return a+b;}
                , TaskType.OTHER);

    }

    @Test
    public void Task() throws ExecutionException, InterruptedException {

        CustomExecutor ce = new CustomExecutor();

        Task task_1 = Task.createTask(()->{//Make an invalid task (divided by zero).
            return 6/0;
        });
        var exception  = ce.submit(task_1); //The CustomExecutor get invalid task and catch the exception.
        // Use the Factory method with a TaskType.
        Task task_2 = Task.createTask(()->{
                    String a = "Aviya";
                    String b = " & Ron";
                    return a+b;}
                , TaskType.OTHER);

        var string_1  = ce.submit(task_2);
        assertEquals("Aviya & Ron", string_1.get());


        // Use the Factory method without a TaskType.
        Task task_3 = Task.createTask(()->{
            String a = "Aviya";
            return a;});
        assertEquals(TaskType.OTHER, task_3.getTaskType());//Check if that the default is OTHER = 3.
        assertEquals(task_2.getTaskType(), task_3.getTaskType());

        //Use the Factory method with a TaskType.
        Task task_4 = Task.createTask(()->{
            String a = "Aviya";
            return a;}, TaskType.COMPUTATIONAL);
        //Check that the default task type is set correctly and the compare method
        assertEquals(-1, task_4.compareTo(task_3));
    }

    // Check if the queue working as excepted.
    @Test
    public void queueOrder()  {
        PriorityQueue<Task> queue = new PriorityQueue<Task>();
        Task<Integer> task1 = Task.createTask(() -> {
            Thread.sleep(1000);
            return 1;
        }, TaskType.COMPUTATIONAL);
        Task<Integer> task2 = Task.createTask(() -> {
            Thread.sleep(1000);
            return 1;
        }, TaskType.IO);
        Task<Integer> task3 = Task.createTask(() -> {
            Thread.sleep(1000);
            return 1;
        }, TaskType.OTHER);
        queue.add(task2);
        queue.add(task1);
        queue.add(task3);
        assertEquals(1 , queue.poll().getTaskType().getPriorityValue());
        assertEquals(2 , queue.poll().getTaskType().getPriorityValue());
        assertEquals(3, queue.poll().getTaskType().getPriorityValue());

    }





}
