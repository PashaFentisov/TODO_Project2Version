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

    /**
     * Поле для виводу тексту в різних кольорах
     */
    public static final String ANSI_RESET = "\u001B[0m";
    /**
     * Поле для виводу тексту в різних кольорах
     */
    public static final String ANSI_GREEN = "\u001B[32m";
    /**
     * Поле для виводу тексту в різних кольорах
     */
    public static final String ANSI_RED = "\u001B[31m";
    /**
     * Поле для виводу тексту в різних кольорах
     */
    public static final String ANSI_YELLOW = "\u001B[33m";
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
            System.out.println(ANSI_RED + "У вас поки немає тасків" + ANSI_RESET);
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
     * Метод для виклику метода {@link TaskShower#showListTasks(User)} через об'єкт цього класу.
     * Передається this - поточний об'єкт User.
     */
    @Override
    public void showListTasks() {
        taskShower.showListTasks(this);
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
     * Метод для виклику метода {@link TaskEditor#deleteTasksFromFile(User)} через об'єкт цього класу.
     * Передається this - поточний об'єкт User.
     */
    @Override
    public void deleteTasksFromFile() {
        taskEditor.deleteTasksFromFile(this);
    }
    
    /**
     * Метод для виклику метода {@link TaskEditor#makeTaskDone(User)} через об'єкт цього класу.
     * Передається this - поточний об'єкт User.
     */
    @Override
    public void makeTaskDone() {
        taskEditor.makeTaskDone(this);
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
     * Метод для виклику метода {@link TaskShower#showTasksInProgress(User)} через об'єкт цього класу.
     * Передається this - поточний об'єкт User.
     */
    @Override
    public void showTasksInProgress() {
        taskShower.showTasksInProgress(this);
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
