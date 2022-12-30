package Interfaces;

import Classes.Task;

import java.util.List;

public interface ITaskEditor {
    Task fillList(IUser user, Task task, List<Task> tasksList, String tempTextOfTask, Integer tempSelectedDay, Integer tempSelectedMonth);

    void makeTaskDone(IUser user, Task doneTask);

    void deleteSelectedTasksFromFile(IUser user, Task task);

    void deleteAllTasksFromFile(IUser user);

    void deleteDoneTasksFromFile(IUser user);
}
