package Classes;

import Interfaces.IOwnReader;
import Interfaces.ITaskEditor;

import java.time.LocalDate;
import java.util.LinkedList;

public class TaskEditor implements ITaskEditor {
    IOwnReader reader;

    public TaskEditor(IOwnReader reader) {
        this.reader = reader;
    }

    public TaskEditor() {}

    @Override
    public void fillList(User user, Task task, LinkedList<Task> tasksList) {
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

    @Override
    public void editList(LinkedList<Task> list, User user) {
        System.out.print(User.ANSI_YELLOW + "Bи хочете додати(add) таск чи видалити(del) таск: " + User.ANSI_RESET);
        String tempString = reader.next();
        if (tempString.equalsIgnoreCase("add")) {
            user.fillList();
        } else {
            int i;
            while (true) {
                user.showListTasks();
                System.out.print(User.ANSI_YELLOW + "Який таск за номером ви хочете видалити: " + User.ANSI_RESET);
                tempString = reader.next();
                try {
                    i = Integer.parseInt(tempString);
                } catch (Exception e) {
                    System.out.println(User.ANSI_RED + "You entered wrong value editing is finished" + User.ANSI_RESET);
                    break;
                }
                for (int j = 0; j < list.size(); j++) {
                    if (list.get(j).getNumber() == i) {
                        list.remove(j);
                        user.setCountAllTasks(user.getCountAllTasks() - 1);
                        if (list.get(j).isDone()) {
                            user.setCountDoneTasks(user.getCountDoneTasks() - 1);
                        }
                        System.out.println(User.ANSI_GREEN + "Task " + i + " видалено" + User.ANSI_RESET);
                        for (int k = 0; k < list.size(); k++) {
                            if (list.get(k).getNumber() > i) {
                                list.get(k).setNumber(list.get(k).getNumber() - 1);
                            }
                        }
                    }
                }
            }
        }
    }

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
                user.getTasksList().remove(i);
                user.setCountAllTasks(user.getCountAllTasks() - 1);
                if (user.getTasksList().get(i).isDone()) {
                    user.setCountDoneTasks(user.getCountDoneTasks() - 1);
                }
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