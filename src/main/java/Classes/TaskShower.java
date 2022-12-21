package Classes;

import Interfaces.ITaskShower;

public class TaskShower implements ITaskShower {

    public TaskShower() {
    }

    public void showListTasks(User user){
        System.out.println(User.ANSI_GREEN + "Yours tasks" + User.ANSI_RESET);
        user.getTasksList().forEach(System.out::println);
    }

    public void showTasksInFile(User user){
        try{
            user.readUserFromFile();
        }catch(Exception e){
            System.out.println(User.ANSI_RED + "Your file is empty" + User.ANSI_RESET);
            return;
        }
        System.out.println(User.ANSI_GREEN + "Yours tasks" + User.ANSI_RESET);
        user.getTasksList().forEach(System.out::println);
    }

    public void showDoneTasks(User user){
        int countOnTime = 0;
        System.out.println(User.ANSI_GREEN + "Виконанні таски " + User.ANSI_RESET);
        user.getTasksList().clear();
        try{
            user.readUserFromFile();
        }catch(Exception e){
            System.out.println(User.ANSI_RED + "Your file is empty" + User.ANSI_RESET);
            return;
        }
        countOnTime = (int) user.getTasksList().stream().filter(Task::isDone).filter(t -> t.getOnTime().contains("Вчасно")).count();
        user.getTasksList().stream().filter(Task::isDone).forEach(System.out::println);
        System.out.println(User.ANSI_YELLOW + "\nВиконано вчасно: " + countOnTime);
        System.out.println("Виконано не вчасно: " + (user.getCountDoneTasks() - countOnTime) + User.ANSI_RESET);
        user.getTasksList().clear();
    }
}
