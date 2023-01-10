package Ex2_1;

import java.util.concurrent.*;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;

public class MyThreadPool implements Callable<Integer> {
        private String fileName;

        public MyThreadPool(String fileName){
            this.fileName = fileName;
        }

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



