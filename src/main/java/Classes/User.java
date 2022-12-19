package Classes;

import Interfaces.ITaskEditor;
import Interfaces.ITaskReaderWriter;
import Interfaces.ITaskShower;

import java.io.File;
import java.util.LinkedList;

public class User{                                    //TODO можливо треба все таки реалізувати всі інтерфейси
    private int countDoneTasks = 0;
    private int countAllTasks = 0;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    static File file = new File("D:\\idea project\\TODO_Project2Version\\src\\main\\resources\\user.json");
    private LinkedList<Task> tasksList = new LinkedList<>();
    Task task;
    ITaskEditor taskEditor;
    ITaskShower taskShower;
    ITaskReaderWriter taskReaderWriter;

    public User() {
    }

    public User(ITaskEditor taskEditor, ITaskShower taskShower, ITaskReaderWriter taskReaderWriter){
        this.taskShower = taskShower;
        this.taskReaderWriter = taskReaderWriter;
        this.taskEditor = taskEditor;
    }

    public void fillList() {
        taskEditor.fillList(task, tasksList);
    }

    public void writeUserToFile() {
        taskReaderWriter.writeUserToFile(this);
    }

    public User readUserFromFile() {
        return taskReaderWriter.readUserFromFile();
    }

    public int getCountDoneTasks() {
        return countDoneTasks;
    }


    public int getCountAllTasks() {
        return countAllTasks;
    }

    public LinkedList<Task> getTasksList() {
        return tasksList;
    }

    @Override
    public String toString() {
        return "User{" +
                "countDoneTasks=" + countDoneTasks +
                ", countAllTasks=" + countAllTasks +
                ", tasksList=" + tasksList +
                ", task=" + task +
                '}';
    }
}
