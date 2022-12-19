package Classes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task implements Serializable{
    static private DateTimeFormatter formatForDateOfMade = DateTimeFormatter.ofPattern("d MMMM yyyy");
    static private DateTimeFormatter formatForExpiryDate = DateTimeFormatter.ofPattern("d MMMM");
    private String text;
    private boolean isDone;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate doBefore = LocalDate.now();
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate doneDate;
    private boolean isOnTime;
    private int number;
    private static int countOfTasks = 0;

    public Task(String text) {
        this.text = text;
        this.number = ++countOfTasks;
    }

    public static DateTimeFormatter getFormatForDateOfMade() {
        return formatForDateOfMade;
    }

    public static DateTimeFormatter getFormatForExpiryDate() {
        return formatForExpiryDate;
    }

    public Task() {
        this("null");
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public LocalDate getDoBefore() {
        return doBefore;
    }

    public void setDoBefore(LocalDate doBefore) {
        this.doBefore = doBefore;
    }

    public LocalDate getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(LocalDate doneDate) {
        this.doneDate = doneDate;
    }

    public boolean isOnTime() {
        return isOnTime;
    }

    public void setOnTime(boolean onTime) {
        isOnTime = onTime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public static int getCountOfTasks() {
        return countOfTasks;
    }

    public static void setCountOfTasks(int countOfTasks) {
        Task.countOfTasks = countOfTasks;
    }

    @Override
    public String toString() {
        return String.format("Task %d: %-50s задано: %-20s Виконати до: %s", number, text,
                LocalDate.now().format(formatForDateOfMade),
                doBefore.format(formatForExpiryDate));
    }
}