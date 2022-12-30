package Classes;

import FormsForInterface.ClassForFormShowingTasksInProgress;
import FormsForInterface.FormForDoneTasks;
import FormsForInterface.FormForShowTaskClass;
import Interfaces.*;
import TestClasses.Main;

import java.time.LocalDate;


/**
 * Класс для виводу списка тасків
 */
public class TaskShower implements ITaskShower {

    public TaskShower() {
    }

    /**
     * Метод для зчитування з файлу об'єкта User методом {@link TaskReaderWriter#readUserFromFile()} і виводу всіх тасків які є у його списку.
     * Для виводу використовується метод {@link FormForShowTaskClass#show(Object[], Main)}.
     *
     * @param user Об'єкт користувача
     * @param main Об'єкт головного класа
     */
    @Override
    public void showTasksInFile(IUser user, Main main) {
        try {
            user.readUserFromFile();
        } catch (Exception e) {
        }

        IFormForShowTaskClass formForShowTaskClass = new FormForShowTaskClass();
        formForShowTaskClass.show(user.getTasksList().toArray(), main);
    }


    /**
     * Метод призначений для виводу всіх виконаних тасків.
     * Об'єкт user зчитується з файлу методом {@link TaskReaderWriter#readUserFromFile()}.
     * Через Stream() вираховується скільки тасків було виконано вчасно.
     * Виводяться таски у яких isDone = true.
     * Виводиться кількість тасків які виконані вчасно, і з запізненням.
     * Для виводу використовується метод {@link FormForDoneTasks#show(Object[], String, String, Main)}.
     * Список тасків для поточного user очищається.
     *
     * @param user Об'єкт користувача
     * @param main Об'єкт головного класа
     */
    @Override
    public void showDoneTasks(IUser user, Main main) {
        int countOnTime;
        try {
            user.readUserFromFile();
        } catch (Exception e) {
        }
        countOnTime = (int) user.getTasksList().stream().filter(ITask::isDone).filter(t -> t.getOnTime().equalsIgnoreCase("Вчасно")).count();
        IFormForDoneTasks form = new FormForDoneTasks();
        form.show(user.getTasksList().stream().filter(ITask::isDone).toArray(), ("Виконано вчасно: " + countOnTime), ("Виконано не вчасно: " + (user.getCountDoneTasks() - countOnTime)), main);
        user.getTasksList().clear();
    }


    /**
     * Метод призначений для виводу всіх ще не виконаних тасків.
     * Об'єкт user зчитується з файлу методом {@link TaskReaderWriter#readUserFromFile()}.
     * Виводяться таски у яких isDone = false.
     * Через Stream() вираховується скільки тасків уже з пропущеним строком виконання.
     * І скільки з актуальним.
     * Для виводу використовується метод {@link ClassForFormShowingTasksInProgress#show(Main, Object[], String, String)}.
     * Список тасків для поточного user очищається.
     *
     * @param user Об'єкт користувача
     * @param main Об'єкт головного класа
     */
    @Override
    public void showTasksInProgress(IUser user, Main main) {
        int countNotOnTime;
        int countOnTime;
        try {
            user.readUserFromFile();
        } catch (Exception e) {
        }
        countNotOnTime = (int) user.getTasksList().stream().filter(t -> !t.isDone()).map(ITask::getDoBefore).filter(t -> t.isBefore(LocalDate.now())).count();
        countOnTime = (int) user.getTasksList().stream().filter(t -> !t.isDone()).count() - countNotOnTime;
        IClassForFormShowingTasksInProgress classForFormShowingTasksInProgress = new ClassForFormShowingTasksInProgress();
        classForFormShowingTasksInProgress.show(main, user.getTasksList().stream().filter(t -> !t.isDone()).toArray(), (countNotOnTime + " - З пропущеним строком виконання"), (countOnTime + " - З актуальним строком виконання"));
        user.getTasksList().clear();
    }
}
