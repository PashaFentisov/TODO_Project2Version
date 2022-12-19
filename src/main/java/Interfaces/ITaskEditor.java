package Interfaces;

import Classes.Task;

import java.util.LinkedList;

public interface ITaskEditor {
    void fillList(Task task, LinkedList<Task> tasksList);
}
