package ServiceClasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OwnReader {
    private BufferedReader reader;

    public OwnReader(){
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public String next(){
        String temp;
        try {
            temp = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return temp.split(" ")[0];
    }

    public String nextLine(){
        String temp;
        try {
            temp = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return temp;
    }

    public int nextInt(){
        return Integer.parseInt(next());
    }

    public Double nextDouble(){
        return Double.parseDouble(next());
    }
}
