package Interfaces;

import Classes.Task;
import Classes.User;

import java.util.List;

public interface ITaskEditor {
    Task fillList(User user, Task task, List<Task> tasksList, String tempTextOfTask, Integer tempSelectedDay, Integer tempSelectedMonth);
    void deleteTasksFromFile(User user);
    void makeTaskDone(User user);
}
