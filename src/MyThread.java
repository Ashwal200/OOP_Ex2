import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MyThread extends Thread{
    String fileName;
    int numOfLine;

    int getNumOfLine() {
        return numOfLine;
    }

    private void setNumOfLine(int numOfLine) {
        this.numOfLine += numOfLine;
    }

    public MyThread(String name , String fileName){
        super(name);
        this.fileName= fileName;
        this.numOfLine = numOfLine;
    }
    public void run() {
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String str;
            str = br.readLine();
            int counter = 1;
            for (int i = 1; str != null; i = i + 1) {
                str = br.readLine();
                if (str != null) {
                    counter++;
                }
            }
            setNumOfLine(counter);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(this.getName() + "  DONE!");
    }
}

