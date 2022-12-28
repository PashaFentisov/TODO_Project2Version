package Classes;

import Interfaces.ITask;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


/**
 * Клас який містить всю інформацію про кожен таск
 */
public class Task implements ITask, Comparable<Task> {
    static private final DateTimeFormatter formatForDateOfMade = DateTimeFormatter.ofPattern("d MMMM yyyy");
    static private final DateTimeFormatter formatForExpiryDate = DateTimeFormatter.ofPattern("d MMMM");
    private String text;
    private boolean isDone;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate doBefore = LocalDate.now();
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate doneDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate createDate;
    private String isOnTime;
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

    public String getOnTime() {
        return isOnTime;
    }

    public void setOnTime(String onTime) {
        this.isOnTime = onTime;
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

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    /**
     * Метод для строкового відображення таска.
     * Для не виконаних тасків задається temp - час скільки залишолось часу до дедлайна.
     * Якщо дедлайн прострочено то temp = "Час сплинув".
     * Якщо поле isDone для task позначено як true, то temp присвоюється приставка "DONE",
     * час коли таск було виконано і "Вчасно" або "З запізненням".
     *
     * @return Строковий опис таска
     */
    @Override
    public String toString() {
        String temp;
        Period p = Period.between(LocalDate.now(), doBefore);
        try {
            if (p.getMonths() < 0 || p.getDays() < 0) {
                throw new Exception();
            }
            temp = String.format("%-3s month %-4s days", p.getMonths(), p.getDays());
        } catch (Exception e) {
            temp = "Час сплинув";
        }
        temp = (isDone) ? String.format("%-12s %-20s %-15s", "DONE", doneDate.format(Task.getFormatForDateOfMade()), isOnTime) : temp;
        if (isDone) {
            return String.format("%-4s %-2d %-2s %-50s %-10s %-25s Виконати до: %-15s %s","Task",  number, ":", text, "задано:",
                    createDate.format(formatForDateOfMade),
                    doBefore.format(formatForExpiryDate), temp);
        } else {
            return String.format("%-4s %-2d %-2s %-50s %-10s %-25s Виконати до: %-15s залишилось : %-6s ", "Task", number,":", text, "задано:",
                    createDate.format(formatForDateOfMade),
                    doBefore.format(formatForExpiryDate), temp);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return isDone == task.isDone && number == task.number && Objects.equals(text, task.text) && Objects.equals(doBefore, task.doBefore) && Objects.equals(doneDate, task.doneDate) && Objects.equals(createDate, task.createDate) && Objects.equals(isOnTime, task.isOnTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, isDone, doBefore, doneDate, createDate, isOnTime, number);
    }

    @Override
    public int compareTo(Task o) {
        int temp = this.text.compareTo(o.text);
        if (temp == 0) {
            temp = this.number - o.number;
        }
        return temp;
    }
}