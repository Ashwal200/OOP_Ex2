package Ex2_1;

import java.util.concurrent.*;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;

public class MyThreadPool implements Callable<Integer> {
        private String fileName;
        /**
        * Constructor for thread pool.
        * Use Callable object for treading and return value.
        * @param fileName hold the file name to run the method call for.
        */
        public MyThreadPool(String fileName){
            this.fileName = fileName;
        }

       /**
        * Represents a task that can be executed concurrently and can return a value of type Integer.
        * The methode may throw an exception if unable to do so.
        * the method will count how many lines is in the file in the path {@code fileName}.
        * @return the computed result --> the num of lines in the file.
        */
        @Override
        public Integer call() {
            int lineCount = 0;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                while (reader.readLine() != null) {
                    lineCount++;
                }
                reader.close();
                System.out.println("Number of lines: " + lineCount);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return lineCount;
        }
    }



