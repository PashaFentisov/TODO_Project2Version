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
            try {
                day = reader.nextInt();
                task.setDoBefore(task.getDoBefore().withDayOfMonth(day));
            } catch (Exception e) {
                System.out.println("You entered wrong value, you have 1 day to do this task or edit it");
                task.setDoBefore(task.getDoBefore().withDayOfMonth(LocalDate.now().getDayOfMonth()));
            }
            System.out.print(ANSI_YELLOW + "Введіть місяць: " + ANSI_RESET);
            try {
                month = reader.nextInt();
                task.setDoBefore(task.getDoBefore().withMonth(month));
            } catch (Exception e) {
                System.out.println("You entered wrong value, you have to finish this task this month");
                task.setDoBefore(task.getDoBefore().withMonth(LocalDate.now().getMonthValue()));
            }
            tasksList.add(task);
            user.setCountAllTasks(task.getNumber());
        }
        user.setTasksList(tasksList);
        System.out.println(User.ANSI_GREEN + "These tasks will be saved, if you agree press enter else enter \"edit\"" + ANSI_RESET);
        user.showListTasks();
        if(reader.nextLine().equalsIgnoreCase("edit")){
            editList(user.getTasksList(), user);
        }
        user.writeUserToFile();
        try{
            user.readUserFromFile();
        }catch(Exception e){}
    }

    public void editList(LinkedList<Task> list, User user){
        System.out.print(ANSI_YELLOW + "Bи хочете додати(add) таск чи видалити(delete) таск: " + ANSI_RESET);
        String tempString = reader.next();
        if (tempString.equalsIgnoreCase("add")) {
            user.fillList();
        } else {
            int i;
            while (true) {
                user.showListTasks();
                System.out.print(ANSI_YELLOW + "Який таск за номером ви хочете видалити: " + ANSI_RESET);
                tempString = reader.next();
                try {
                    i = Integer.parseInt(tempString);
                } catch (Exception e) {
                    break;
                }
                for (int j = 0; j < list.size(); j++) {
                    if (list.get(j).getNumber()==i) {
                        list.remove(j);
                        user.setCountAllTasks(user.getCountAllTasks()-1);
                        System.out.println(User.ANSI_GREEN + "Task " + i + " видалено" + User.ANSI_RESET);
                        for (int k = 0; k < list.size(); k++) {
                            if(list.get(k).getNumber()>i){
                                list.get(k).setNumber(list.get(k).getNumber()-1);
                            }
                        }
                    }
                }
            }
        }
    }

}