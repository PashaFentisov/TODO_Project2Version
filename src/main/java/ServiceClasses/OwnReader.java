package ServiceClasses;

import Interfaces.IOwnReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Службовий клас для зчитування данних з консолі.
 */
public class OwnReader implements IOwnReader {
    private BufferedReader reader;

    public OwnReader() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Метод зчитує значення методом {@link BufferedReader#readLine()},
     * і повертає значення яке було введено до першого пробілу.
     * @return Зчитане значення до першого пробілу
     */
    @Override
    public String next() {
        String temp;
        try {
            temp = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return temp.split(" ")[0];
    }
    /**
     * Метод зчитує рядок методом {@link BufferedReader#readLine()} і повертає значення.
     * @return зчитаний рядок.
     */
    @Override
    public String nextLine() {
        String temp;
        try {
            temp = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return temp;
    }
    /**
     * Метод зчитує значення методом {@link OwnReader#next()}, і приводить зчитане значення до типу Integer.
     * @return Зчитане int значення
     */
    @Override
    public int nextInt() {
        return Integer.parseInt(next());
    }
    /**
     * Метод зчитує значення методом {@link OwnReader#next()}, і приводить зчитане значення до типу Double.
     * @return Зчитане double значення
     */
    @Override
    public Double nextDouble() {
        return Double.parseDouble(next());
    }
}
