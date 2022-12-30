package Interfaces;

import TestClasses.Main;


public interface ITaskShower {
    void showTasksInFile(IUser user, Main main);

    void showDoneTasks(IUser user, Main main);

    void showTasksInProgress(IUser user, Main main);
}
