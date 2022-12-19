package Classes;

import Interfaces.ITaskEditor;
import ServiceClasses.OwnReader;
import java.time.LocalDate;
import java.util.LinkedList;

import static Classes.User.ANSI_RESET;
import static Classes.User.ANSI_YELLOW;
public class TaskEditor implements ITaskEditor {
    OwnReader reader;

    public TaskEditor(OwnReader reader) {
        this.reader = reader;
    }
    public TaskEditor() {
    }

    public void fillList(User user, Task task, LinkedList<Task> tasksList) {
        String tempStr;
        int day;
        int month;
        while (true) {
            System.out.print(ANSI_YELLOW + "Введіть таск який хочете додати до файлу, або stop: " + ANSI_RESET);
            tempStr = reader.nextLine();
            if (tempStr.equalsIgnoreCase("stop")) {
                break;
            }
            task = new Task(tempStr);
            System.out.println(ANSI_YELLOW + "\nВведіть дату до якої треба виконати таск" + ANSI_RESET);
            System.out.print(ANSI_YELLOW + "Введіть день: " + ANSI_RESET);
            day = reader.nextInt();
            try {
                task.setDoBefore(task.getDoBefore().withDayOfMonth(day));
            } catch (Exception e) {
                System.out.println("You entered wrong value, you have 1 day to do this task or edit it");
                task.setDoBefore(task.getDoBefore().withDayOfMonth(LocalDate.now().getDayOfMonth()));
            }
            System.out.print(ANSI_YELLOW + "Введіть місяць: " + ANSI_RESET);
            month = reader.nextInt();
            try {
                task.setDoBefore(task.getDoBefore().withMonth(month));
            } catch (Exception e) {
                System.out.println("You entered wrong value, you have to finish this task this month");
                task.setDoBefore(task.getDoBefore().withMonth(LocalDate.now().getMonthValue()));
            }
            tasksList.add(task);
            user.setCountAllTasks(task.getNumber());
        }
//        addTasksToFile();
    }

}