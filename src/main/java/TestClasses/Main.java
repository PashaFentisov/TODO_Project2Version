package TestClasses;

import Classes.User;
import Interfaces.IUser;
import ServiceClasses.OwnReader;

/**
 * @author Pasha Fentisov
 * @version 2.0
 * @since 22.19.22
 */

public class Main {
    private static final OwnReader reader = new OwnReader();
    private static boolean exit;

    public static void main(String[] args) {
        IUser user = User.getInstatnce();
        while (!exit) {
            method(user);
        }
    }

    public static void method(IUser user) {
        System.out.println(User.ANSI_YELLOW + "Можливі функції" + User.ANSI_RESET);
        System.out.println("""
                1.0 Заповнити список завданнями - fillList
                2.0 Подивитись завдання в файлі - showTasksInFile
                3.0 Помітити завдання як виконане(DONE)- makeTaskDone
                4.0 Подивитись виконані завдання - showDoneTasks
                5.0 Подивитись завдання в прогрессі - showTasksInProgress
                6.0 Видалити завдання з файлу - deleteTasksFromFile""");
        System.out.print(User.ANSI_YELLOW + "Введіть номер функції яку хочете використати, або натисніть \"enter\": " + User.ANSI_RESET);
        String s = reader.next();
        if (s.isEmpty()) {
            exit = true;
        }
        switch (s) {
            case "1":
                user.fillList();
                break;
            case "2":
                user.showTasksInFile();
                break;
            case "3":
                user.makeTaskDone();
                break;
            case "4":
                user.showDoneTasks();
                break;
            case "5":
                user.showTasksInProgress();
                break;
            case "6":
                user.deleteTasksFromFile();
                break;
            default:
                break;
        }
    }
}
