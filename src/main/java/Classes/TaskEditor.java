package Classes;

import Interfaces.IOwnReader;
import Interfaces.ITaskEditor;

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
     * Користувач вводить текст, та дату до якої треба виконати переданий task.
     * При введені "stop" наповнення списку тасками завершується і вони записуються у файл методом {@link TaskReaderWriter#writeUserToFile(User)}.
     * Для task початково поле isOnTime задається як пусте, щоб уникнути nullPointerException.
     * При введенні дати до якої треба виконати таск проводиться перевірка на коректність,
     * якщо вона не пройдена то полю doBefore присвоюється поточна дата.
     * Рік для doBefore за змовченням задається поточний.
     * Для переданого user встановлює кількість тасків на + 1 з кожним доданим таском.
     * Якщо користувач хоче змінити список перед записом до файлу викликається метод  {@link TaskEditor#editList(List, User)},
     * після редагування об'єкт user з списком тасків записується у файл методом {@link TaskReaderWriter#writeUserToFile(User)}.
     * І з файлу зчитується оновлений об'єкт user методом {@link TaskReaderWriter#readUserFromFile()}.
     *
     * @param user      Об'єкт користувача
     * @param task      Таск
     * @param tasksList Список тасків
     */
    @Override
    public void fillList(User user, Task task, List<Task> tasksList) {
        String tempStr;
        int day;
        int month;
        while (true) {
            System.out.print(User.ANSI_YELLOW + "Введіть таск який хочете додати до файлу, або stop: " + User.ANSI_RESET);
            tempStr = reader.nextLine();
            if (tempStr.equalsIgnoreCase("stop")) {
                break;
            }
            task = new Task(tempStr);
            task.setOnTime("");
            System.out.println(User.ANSI_YELLOW + "\nВведіть дату до якої треба виконати таск" + User.ANSI_RESET);
            System.out.print(User.ANSI_YELLOW + "Введіть день: " + User.ANSI_RESET);
            try {
                day = reader.nextInt();
                task.setDoBefore(task.getDoBefore().withDayOfMonth(day));
            } catch (Exception e) {
                System.out.println(User.ANSI_RED + "You entered wrong value, day is " + LocalDate.now().getDayOfMonth() + User.ANSI_RESET);
                task.setDoBefore(task.getDoBefore().withDayOfMonth(LocalDate.now().getDayOfMonth()));
            }
            System.out.print(User.ANSI_YELLOW + "Введіть місяць: " + User.ANSI_RESET);
            try {
                month = reader.nextInt();
                task.setDoBefore(task.getDoBefore().withMonth(month));
            } catch (Exception e) {
                System.out.println(User.ANSI_RED + "You entered wrong value, month is " + LocalDate.now().getMonth() + User.ANSI_RESET);
                task.setDoBefore(task.getDoBefore().withMonth(LocalDate.now().getMonthValue()));
            }
            tasksList.add(task);
            user.setCountAllTasks(task.getNumber());
        }
        user.setTasksList(tasksList);
        System.out.println(User.ANSI_YELLOW + "\nThese tasks will be saved, if you agree press enter else enter \"edit\"" + User.ANSI_RESET);
        user.showListTasks();
        if (reader.nextLine().equalsIgnoreCase("edit")) {
            editList(user.getTasksList(), user);
        }
        user.writeUserToFile();
        try {
            user.readUserFromFile();
        } catch (Exception e) {
        }
    }

    /**
     * Метод відповідає за редагування списку з тасками.
     * Можливе додавання або видалення тасків з списку.
     * При додавані викликиється метод {@link TaskEditor#fillList(User, Task, List)}.
     * При видаленні викликається метод {@link TaskEditor#deleteSomeTasksFromFile(User, String)}.
     * Після редагування повертаємось в метод {@link TaskEditor#fillList(User, Task, List)},
     * записуєм user в файл і зчитуєм оновленого
     *
     * @param user Об'єкт користувача
     * @param list Список тасків переданий з {@link TaskEditor#fillList(User, Task, List)}
     */
    @Override
    public void editList(List<Task> list, User user) {
        System.out.print(User.ANSI_YELLOW + "Bи хочете додати(add) таск чи видалити(del) таск: " + User.ANSI_RESET);
        String tempString = reader.next();
        if (tempString.equalsIgnoreCase("add")) {
            user.fillList();
        } else {
            while (true) {
                if (user.getTasksList().isEmpty()) {
                    System.out.println(User.ANSI_RED + "У вас немає тасків" + User.ANSI_RESET);
                    return;
                }
                user.showListTasks();
                System.out.print(User.ANSI_YELLOW + "Який таск за номером ви хочете видалити: " + User.ANSI_RESET);
                tempString = reader.next();
                deleteSomeTasksFromFile(user, tempString);
            }
        }
    }

    /**
     * Метод дозволяє видалити всі таски з файлу.
     * Це службовий метод який викликається в методі {@link TaskEditor#deleteTasksFromFile(User)}.
     * Для отриманого user очищається список тасків.
     * Поля countAllTasks і countDoneTasks приймають значення 0
     * user з такими значеннями записується у файл методом {@link TaskReaderWriter#writeUserToFile(User)}.
     * Після цього методу робота метода {@link TaskEditor#deleteTasksFromFile(User)} завершується
     *
     * @param user Об'єкт користувача
     */
    private boolean deleteAllTasksFromFile(User user) {
        System.out.print(User.ANSI_RED + "Всі таски будуть видалені з пам'яті, для підтвердження натисніть enter, для відміни введіть stop: " + User.ANSI_RESET);
        if (reader.nextLine().equalsIgnoreCase("stop")) {
            return true;
        }
        user.getTasksList().clear();
        user.setCountAllTasks(0);
        user.setCountDoneTasks(0);
        user.writeUserToFile();
        System.out.println(User.ANSI_GREEN + "Всі таски видалено" + User.ANSI_RESET);
        return false;
    }

    /**
     * Метод дозволяє видалити всі зроблені таски з файлу.
     * Це службовий метод який викликається в методі {@link TaskEditor#deleteTasksFromFile(User)}.
     * Список з тасками перебирається в циклі і якщо у таска поле isDone = true,
     * то цей таск видаляється.
     * При цьому у отриманого user поля countAllTasks і countDoneTasks зменшуються на 1 при кожному видалені.
     * В вкладеному циклі номера тасків (поле number) зменшуються на 1 при видалені попереднього.
     * Після роботи цього метода робота метода {@link TaskEditor#deleteTasksFromFile(User)} продовжується.
     *
     * @param user Об'єкт користувача
     */
    private void deleteDoneTasksFromFile(User user) {
        System.out.print(User.ANSI_RED + "Всі виконані таски будуть видалені з пам'яті, для підтвердження натисніть enter, для відміни введіть stop: " + User.ANSI_RESET);
        if (reader.nextLine().equalsIgnoreCase("stop")) {
            return;
        }
        for (int i = 0; i < user.getTasksList().size(); i++) {
            if (user.getTasksList().get(i).isDone()) {
                user.getTasksList().remove(i);
                user.setCountAllTasks(user.getCountAllTasks() - 1);
                user.setCountDoneTasks(0);
                for (int k = 0; k < user.getTasksList().size(); k++) {
                    if (user.getTasksList().get(k).getNumber() > i) {
                        user.getTasksList().get(k).setNumber(user.getTasksList().get(k).getNumber() - 1);
                    }
                }
                --i;
            }
        }
        System.out.println(User.ANSI_GREEN + "Всі виконані таски видалено" + User.ANSI_RESET);
    }

    /**
     * Метод дозволяє видалити таск з списку по переданому індексу.
     * Це службовий метод який викликається в методі {@link TaskEditor#deleteTasksFromFile(User)}.
     * Якщо передано неправильне значення робота метода завершується.
     * В циклі співставляються номера тасків і передане значення.
     * Якщо номер таску (поле number) = переданому значенню таск видаляється,
     * якщо він був помічений як DONE то поле countDoneTasks для отриманого user зменшується на 1.
     * Для отриманого user поле countAllTasks зменшується на 1 при кожному видалені
     * В вкладеному циклі номера тасків (поле number) зменшуються на 1 при видалені попереднього.
     * Після роботи цього метода робота метода {@link TaskEditor#deleteTasksFromFile(User)} продовжується.
     *
     * @param user Об'єкт користувача
     * @param temp індекс таска в списку який буде видалено
     */
    private void deleteSomeTasksFromFile(User user, String temp) {
        int intTemp;
        try {
            intTemp = Integer.parseInt(temp);
        } catch (Exception e) {
            System.out.println(User.ANSI_RED + "Wrong value!" + User.ANSI_RESET);
            return;
        }
        for (int i = 0; i < user.getTasksList().size(); i++) {
            if (user.getTasksList().get(i).getNumber() == intTemp) {
                if (user.getTasksList().get(i).isDone()) {
                    user.setCountDoneTasks(user.getCountDoneTasks() - 1);
                }
                user.getTasksList().remove(i);
                user.setCountAllTasks(user.getCountAllTasks() - 1);
                for (int k = 0; k < user.getTasksList().size(); k++) {
                    if (user.getTasksList().get(k).getNumber() > i) {
                        user.getTasksList().get(k).setNumber(user.getTasksList().get(k).getNumber() - 1);
                    }
                }
                System.out.println(User.ANSI_GREEN + "Task " + intTemp + " видалено" + User.ANSI_RESET);
                break;
            }
        }
    }

    /**
     * Метод відповідає за видалення тасків з файлу.
     * Читаємо з файлу список тасків методом {@link TaskReaderWriter#readUserFromFile()}.
     * Запитуємо у користувача що саме він хоче видалити,
     * stop - видалення закінчується.
     * all - викликаємо метод - {@link TaskEditor#deleteAllTasksFromFile(User)}.
     * DONE - викликаємо метод - {@link TaskEditor#deleteDoneTasksFromFile(User)}.
     * Також користувач може ввести номер таску який
     * хоче видалити в такому випадку викликається метод - {@link TaskEditor#deleteSomeTasksFromFile(User, String)}.
     * Після перетворень відформатований список записується в файл методом - {@link TaskReaderWriter#writeUserToFile(User)}.
     *
     * @param user Об'єкт користувача
     */
    @Override
    public void deleteTasksFromFile(User user) {
        String temp;
        try {
            user.readUserFromFile();
        } catch (Exception e) {
            System.out.println(User.ANSI_RED + "You don`t have any task" + User.ANSI_RESET);
            return;
        }
        while (true) {
            System.out.println(User.ANSI_YELLOW + "----------------------------------------------------------------------------------------------------------------------------" + User.ANSI_RESET);
            user.showListTasks();
            System.out.print(User.ANSI_YELLOW + "Введіть номер таску якій хочете видалити\nДля видалення всіх таксів - all\nДля видалення зроблених - DONE\nДля закінчення - stop\nПоле для вводу:" + User.ANSI_RESET);
            temp = reader.next();
            if (temp.equalsIgnoreCase("stop")) {
                break;
            }
            if (temp.equalsIgnoreCase("all")) {
                if (!deleteAllTasksFromFile(user)) {
                    return;
                }
            }
            if (temp.equalsIgnoreCase("DONE")) {
                deleteDoneTasksFromFile(user);
            } else {
                deleteSomeTasksFromFile(user, temp);
            }
        }
        user.writeUserToFile();
    }

    /**
     * Метод помічає таск як виконаний.
     * Читаємо список тасків з файлу методом {@link TaskReaderWriter#readUserFromFile()}, якщо тасків ще не має то метод закінчує роботу.
     * Виводимо список тасків які є методом - {@link TaskShower#showListTasks(User)}.
     * Користувач може ввести номер таску який хоче помітити як DONE.
     * Або натиснути "enter" і вийти з редагування
     * Проводиться перевірка чи було введено число, чи таск з таким номером є у списку, і чи він ще не помічений як DONE.
     * При проходженні всіх перевірок поле isDone у task помічається як true.
     * Полю doneDate у task присвоюється поточна дата.
     * Полю isOnTime у task просвоюється значення, "Вчасно" якщо таск помічено як Done перед датою яка знаходиться в doBefore поточного task,
     * "З запізненням" якщо після doBefore.
     * Поле countDoneTasks у user збільшується на 1 з кожним поміченим task.
     * Після всіх перетворень відформатований список записується у файл методом - {@link TaskReaderWriter#writeUserToFile(User)}.
     *
     * @param user Об'єкт користувача
     */
    @Override
    public void makeTaskDone(User user) {
        int intTemp;
        try {
            user.readUserFromFile();
        } catch (Exception e) {
            System.out.println(User.ANSI_RED + "Your file is empty" + User.ANSI_RESET);
            return;
        }
        while (true) {
            user.showListTasks();
            System.out.print(User.ANSI_YELLOW + "Вкажіть номер таску який уже зробили, або натисніть \"enter\": " + User.ANSI_RESET);
            try {
                intTemp = Integer.parseInt(reader.next());
            } catch (Exception e) {
                break;
            }
            if (intTemp - 1 >= user.getTasksList().size()) {
                System.out.println(User.ANSI_RED + "Такого таску немає, повторіть спробу" + User.ANSI_RESET);
                continue;
            }
            if (user.getTasksList().get(intTemp - 1).isDone()) {
                System.out.println(User.ANSI_RED + "Цей таск вже помічено як зроблений, повторіть спробу" + User.ANSI_RESET);
                continue;
            }
            if (LocalDate.now().isAfter(user.getTasksList().get(intTemp - 1).getDoBefore())) {
                user.getTasksList().get(intTemp - 1).setOnTime("З запізненням");
                user.getTasksList().get(intTemp - 1).setDoneDate(LocalDate.now());
                user.getTasksList().get(intTemp - 1).setDone(true);
            } else {
                user.getTasksList().get(intTemp - 1).setOnTime("Вчасно");
                user.getTasksList().get(intTemp - 1).setDoneDate(LocalDate.now());
                user.getTasksList().get(intTemp - 1).setDone(true);
            }
            user.setCountDoneTasks(user.getCountDoneTasks() + 1);
        }
        user.writeUserToFile();
    }
}