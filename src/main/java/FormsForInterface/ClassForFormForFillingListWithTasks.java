package FormsForInterface;

import Classes.Task;
import Interfaces.IUser;
import TestClasses.Main;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class ClassForFormForFillingListWithTasks {
    private JPanel MainPanel;
    private JTextField TextFieldForTextOfTask;
    private JComboBox DayComboBox;
    private JComboBox MonthComboBox;
    private JLabel EnterTaskLabel;
    private JLabel EnterMainDateLabel;
    private JLabel MonthLabel;
    private JLabel DayLabel;
    private JButton addTaskButton;
    private JButton backButton;

    private Main main;

    public ClassForFormForFillingListWithTasks(Main main) {
        this.main = main;
    }

    public void show(IUser user){
        try{
            user.readUserFromFile();
        }catch(Exception e){}
        main.getMainFrame().setContentPane(MainPanel);
        MainPanel.setVisible(true);
        backButton.addActionListener((e)->actionForBackButton(user));
        addTaskButton.addActionListener((e)-> actionForAddTaskButton(user));
        MonthComboBox.addActionListener((e)->ActionWhenMonthIsChosen());
        addTaskButton.setBackground(Color.GREEN);
        workingMethod();
    }
    public void ActionWhenMonthIsChosen(){
        DayComboBox.removeAllItems();
        LocalDate d = LocalDate.now().withMonth((Integer)MonthComboBox.getSelectedItem());
        for (int i = 1; i <= d.getMonth().length(LocalDate.now().isLeapYear()); i++) {
            DayComboBox.addItem(i);
        }
    }

    public void workingMethod(){
        TextFieldForTextOfTask.setText("");
        for (int i = 1; i <= 12; i++) {
            MonthComboBox.addItem(i);
        }
        MonthComboBox.setSelectedIndex(0);
        DayComboBox.removeAllItems();
    }
    public void actionForAddTaskButton(IUser user){
        String tempTextOfTask = TextFieldForTextOfTask.getText();
        Integer tempSelectedDay = (Integer) DayComboBox.getSelectedItem();
        Integer tempSelectedMonth = (Integer) MonthComboBox.getSelectedItem();
        if(tempTextOfTask.isEmpty()){
            JOptionPane.showMessageDialog(new JFrame(),"Enter text for task!","Alert",JOptionPane.WARNING_MESSAGE);
        } else if(tempSelectedDay==null){
            JOptionPane.showMessageDialog(new JFrame(),"Enter day!","Alert",JOptionPane.WARNING_MESSAGE);
        } else {
            Task s = user.fillList(tempTextOfTask, tempSelectedDay, tempSelectedMonth);
            int a = JOptionPane.showConfirmDialog(new Frame(),("This task will be added:\n" + s),"Confirm changes", 0);
            if(a==JOptionPane.YES_OPTION){
                workingMethod();
            } else if(a==JOptionPane.NO_OPTION){
               user.getTasksList().remove(s);
               if(s.isDone()){
                   user.setCountDoneTasks(user.getCountDoneTasks()-1);
               }
               user.setCountAllTasks(user.getCountAllTasks()-1);
               Task.setCountOfTasks(Task.getCountOfTasks()-1);
            }
        }
    }
    public void actionForBackButton(IUser user){
        user.writeUserToFile();
        try {
            user.readUserFromFile();
        } catch (Exception e) {
        }
        MainPanel.setVisible(false);
        main.showMainPanel();
    }
}
