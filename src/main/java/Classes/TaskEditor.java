package Classes;

import Interfaces.IOwnReader;
import Interfaces.ITaskEditor;
import Interfaces.IUser;

import java.time.LocalDate;
import java.util.List;

/**
 * Класс який відповідає за редагування списку тасків
 */
public class TaskEditor implements ITaskEditor {
    /**
     * Поток для зчитування тексту з клавіатури
     */
    IOwnReader reader;

    public TaskEditor(IOwnReader reader) {
        this.reader = reader;
    }

    public TaskEditor() {
    }


    /**
     * Метод відповідає за наповнення списку тасками.
     * Користувач вводить текст, та дату до якої треба виконати task.
     * Для task початково поле isOnTime задається як пусте, щоб уникнути nullPointerException.
     * При введенні дати до якої треба виконати таск проводиться перевірка на коректність,
     * Рік для doBefore за змовченням задається поточний.
     * Для переданого user встановлює кількість тасків на + 1 з кожним доданим таском.
     * Для отриманого User в список додається відредагований таск.
     *
     * @param user              Об'єкт користувача
     * @param task              Таск
     * @param tasksList         Список тасків
     * @param tempTextOfTask    Текст таску
     * @param tempSelectedDay   День до якого треба виконати таск
     * @param tempSelectedMonth Місяць до якого треба виконати таск
     * @return created task
     */
    @Override
    public Task fillList(IUser user, Task task, List<Task> tasksList, String tempTextOfTask, Integer tempSelectedDay, Integer tempSelectedMonth) {
        task = new Task(tempTextOfTask);
        task.setOnTime("");
        task.setDoBefore(task.getDoBefore().withDayOfMonth(tempSelectedDay));
        task.setDoBefore(task.getDoBefore().withMonth(tempSelectedMonth));
        task.setCreateDate(LocalDate.now());
        tasksList.add(task);
        user.setCountAllTasks(user.getCountAllTasks() + 1);
        user.setTasksList(tasksList);
        return task;
    }

    /**
     * Метод дозволяє видалити всі таски з файлу.
     * Для отриманого user очищається список тасків.
     * Поля countAllTasks і countDoneTasks приймають значення 0
     * user з такими значеннями записується у файл методом {@link TaskReaderWriter#writeUserToFile(IUser)}.
     *
     * @param user Об'єкт користувача
     */
    public void deleteAllTasksFromFile(IUser user) {
        user.getTasksList().clear();
        user.setCountAllTasks(0);
        user.setCountDoneTasks(0);
        user.writeUserToFile();
    }

    /**
     * Метод дозволяє видалити всі зроблені таски з файлу.
     * Список з тасками перебирається в циклі і якщо у таска поле isDone = true, то цей таск видаляється.
     * При цьому у отриманого user поля countAllTasks і countDoneTasks зменшуються на 1 при кожному видалені.
     * В вкладеному циклі номера тасків (поле number) зменшуються на 1 при видалені попереднього.
     *
     * @param user Об'єкт користувача
     */
    public void deleteDoneTasksFromFile(IUser user) {
        user.setCountDoneTasks(0);
        for (int i = 0; i < user.getTasksList().size(); i++) {
            if (user.getTasksList().get(i).isDone()) {
                user.getTasksList().remove(i);
                user.setCountAllTasks(user.getCountAllTasks() - 1);
                for (int k = 0; k < user.getTasksList().size(); k++) {
                    if (user.getTasksList().get(k).getNumber() > i) {
                        user.getTasksList().get(k).setNumber(user.getTasksList().get(k).getNumber() - 1);
                    }
                }
                --i;
            }
        }
        user.writeUserToFile();
    }

    /**
     * Метод видаляє отриманий таск з списку.
     * Якщо він був помічений як DONE то поле countDoneTasks для отриманого user зменшується на 1.
     * Для отриманого user поле countAllTasks зменшується на 1 при кожному видалені
     * В циклі номера тасків (поле number) зменшуються на 1 при видалені попереднього.
     *
     * @param user Об'єкт користувача
     * @param task таск який буде видалено
     */
    public void deleteSelectedTasksFromFile(IUser user, Task task) {
        for (int k = 0; k < user.getTasksList().size(); k++) {
            if (user.getTasksList().get(k).getNumber() > task.getNumber()) {
                user.getTasksList().get(k).setNumber(user.getTasksList().get(k).getNumber() - 1);
            }
        }
        if (task.isDone()) {
            user.setCountDoneTasks(user.getCountDoneTasks() - 1);
        }
        user.getTasksList().remove(task);
        user.setCountAllTasks(user.getCountAllTasks() - 1);
        user.writeUserToFile();
    }

    /**
     * Метод помічає таск як виконаний.
     * Користувач обирає таск який хоче помітити як DONE.
     * Полю doneDate у task присвоюється поточна дата.
     * Полю isOnTime у task просвоюється значення, "Вчасно" якщо таск помічено як Done перед датою яка знаходиться в doBefore поточного task,
     * "З запізненням" якщо після doBefore.
     * Поле countDoneTasks у user збільшується на 1 з кожним поміченим task.
     * Після всіх перетворень відформатований список записується у файл методом - {@link TaskReaderWriter#writeUserToFile(IUser)}.
     *
     * @param user     Об'єкт користувача
     * @param doneTask Об'єкт користувача
     */
    @Override
    public void makeTaskDone(IUser user, Task doneTask) {
        int temp = user.getTasksList().indexOf(doneTask);
        doneTask.setDoneDate(LocalDate.now());
        doneTask.setDone(true);
        if (LocalDate.now().isAfter(doneTask.getDoBefore())) {
            doneTask.setOnTime("З запізненням");
        } else {
            doneTask.setOnTime("Вчасно");
        }
        user.getTasksList().set(temp, doneTask);
        user.setCountDoneTasks(user.getCountDoneTasks() + 1);
        user.writeUserToFile();
    }
}