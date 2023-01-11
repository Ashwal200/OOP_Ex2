package Ex2_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class that inherits from the Thread class. The constructor gets a name
 * the file and in the run function the number of lines of this file must be calculated.
 * and calculates the number of lines in the same file.
 */
public class MyThread extends Thread {
    String fileName;
    int numOfLine;

    /**
     * @return the number of lines in the files.
     */
    int getNumOfLine() {
        return numOfLine;
    }

    /**
     * @param numOfLine count each file lines in add it all together.
     */
    private void setNumOfLine(int numOfLine) {
        this.numOfLine += numOfLine;
    }

    /**
     * Constructor to threads.
     *
     * @param name     the name of the thread.
     * @param fileName the name of the files.
     */
    public MyThread(String name, String fileName) {
        super(name);
        this.fileName = fileName;
        this.numOfLine = numOfLine;
    }

    /**
     * When the run() method calls, the code specified in the run() method is executed. We can call the run() method multiple times.<br>
     * This method will find the specific file count the number of line in it and add the number to {@code numOfLine}.
     */
    public void run() {
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            int counter = 0;
            while (br.readLine() != null) {
                counter++;
            }
            setNumOfLine(counter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(this.getName() + "  DONE!");
    }
}

