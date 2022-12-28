package Interfaces;

import java.time.LocalDate;

public interface ITask {
    String getText();

    void setText(String text);

    boolean isDone();

    void setDone(boolean done);

    LocalDate getDoBefore();

    void setDoBefore(LocalDate doBefore);

    LocalDate getDoneDate();

    void setDoneDate(LocalDate doneDate);

    String getOnTime();

    void setOnTime(String onTime);

    int getNumber();

    void setNumber(int number);
}
