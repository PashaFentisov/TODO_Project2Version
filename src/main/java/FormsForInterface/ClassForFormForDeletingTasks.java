package FormsForInterface;

import Classes.Task;
import Interfaces.IUser;
import TestClasses.Main;

import javax.swing.*;

public class ClassForFormForDeletingTasks {
    private JPanel MainPanel;
    private JList listOfTasksToDelete;
    private JButton deleteSelectedButton;
    private JButton allTasksButton;
    private JButton backAndSaveButton;
    private JButton doneTasksButton;

    private Main main;

    public ClassForFormForDeletingTasks(Main main) {
        this.main = main;
    }

    public void show(IUser user){
        try{
            user.readUserFromFile();
        }catch(Exception e){}
        listOfTasksToDelete.setListData(user.getTasksList().toArray());
        main.getMainFrame().setContentPane(MainPanel);
        MainPanel.setVisible(true);
        backAndSaveButton.addActionListener((e)->actionForBackButton(user));
        deleteSelectedButton.addActionListener((e)-> actionForDeleteSelectedButton(user));
        allTasksButton.addActionListener((e)->actionForDeleteAllTasksButton(user));
        doneTasksButton.addActionListener((e)->actionForDeleteDoneTasksButton(user));
    }

    private void readUserMethod(IUser user){
        try{
            user.readUserFromFile();
        }catch(Exception e){}
        listOfTasksToDelete.setListData(user.getTasksList().toArray());
    }

    private void actionForDeleteSelectedButton(IUser user){
        if(listOfTasksToDelete.getSelectedValue()==null){
            JOptionPane.showMessageDialog(new JFrame(),"Choose task to delete or choose another option!","Alert",JOptionPane.WARNING_MESSAGE);
        }else if(listOfTasksToDelete.getSelectedValue()!=null){
            user.deleteSelectedTasksFromFile((Task)listOfTasksToDelete.getSelectedValue());
            readUserMethod(user);
        }
    }

    private void actionForDeleteAllTasksButton(IUser user){
        int k = JOptionPane.showConfirmDialog(new JFrame(),"All tasks are gonna be deleted","Confirm changes",JOptionPane.YES_NO_OPTION);
        if(k == JOptionPane.YES_OPTION){
            user.deleteAllTasksFromFile();
            readUserMethod(user);
            actionForBackButton(user);
        }
    }

    private void actionForDeleteDoneTasksButton(IUser user){
        int k = JOptionPane.showConfirmDialog(new JFrame(),"All done tasks are gonna be deleted","Confirm changes",JOptionPane.YES_NO_OPTION);
        if(k == JOptionPane.YES_OPTION){
            user.deleteDoneTasksFromFile();
            readUserMethod(user);
        }
    }
    public void actionForBackButton(IUser user){
        MainPanel.setVisible(false);
        main.showMainPanel();
    }
}
