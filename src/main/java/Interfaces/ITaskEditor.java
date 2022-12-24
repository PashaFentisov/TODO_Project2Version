package Interfaces;

import Classes.Task;
import Classes.User;

import java.util.List;

public interface ITaskEditor {
    void fillList(User user, Task task, List<Task> tasksList);
    void editList(List<Task> list, User user);
    void deleteTasksFromFile(User user);
    void makeTaskDone(User user);
}
