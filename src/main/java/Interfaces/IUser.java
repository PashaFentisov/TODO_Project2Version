package Interfaces;

import Classes.Task;
import TestClasses.Main;

import java.util.List;

public interface IUser {
    Task fillList(String tempTextOfTask, Integer tempSelectedDay, Integer tempSelectedMonth);

    void writeUserToFile();

    void readUserFromFile();

    void showTasksInFile(Main main);

    void makeTaskDone(Task doneTask);

    void showDoneTasks(Main main);

    void showTasksInProgress(Main main);

    int getCountDoneTasks();

    int getCountAllTasks();

    List<Task> getTasksList();

    void setCountDoneTasks(int countDoneTasks);

    void setTasksList(List<Task> tasksList);

    void setCountAllTasks(int countAllTasks);

    void deleteSelectedTasksFromFile(Task task);

    void deleteAllTasksFromFile();

    void deleteDoneTasksFromFile();
}
