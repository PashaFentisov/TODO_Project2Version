package Classes;

import Interfaces.ITaskEditor;
import Interfaces.ITaskReaderWriter;
import Interfaces.ITaskShower;
import Interfaces.IUser;

import java.io.File;
import java.util.LinkedList;

public class User implements IUser{                                    //TODO можливо треба все таки реалізувати всі інтерфейси
    private int countDoneTasks = 0;
    private int countAllTasks = 0;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    static File file = new File("D:\\idea project\\TODO_Project2Version\\src\\main\\resources\\user.json");
    private LinkedList<Task> tasksList = new LinkedList<>();
    private Task task;
    private ITaskEditor taskEditor;
    private ITaskShower taskShower;
    private ITaskReaderWriter taskReaderWriter;

    public User() {
    }

    public User(ITaskEditor taskEditor, ITaskShower taskShower, ITaskReaderWriter taskReaderWriter){
        this.taskShower = taskShower;
        this.taskReaderWriter = taskReaderWriter;
        this.taskEditor = taskEditor;
        try{
            readUserFromFile();
        }catch(Exception e){
            System.out.println(ANSI_RED + "У вас поки немає тасків" + ANSI_RESET);
        }
    }

    public void fillList() {
        taskEditor.fillList(this, task, tasksList);
    }

    public void writeUserToFile() {
        taskReaderWriter.writeUserToFile(this);
    }

    public void readUserFromFile() {
        User user = taskReaderWriter.readUserFromFile();
        this.tasksList = user.getTasksList();
        this.countDoneTasks = user.getCountDoneTasks();
        this.countAllTasks = user.getCountAllTasks();
        Task.setCountOfTasks(user.getCountAllTasks());
    }
    public void showListTasks(){
        taskShower.showListTasks(this);
    }
    public void showTasksInFile(){
        taskShower.showTasksInFile(this);
    }
    public void editList(){
        taskEditor.editList(this.tasksList, this);
    }

    public void deleteTasksFromFile(){
        taskEditor.deleteTasksFromFile(this);
    }

    public void makeTaskDone(){
        taskEditor.makeTaskDone(this);
    }

    public void showDoneTasks(){
        taskShower.showDoneTasks(this);
    }

    public void showTasksInProgress(){
        taskShower.showTasksInProgress(this);
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

    public void setCountDoneTasks(int countDoneTasks) {
        this.countDoneTasks = countDoneTasks;
    }

    public void setTasksList(LinkedList<Task> tasksList) {
        this.tasksList = tasksList;
    }

    public void setCountAllTasks(int countAllTasks) {
        this.countAllTasks = countAllTasks;
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
