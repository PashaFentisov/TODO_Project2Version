package Interfaces;

import Classes.User;
import TestClasses.Main;


public interface ITaskShower {
    void showListTasks(User user);

    void showTasksInFile(User user, Main main);

    void showDoneTasks(User user, Main main);

    void showTasksInProgress(User user, Main main);
}
