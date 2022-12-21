package Classes;

import Interfaces.ITaskShower;

import java.time.LocalDate;

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
        try{
            user.readUserFromFile();
        }catch(Exception e){
            System.out.println(User.ANSI_RED + "Your file is empty" + User.ANSI_RESET);
            return;
        }
        countOnTime = (int) user.getTasksList().stream().filter(Task::isDone).filter(t -> t.getOnTime().equalsIgnoreCase("Вчасно")).count();
        user.getTasksList().stream().filter(Task::isDone).forEach(System.out::println);
        System.out.println(User.ANSI_YELLOW + "\nВиконано вчасно: " + countOnTime);
        System.out.println("Виконано не вчасно: " + (user.getCountDoneTasks() - countOnTime) + User.ANSI_RESET);
        user.getTasksList().clear();
    }

    public void showTasksInProgress(User user){  //TODO виводити час до дедлайна
        int countTime;
        System.out.println(User.ANSI_RED + "\nНе виконанні завдання" + User.ANSI_RESET);
        try{
            user.readUserFromFile();
        }catch(Exception e){
            System.out.println(User.ANSI_RED + "Your file is empty" + User.ANSI_RESET);
            return;
        }
        user.getTasksList().stream().filter(t ->!t.isDone()).forEach(System.out::println);
        countTime = (int) user.getTasksList().stream().filter(t ->!t.isDone()).map(Task::getDoBefore).filter(t->t.isBefore(LocalDate.now())).count();
        if (countTime == 0) {
            System.out.println(User.ANSI_GREEN +"0 - З пропущеним строком виконання" + User.ANSI_RESET);
        } else {
            System.out.println(User.ANSI_RED + countTime + " - З пропущеним строком виконання" + User.ANSI_RESET);
        }
        countTime = (int)  user.getTasksList().stream().filter(t->!t.isDone()).count() - countTime;
        System.out.println(User.ANSI_GREEN + countTime + " - З актуальним строком виконання" + User.ANSI_RESET);
        user.getTasksList().clear();
    }
}
