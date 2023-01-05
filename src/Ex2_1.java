import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.*;



public class Ex2_1 {
    // help class!!!!
    public static class MyCustomFormatter extends Formatter {
        @Override
        public String format(LogRecord message) {
            StringBuffer sb = new StringBuffer();
            sb.append(message.getLevel());
            sb.append(": ");
            sb.append(message.getMessage());
            return sb.toString();
        }
    }
    // help function!
    public static int readFile (String fileName){
        int numOfLine = 0;
        //		 try read from the file
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String str;
            str = br.readLine();
            numOfLine++;
            for(int i=1; str!=null; i=i+1) {
                str = br.readLine();
                if (str != null){
                    numOfLine++;
                }
            }
            br.close();
            fr.close();
        }
        catch(IOException ex) {
            System.err.print("Error reading file\n" + ex);
            ex.printStackTrace();
            System.exit(2);
        }
        return numOfLine;
    }

    /**
     * QUESTIONS 1
     * n - a natural number representing the number of text files.
     * The number of lines in each file is a random number, obtained with the help of a usage of Random class <br>
     * And in the seed and bound parameters.
     * Output: The function creates n text files on disk and returns an array
     * of the file names. In each line you can write a sentence like "World Hello"
     * containing at least 10 characters.
     * @param n
     * @param seed
     * @param bound
     * @return
     */
    public static String[] createTextFiles(int n, int seed, int bound) {
        String fileString[] = new String[n];
        Random rand = new Random(seed);
        for (int i = 1; i <= n; i++) {
            fileString[i-1] = "file_"+i;
            Logger logger = Logger.getLogger("MyLog");
            FileHandler fh;

            try {
                // This block configure the logger with handler and formatter
                fh = new FileHandler("C:\\Users\\ashwa\\Desktop\\Ex2\\file_" + i);
                logger.addHandler(fh);
                SimpleFormatter formatter = new SimpleFormatter();
                fh.setFormatter(formatter);
                fh.setFormatter(new MyCustomFormatter());
                logger.setUseParentHandlers(false);

                // the following statement is used to log any messages

                int randomNumber = rand.nextInt(bound);
                for (int j = 0; j < randomNumber; j++)
                {
                    logger.info("DONT WANNA CLOSE MY EYES I DONT WANNA FALL ASLEEP!\n");

                }
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
     * A function must be written
     * public static int getNumOfLines(String[] fileNames){…}
     * Input: an array that contains the file names.
     * Output: the total number of lines of the files.
     *
     * @param fileNames
     * @return
     */
    public static int getNumOfLines(String[] fileNames){
        int numberOfLIne = 0;
        for (String file : fileNames) {
            int tempNumberOfLIne = readFile(file);
            numberOfLIne += tempNumberOfLIne;
        }
        return numberOfLIne;
    }


    //#################################################
    /**
     * QUESTIONS 3
     * @param fileNames
     * @return
     */
    public static int getNumOfLinesThreads(String[] fileNames) throws InterruptedException {
        MyThread threadList[] = new MyThread[fileNames.length];
        int numberOfLIne =0;

        for (int i = 0 ; i < fileNames.length ; i++) {
            threadList[i] = new MyThread( "Thread_"+i ,fileNames[i]);
            threadList[i].start();
        }
        for (int i = 0 ; i < fileNames.length ; i++) {
            threadList[i].join();
            numberOfLIne += threadList[i].getNumOfLine();

        }

        return numberOfLIne;
        }

    /**
     * QUESTIONS 4
     * @param fileNames
     * @return
     */
    public static int getNumOfLinesThreadPool(String[]fileNames){
        MyThreadPool mtp = new MyThreadPool();
        mtp.runThreadPool(fileNames.length , fileNames);
        return 0;}



    public static void main (String[]args) throws InterruptedException {

        String st[] = createTextFiles(7,2,600);
        System.out.println("Q_1 :");
        System.out.println(Arrays.toString(st));

        System.out.println("Q_2 :");
        Instant start = Instant.now();
        System.out.println(getNumOfLines(st));
        Instant end = Instant.now();
        Duration elapsed = Duration.between(start, end);
        System.out.println("Elapsed time: " + elapsed.toMillis() + " milliseconds");


        System.out.println("Q_3 Thread :");
        Instant start_Thread = Instant.now();
        System.out.println(getNumOfLinesThreads(st));
        Instant end_Thread = Instant.now();
        Duration elapsed_Thread = Duration.between(start_Thread, end_Thread);
        System.out.println("Elapsed time Thread: " + elapsed_Thread.toMillis() + " milliseconds");
//
        System.out.println("Q_4 Thread Pool:");
        Instant start_ThreadPool = Instant.now();
        System.out.println(getNumOfLinesThreads(st));
        Instant end_ThreadPool = Instant.now();
        Duration elapsed_ThreadPool = Duration.between(start_ThreadPool, end_ThreadPool);
        System.out.println("Elapsed time ThreadPool: " + elapsed_ThreadPool.toMillis() + " milliseconds");
        getNumOfLinesThreadPool(st);

    }

}








