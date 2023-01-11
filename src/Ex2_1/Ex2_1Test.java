package Ex2_1;

import org.junit.jupiter.api.Test;

import static Ex2_1.Ex2_1.getNumOfLines;
import static org.junit.jupiter.api.Assertions.*;

class Ex2_1Test {
    Ex2_1 w = new Ex2_1();

    @Test
    void readFile() {
    }

    @Test
    void createTextFiles() {
    }

    @Test
    void getNumOfLines1() {
        int c = 0;
        String arr[] = w.createTextFiles(1 ,1 ,12);
        c = getNumOfLines(arr);
    }

    @Test
    void getNumOfLinesThreads() {
    }

    @Test
    void getNumOfLinesThreadPool() {
    }
}