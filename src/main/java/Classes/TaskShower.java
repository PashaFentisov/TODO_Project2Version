package Classes;

import Interfaces.ITaskShower;

public class TaskShower implements ITaskShower {

    public TaskShower() {
    }

    public void showListTasks(User user){
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
}
