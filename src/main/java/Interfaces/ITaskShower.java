package Interfaces;

import Classes.User;

public interface ITaskShower {
    void showListTasks(User user);
    void showTasksInFile(User user);
    void showDoneTasks(User user);
    void showTasksInProgress(User user);
}
