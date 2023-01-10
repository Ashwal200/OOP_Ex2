package Ex2_1;

import java.util.Arrays;

public class main {
    static Ex2_1 ex = new Ex2_1();
    static String st[] = ex.createTextFiles(7,2,600);
    public static void main (String[]args) throws InterruptedException {


        System.out.println("Q_1 :");
        System.out.println(Arrays.toString(st));

        System.out.println("Q_2 :");
        System.out.println(ex.getNumOfLines(st));

        System.out.println("Q_3 Thread :");
        System.out.println(ex.getNumOfLinesThreads(st));

        System.out.println("Q_4 Thread Pool:");
        System.out.println(ex.getNumOfLinesThreads(st));

    }
}
