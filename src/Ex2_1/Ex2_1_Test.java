package Ex2_1;


import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;


import static Ex2_1.Ex2_1.getNumOfLines;
import static org.junit.Assert.assertThrows;

public class Ex2_1_Test {

    Ex2_1 ex = new Ex2_1();
    String [] a = ex.createTextFiles(20, -8, 10);
    String st[] = ex.createTextFiles(3000,2,100);
    String arr[] ={"file_20001"};




    @Test
    void createTextFiles() {

        Throwable exception = assertThrows(NegativeArraySizeException.class,
                ()->{Ex2_1.createTextFiles(-1, 8, 100);} );// Invalid num of files.
        Throwable exception1 = assertThrows(IllegalArgumentException.class,
                ()->{Ex2_1.createTextFiles(12, 8, -100);} );//Invalid max num of line(smaller than 0).

    }

    @Test
    void getNumOfLinesTest() {
        System.out.println("Q_2 Thread:");
        System.out.println(ex.getNumOfLines(st));

        //Try to count the number of lines from unexisted file.

        getNumOfLines(arr);

    }

    @Test
    void getNumOfLinesThreads() throws InterruptedException {

        System.out.println("Q_3 Thread :");
        System.out.println(ex.getNumOfLinesThreads(st));

        //Try to count the number of lines from unexisted file.
        ex.getNumOfLinesThreads(arr);
    }

    @Test
    void getNumOfLinesThreadPool() {

        System.out.println("Q_4 Thread Pool:");
        System.out.println(ex.getNumOfLinesThreads(st));

        //Try to count the number of lines from unexisted file.
        ex.getNumOfLinesThreadPool(arr);

    }
}
