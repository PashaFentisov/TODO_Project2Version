package Interfaces;

import Classes.Task;
import TestClasses.Main;

import java.util.List;

public interface IUser {
    void fillList();

    void writeUserToFile();

    void readUserFromFile();

    void showListTasks();

    void showTasksInFile(Main main);

    void editList();

    void deleteTasksFromFile();

    void makeTaskDone();

    void showDoneTasks(Main main);

    void showTasksInProgress();

    int getCountDoneTasks();

    int getCountAllTasks();

    List<Task> getTasksList();

    void setCountDoneTasks(int countDoneTasks);

    void setTasksList(List<Task> tasksList);

    void setCountAllTasks(int countAllTasks);

    Task getTask();

    void setTask(Task task);
}
