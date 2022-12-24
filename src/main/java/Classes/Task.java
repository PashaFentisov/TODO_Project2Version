package Classes;

import Interfaces.ITask;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;


/**
 * Клас який містить всю інформацію про кожен таск
 */
public class Task implements ITask {
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
            temp = String.format("%s month %s days", p.getMonths(), p.getDays());
        } catch (Exception e) {
            temp = "Час сплинув";
        }
        temp = (isDone) ? String.format("%-12s %s %s", "DONE", doneDate.format(Task.getFormatForDateOfMade()), isOnTime) : temp;
        if (isOnTime.equalsIgnoreCase("Вчасно")) {
            return String.format(User.ANSI_GREEN + "Task %d: %-50s задано: %-20s Виконати до: %-15s %s" + User.ANSI_RESET, number, text,
                    createDate.format(formatForDateOfMade),
                    doBefore.format(formatForExpiryDate), temp);
        } else if (isOnTime.equalsIgnoreCase("З запізненням")) {
            return String.format(User.ANSI_RED + "Task %d: %-50s задано: %-20s Виконати до: %-15s %s" + User.ANSI_RESET, number, text,
                    createDate.format(formatForDateOfMade),
                    doBefore.format(formatForExpiryDate), temp);
        } else {
            if (temp.equalsIgnoreCase("Час сплинув")) {
                return String.format("Task %d: %-50s задано: %-20s Виконати до: %-15s залишилось :" + User.ANSI_RED + " %s " + User.ANSI_RESET, number, text,
                        createDate.format(formatForDateOfMade),
                        doBefore.format(formatForExpiryDate), temp);
            } else {
                return String.format("Task %d: %-50s задано: %-20s Виконати до: %-15s залишилось : %s ", number, text,
                        createDate.format(formatForDateOfMade),
                        doBefore.format(formatForExpiryDate), temp);
            }
        }
    }
}