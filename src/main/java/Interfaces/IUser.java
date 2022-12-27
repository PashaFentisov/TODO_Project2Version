package Interfaces;

import Classes.Task;
import TestClasses.Main;

import java.util.List;

public interface IUser {
    Task fillList(String tempTextOfTask, Integer tempSelectedDay, Integer tempSelectedMonth);

    void writeUserToFile();

    void readUserFromFile();

    void showListTasks();

    void showTasksInFile(Main main);

    void deleteTasksFromFile();

    void makeTaskDone(Task doneTask);

    void showDoneTasks(Main main);

    void showTasksInProgress(Main main);

    int getCountDoneTasks();

    int getCountAllTasks();

    List<Task> getTasksList();

    void setCountDoneTasks(int countDoneTasks);

    void setTasksList(List<Task> tasksList);

    void setCountAllTasks(int countAllTasks);

}
