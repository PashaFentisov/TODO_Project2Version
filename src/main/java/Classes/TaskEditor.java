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

    public void deleteTasksFromFile(User user){
        int intTemp;
        user.getTasksList().clear();
        String temp;
        try {
            user.readUserFromFile();
        }catch(Exception e){
            System.out.println(User.ANSI_RED + "You don`t have any task" + ANSI_RESET);
            return;
        }
        while (true) {
            System.out.println(ANSI_YELLOW + "Поточні таски---------------------------------------------------------------------------------------------------------------" + ANSI_RESET);
            user.showListTasks();
            System.out.print(ANSI_YELLOW + "Введіть номер таску якій хочете видалити\nДля видалення всіх таксів - all\nДля видалення зроблених - DONE\nДля закінчення - stop\nПоле для вводу:" + ANSI_RESET);
            temp = reader.next();
            if (temp.equalsIgnoreCase("stop")) {
                break;
            }
            if (temp.equalsIgnoreCase("all")) {
                System.out.print(User.ANSI_RED + "Всі таски будуть видалені з пам'яті, для підтвердження натисніть enter, для відміни введіть stop: " + ANSI_RESET);
                if (reader.nextLine().equalsIgnoreCase("stop")) {
                    continue;
                }
                user.getTasksList().clear();
                user.setCountAllTasks(0);
                user.writeUserToFile();
                System.out.println(User.ANSI_GREEN + "Всі таски видалено" + ANSI_RESET);
                return;
            }
            if (temp.equalsIgnoreCase("DONE")) {
                System.out.print(User.ANSI_RED + "Всі виконані таски будуть видалені з пам'яті, для підтвердження натисніть enter, для відміни введіть stop: " + ANSI_RESET);
                if (reader.nextLine().equalsIgnoreCase("stop")) {
                    continue;
                }
                for (int i = 0; i < user.getTasksList().size(); i++) {
                    if (user.getTasksList().get(i).getText().contains("DONE")) {
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
                System.out.println(User.ANSI_GREEN + "Всі виконані таски видалено" + ANSI_RESET);
                break;

            } else {
                try {
                    intTemp = Integer.parseInt(temp);
                } catch (Exception e) {
                    break;
                }
                for (int i = 0; i < user.getTasksList().size(); i++) {
                    if (user.getTasksList().get(i).getNumber()==intTemp) {
                        user.getTasksList().remove(i);
                        user.setCountAllTasks(user.getCountAllTasks() - 1);
                        for (int k = 0; k < user.getTasksList().size(); k++) {
                            if (user.getTasksList().get(k).getNumber() > i) {
                                user.getTasksList().get(k).setNumber(user.getTasksList().get(k).getNumber() - 1);
                            }
                        }
                        System.out.println(User.ANSI_GREEN + "Task " + intTemp + " видалено" + ANSI_RESET);
                        break;
                    }
                }
            }
        }
        user.writeUserToFile();
    }

}