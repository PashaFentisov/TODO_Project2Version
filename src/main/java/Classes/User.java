package Classes;

import Interfaces.ITaskEditor;
import Interfaces.ITaskReaderWriter;
import Interfaces.ITaskShower;
import Interfaces.IUser;
import ServiceClasses.OwnReader;

import java.io.File;
import java.util.LinkedList;

public class User implements IUser{
    private int countDoneTasks = 0;
    private int countAllTasks = 0;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    static File file = new File("user.json");
    private LinkedList<Task> tasksList = new LinkedList<>();
    private Task task;
    private ITaskEditor taskEditor;
    private ITaskShower taskShower;
    private ITaskReaderWriter taskReaderWriter;

    public User() {}

    private static User user;

    public synchronized static User getInstatnce(){
        if(user==null){
            user = new User(new TaskEditor(new OwnReader()), new TaskShower(), new TaskReaderWriter());
        }
        return user;
    }

    private User(ITaskEditor taskEditor, ITaskShower taskShower, ITaskReaderWriter taskReaderWriter){
        this.taskShower = taskShower;
        this.taskReaderWriter = taskReaderWriter;
        this.taskEditor = taskEditor;
        try{
            readUserFromFile();
        }catch(Exception e){
            System.out.println(ANSI_RED + "У вас поки немає тасків" + ANSI_RESET);
        }
    }
    @Override
    public void fillList() {
        taskEditor.fillList(this, task, tasksList);
    }
    @Override
    public void writeUserToFile() {
        taskReaderWriter.writeUserToFile(this);
    }
    @Override
    public void readUserFromFile() {
        User user = taskReaderWriter.readUserFromFile();
        this.tasksList = user.getTasksList();
        this.countDoneTasks = user.getCountDoneTasks();
        this.countAllTasks = user.getCountAllTasks();
        Task.setCountOfTasks(user.getCountAllTasks());
    }
    @Override
    public void showListTasks(){
        taskShower.showListTasks(this);
    }
    @Override
    public void showTasksInFile(){
        taskShower.showTasksInFile(this);
    }
    @Override
    public void editList(){
        taskEditor.editList(this.tasksList, this);
    }
    @Override
    public void deleteTasksFromFile(){
        taskEditor.deleteTasksFromFile(this);
    }
    @Override
    public void makeTaskDone(){
        taskEditor.makeTaskDone(this);
    }
    @Override
    public void showDoneTasks(){
        taskShower.showDoneTasks(this);
    }
    @Override
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
