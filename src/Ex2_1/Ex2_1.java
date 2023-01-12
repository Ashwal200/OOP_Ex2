package Ex2_1;

import java.io.*;
import java.util.Random;
import java.util.concurrent.*;
import java.util.logging.*;



public class Ex2_1 {

    /**
     * Count the number of lines.
     * @param fileName the file that we are working on.
     * @return the total lines in the file.
     */
    public static int readFile (String fileName){
        int numOfLine = 0;
        //		 try read from the file
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            while (br.readLine() != null) {
                numOfLine++;
            }
            br.close();
            fr.close();
        }
        catch(FileNotFoundException ex) {

            System.err.print("Error reading file\n" + ex);
            ex.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return numOfLine;
    }

    /**
     * QUESTIONS 1
     * n - a natural number representing the number of text files.
     * The number of lines in each file is a random number, obtained with the help of a usage of Random class <br>
     * And in the seed and bound parameters.<br>
     * Output: The function creates n text files on disk and returns an array of the file names.<br>
     * If file exist it Overwrite the file and build new one.
     * @param n number of files to create.
     * @param seed The seed is the initial value of the internal state of the pseudorandom number generator.
     * @param bound define the range so that numbers can be greater than or equal to zero and less than the bound value.
     * @return array that contains the name of the file that we create.
     */
    public static String[] createTextFiles(int n, int seed, int bound) {
        if (bound <= 0) throw new IllegalArgumentException("Enter a positive number!");
        if (n < 0) throw new NegativeArraySizeException("Enter a positive number!");
        String fileString[] = new String[n];
        Random rand = new Random(seed);
        for (int i = 1; i <= n; i++) {


            try {

                BufferedWriter writer = new BufferedWriter(new FileWriter("file_" + i));
                fileString[i-1] = "file_"+i;
                int randomNumber = rand.nextInt(bound);
                for (int j = 0; j < randomNumber; j++)
                {
                    writer.write("DONT WANNA CLOSE MY EYES I DONT WANNA FALL ASLEEP!\n");
                }
                writer.close();
            }
            catch (SecurityException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
        return fileString;
    }

    /**
     * QUESTIONS 2
     * This function get an array that contains the file names.
     * And count the total number of lines of the files.
     * @param fileNames array that contains the name of the file that we create.
     * @return numbers of the entire lines in all the files together.
     */
    public static int getNumOfLines(String[] fileNames){
        long start = System.currentTimeMillis();
        int numberOfLIne = 0;
        for (String file : fileNames) {
            int tempNumberOfLIne = readFile(file);
            numberOfLIne += tempNumberOfLIne;
        }
        long end = System.currentTimeMillis();
        long elapsed = end - start;
        System.out.println("Elapsed time: " + elapsed/1000.0 + " milliseconds");
        return numberOfLIne;

    }



    /**
     * QUESTIONS 3
     * @param fileNames array that contains the name of the file that we create.
     * @return numbers of the entire lines in all the files together.
     *
     */
    public int getNumOfLinesThreads(String[] fileNames)  {
        long start_Thread = System.currentTimeMillis();
        MyThread threadList[] = new MyThread[fileNames.length];
        int numberOfLIne =0;

        for (int i = 0 ; i < fileNames.length ; i++) {
            threadList[i] = new MyThread( "Thread_"+i ,fileNames[i]);
            threadList[i].start();
        }
        for (int i = 0 ; i < fileNames.length ; i++) {
            try {
                threadList[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            numberOfLIne += threadList[i].getNumOfLine();
        }
        long end_Thread = System.currentTimeMillis();
        long elapsed_Thread = end_Thread - start_Thread;
        System.out.println("Elapsed time Thread: " + elapsed_Thread/1000.0 + " milliseconds");
        return numberOfLIne;
        }

    /**
     * QUESTIONS 4
     * @param fileNames array that contains the name of the file that we create.
     * @return numbers of the entire lines in all the files together.
     *
     */
    public  int getNumOfLinesThreadPool(String[]fileNames){
            long startTime = System.currentTimeMillis();
            int totalLines = 0;
            ExecutorService pool = Executors.newFixedThreadPool(fileNames.length);
            Future<Integer> [] futures = new Future[fileNames.length];
            int index = 0;
            for (String filename: fileNames) {
                Callable<Integer> counter = new MyThreadPool(filename);
                Future<Integer> future = pool.submit(counter);
                futures[index++] = (future);
            }
            for (Future<Integer> future : futures) {
                try {
                    totalLines += future.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            pool.shutdown();
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            System.out.println("Elapsed time: " + elapsedTime/1000.0 + " milliseconds");
            return totalLines;

    }




}








