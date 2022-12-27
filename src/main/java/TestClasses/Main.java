package TestClasses;

import Classes.User;
import FormsForInterface.ClassForFormForFillingListWithTasks;
import Interfaces.IUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Pasha Fentisov
 * @version 2.0
 * @since 22.19.22
 */

public class Main extends WindowAdapter {
    IUser user;
    private static boolean exit;
    private JPanel MainPanel;
    private JRadioButton sixthOption;
    private JRadioButton firstOption;
    private JRadioButton fifthOption;
    private JRadioButton fourthOption;
    private JRadioButton thirdOption;
    private JRadioButton secondOption;
    private JButton apply;
    private JLabel ChooseOption;
    private JButton exitButton;

    private JFrame mainFrame;

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public void windowClosing(WindowEvent e) {
        int a=JOptionPane.showConfirmDialog(mainFrame,"Don`t forget to save changes!\n                   Close?", "Confirm closing", 0);
        if(a==JOptionPane.YES_OPTION){
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }else if(a == JOptionPane.NO_OPTION){
            mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
    }
    public Main() {
        apply.setBackground(Color.lightGray);
        user = User.getInstance();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension d = toolkit.getScreenSize();
        mainFrame = new JFrame("TODO Holder");
        mainFrame.addWindowListener(this);
        mainFrame.setVisible(true);
        mainFrame.setBounds(d.width / 2 - 400, d.height / 2 - 250, 800, 400);
        mainFrame.setContentPane(MainPanel);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ButtonGroup group = new ButtonGroup();
        group.add(firstOption);
        group.add(secondOption);
        group.add(thirdOption);
        group.add(fourthOption);
        group.add(fifthOption);
        group.add(sixthOption);
        apply.addActionListener((e) -> actionForButtonApply(this));
        exitButton.addActionListener((r) -> System.exit(0));
    }

    public void showMainPanel() {
        mainFrame.setContentPane(MainPanel);
        MainPanel.setVisible(true);
    }


    private void actionForButtonApply(Main main) {
        ClassForFormForFillingListWithTasks classForFormForFillingListWithTasks = new ClassForFormForFillingListWithTasks(main);
        apply.setBackground(Color.lightGray);
        MainPanel.setVisible(false);
        if (firstOption.isSelected()) {
            classForFormForFillingListWithTasks.show(user);
        } else if (secondOption.isSelected()) {
            user.showTasksInFile(main);
        } else if (thirdOption.isSelected()) {
            user.makeTaskDone();
        } else if (fourthOption.isSelected()) {
            user.showDoneTasks(main);
        } else if (fifthOption.isSelected()) {
            user.showTasksInProgress(main);
        } else if (sixthOption.isSelected()) {
            user.deleteTasksFromFile();
        } else {
            MainPanel.setVisible(true);
            apply.setBackground(Color.red);
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.showMainPanel();
    }
}
