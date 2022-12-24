package Classes;

import Interfaces.ITaskShower;

import java.time.LocalDate;


/**
 * Класс для виводу списка тасків
 */
public class TaskShower implements ITaskShower {

    public TaskShower() {
    }

    /**
     * Метод виводить всі таски які знаходяться в списку переданого user.
     *
     * @param user Об'єкт користувача
     */
    @Override
    public void showListTasks(User user) {
        System.out.println(User.ANSI_YELLOW + "Ваші таски" + User.ANSI_RESET);
        user.getTasksList().forEach(System.out::println);
    }

    /**
     * Метод для зчитування з файлу об'єкта User методом {@link TaskReaderWriter#readUserFromFile()} і виводу всіх тасків які є у його списку.
     *
     * @param user Об'єкт користувача
     */
    @Override
    public void showTasksInFile(User user) {
        try {
            user.readUserFromFile();
        } catch (Exception e) {
            System.out.println(User.ANSI_RED + "Your file is empty" + User.ANSI_RESET);
            return;
        }
        if (user.getTasksList().isEmpty()) {
            System.out.println(User.ANSI_YELLOW + "У вас поки немає тасків" + User.ANSI_RESET);
        }
        System.out.println(User.ANSI_YELLOW + "Ваші таски" + User.ANSI_RESET);
        user.getTasksList().forEach(System.out::println);
    }

    /**
     * Метод призначений для виводу всіх виконаних тасків.
     * Об'єкт user зчитується з файлу методом {@link TaskReaderWriter#readUserFromFile()}.
     * Через Stream() вираховується скільки тасків було виконано вчасно.
     * Виводяться таски у яких isDone = true.
     * Виводиться кількість тасків які виконані вчасно, і з запізненням.
     * Список тасків для поточного user очищається
     *
     * @param user Об'єкт користувача
     */
    @Override
    public void showDoneTasks(User user) {
        int countOnTime;
        System.out.println(User.ANSI_GREEN + "Виконанні таски " + User.ANSI_RESET);
        try {
            user.readUserFromFile();
        } catch (Exception e) {
            System.out.println(User.ANSI_RED + "Your file is empty" + User.ANSI_RESET);
            return;
        }
        countOnTime = (int) user.getTasksList().stream().filter(Task::isDone).filter(t -> t.getOnTime().equalsIgnoreCase("Вчасно")).count();
        user.getTasksList().stream().filter(Task::isDone).forEach(System.out::println);
        System.out.println(User.ANSI_GREEN + "\nВиконано вчасно: " + countOnTime + User.ANSI_RESET);
        System.out.println(User.ANSI_RED + "Виконано не вчасно: " + (user.getCountDoneTasks() - countOnTime) + User.ANSI_RESET);
        user.getTasksList().clear();
    }

    /**
     * Метод призначений для виводу всіх ще не виконаних тасків.
     * Об'єкт user зчитується з файлу методом {@link TaskReaderWriter#readUserFromFile()}.
     * Виводяться таски у яких isDone = false.
     * Через Stream() вираховується скільки тасків уже з пропущеним строком виконання.
     * І скільки з актуальним.
     * Список тасків для поточного user очищається
     *
     * @param user Об'єкт користувача
     */
    @Override
    public void showTasksInProgress(User user) {
        int countTime;
        System.out.println(User.ANSI_RED + "\nНе виконанні завдання" + User.ANSI_RESET);
        try {
            user.readUserFromFile();
        } catch (Exception e) {
            System.out.println(User.ANSI_RED + "Your file is empty" + User.ANSI_RESET);
            return;
        }
        user.getTasksList().stream().filter(t -> !t.isDone()).forEach(System.out::println);
        countTime = (int) user.getTasksList().stream().filter(t -> !t.isDone()).map(Task::getDoBefore).filter(t -> t.isBefore(LocalDate.now())).count();
        if (countTime == 0) {
            System.out.println(User.ANSI_GREEN + "0 - З пропущеним строком виконання" + User.ANSI_RESET);
        } else {
            System.out.println(User.ANSI_RED + countTime + " - З пропущеним строком виконання" + User.ANSI_RESET);
        }
        countTime = (int) user.getTasksList().stream().filter(t -> !t.isDone()).count() - countTime;
        System.out.println(User.ANSI_GREEN + countTime + " - З актуальним строком виконання" + User.ANSI_RESET);
        user.getTasksList().clear();
    }
}
