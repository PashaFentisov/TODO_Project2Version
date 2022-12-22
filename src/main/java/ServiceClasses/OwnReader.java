package ServiceClasses;

import Interfaces.IOwnReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OwnReader implements IOwnReader {
    private BufferedReader reader;

    public OwnReader(){
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public String next(){
        String temp;
        try {
            temp = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return temp.split(" ")[0];
    }

    @Override
    public String nextLine(){
        String temp;
        try {
            temp = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return temp;
    }

    @Override
    public int nextInt(){
        return Integer.parseInt(next());
    }

    @Override
    public Double nextDouble(){
        return Double.parseDouble(next());
    }
}
