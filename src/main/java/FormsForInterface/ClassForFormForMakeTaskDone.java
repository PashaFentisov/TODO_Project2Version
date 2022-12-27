package FormsForInterface;

import Classes.Task;
import Interfaces.IUser;
import TestClasses.Main;

import javax.swing.*;
public class ClassForFormForMakeTaskDone {
    private JPanel MainPanel;
    private JList listOfTasksWillBeDone;
    private JButton DONEButton;
    private JButton backButton;
    private JLabel Label;
    private Main main;

    public ClassForFormForMakeTaskDone(Main main) {
        this.main = main;
    }

    public void show(IUser user){
        try{
            user.readUserFromFile();
        }catch(Exception e){}
        listOfTasksWillBeDone.setListData(user.getTasksList().stream().filter((t)->!t.isDone()).toArray());
        main.getMainFrame().setContentPane(MainPanel);
        MainPanel.setVisible(true);
        backButton.addActionListener((e)->actionForBackButton(user));
        DONEButton.addActionListener((e)-> actionForDONEButton(user));
    }

    private void readUserMethod(IUser user){
        try{
            user.readUserFromFile();
        }catch(Exception e){}
        listOfTasksWillBeDone.setListData(user.getTasksList().stream().filter((t)->!t.isDone()).toArray());
    }

    public void actionForDONEButton(IUser user){
        if(listOfTasksWillBeDone.getSelectedValue()==null){
            JOptionPane.showMessageDialog(new JFrame(),"Choose task!","Alert",JOptionPane.WARNING_MESSAGE);
        }else if(listOfTasksWillBeDone.getSelectedValue()!=null){
            user.makeTaskDone((Task)listOfTasksWillBeDone.getSelectedValue());
            readUserMethod(user);
        }
    }
    public void actionForBackButton(IUser user){
        MainPanel.setVisible(false);
        main.showMainPanel();
    }
}
