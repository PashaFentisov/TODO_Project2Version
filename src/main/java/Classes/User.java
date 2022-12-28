package Classes;

import Interfaces.ITaskEditor;
import Interfaces.ITaskReaderWriter;
import Interfaces.ITaskShower;
import Interfaces.IUser;
import ServiceClasses.OwnReader;
import TestClasses.Main;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Загальний клас який оголошує роботу всіх методів з списком тасків.
 */
public class User implements IUser {
    private int countDoneTasks = 0;
    private int countAllTasks = 0;
    public static File file = new File("user.json");
    private List<Task> tasksList = new LinkedList<>();
    private Task task;
    private ITaskEditor taskEditor;
    private ITaskShower taskShower;
    private ITaskReaderWriter taskReaderWriter;

    public User() {
    }

    private static IUser user;

    /**
     * Метод який повертає об'єкт класа User так як застосований патерн Singleton.
     *
     * @return Об'єкт User
     */
    public synchronized static IUser getInstance() {
        if (user == null) {
            user = new User(new TaskEditor(new OwnReader()), new TaskShower(), new TaskReaderWriter());
        }
        return user;
    }

    private User(ITaskEditor taskEditor, ITaskShower taskShower, ITaskReaderWriter taskReaderWriter) {
        this.taskShower = taskShower;
        this.taskReaderWriter = taskReaderWriter;
        this.taskEditor = taskEditor;
        try {
            readUserFromFile();
        } catch (Exception e) {
        }
    }

    //    /**
//     * Метод для виклику метода {@link TaskEditor#fillList(User, Task, List)} через об'єкт цього класу.
//     * Передається this - поточний об'єкт User,  task - об'єкт класа Task який заповнюється і додається в список тасків,
//     * tasksList - список тасків поточного об'єкта класа USer
//     */
    @Override
    public Task fillList(String tempTextOfTask, Integer tempSelectedDay, Integer tempSelectedMonth) {
        task = taskEditor.fillList(this, task, tasksList, tempTextOfTask, tempSelectedDay, tempSelectedMonth);
        return task;
    }

    /**
     * Метод для виклику метода {@link TaskReaderWriter#writeUserToFile(User)} через об'єкт цього класу.
     * Передається this - поточний об'єкт User.
     */
    @Override
    public void writeUserToFile() {
        taskReaderWriter.writeUserToFile(this);
    }

    /**
     * Метод для виклику метода {@link TaskReaderWriter#readUserFromFile()} через об'єкт цього класу.
     * Метод {@link TaskReaderWriter#readUserFromFile()} повертає об'єкт класа User.
     * З цього об'єкта дістаємо поля tasksList, countDoneTasks, countAllTasks і присвоюємо їх значення відповідним полям поточного об'єкта User.
     */
    @Override
    public void readUserFromFile() {
        User user = taskReaderWriter.readUserFromFile();
        this.tasksList = user.getTasksList();
        this.countDoneTasks = user.getCountDoneTasks();
        this.countAllTasks = user.getCountAllTasks();
        Task.setCountOfTasks(user.getCountAllTasks());
    }

    /**
     * Метод для виклику метода {@link TaskShower#showTasksInFile(User, Main)} через об'єкт цього класу.
     * Передається this - поточний об'єкт User.
     */
    @Override
    public void showTasksInFile(Main main) {
        taskShower.showTasksInFile(this, main);
    }

    /**
     * Метод для виклику метода {@link TaskEditor#makeTaskDone(IUser, Task)} через об'єкт цього класу.
     * Передається this - поточний об'єкт User.
     */
    @Override
    public void makeTaskDone(Task doneTask) {
        taskEditor.makeTaskDone(this, doneTask);
    }

    /**
     * Метод для виклику метода {@link TaskShower#showDoneTasks(User, Main)} через об'єкт цього класу.
     * Передається this - поточний об'єкт User.
     */
    @Override
    public void showDoneTasks(Main main) {
        taskShower.showDoneTasks(this, main);
    }

    /**
     * Метод для виклику метода {@link TaskShower#showTasksInProgress(User, Main)} через об'єкт цього класу.
     * Передається this - поточний об'єкт User.
     */
    @Override
    public void showTasksInProgress(Main main) {
        taskShower.showTasksInProgress(this, main);
    }

    @Override
    public void deleteSelectedTasksFromFile(Task task) {
        taskEditor.deleteSelectedTasksFromFile(this, task);
    }

    @Override
    public void deleteAllTasksFromFile() {
        taskEditor.deleteAllTasksFromFile(this);
    }

    @Override
    public void deleteDoneTasksFromFile() {
        taskEditor.deleteDoneTasksFromFile(this);
    }

    public int getCountDoneTasks() {
        return countDoneTasks;
    }

    public int getCountAllTasks() {
        return countAllTasks;
    }

    public List<Task> getTasksList() {
        return tasksList;
    }

    public void setCountDoneTasks(int countDoneTasks) {
        this.countDoneTasks = countDoneTasks;
    }

    public void setTasksList(List<Task> tasksList) {
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
