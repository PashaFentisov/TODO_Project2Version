package Interfaces;

import Classes.Task;
import Classes.User;

import java.util.LinkedList;

public interface ITaskEditor {
    void fillList(User user, Task task, LinkedList<Task> tasksList);
    void editList(LinkedList<Task> list, User user);
    void deleteTasksFromFile(User user);
}
