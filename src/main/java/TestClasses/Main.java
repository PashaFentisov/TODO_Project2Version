package TestClasses;

import Classes.User;
import FormsForInterface.ClassForFormForDeletingTasks;
import FormsForInterface.ClassForFormForFillingListWithTasks;
import FormsForInterface.ClassForFormForMakeTaskDone;
import Interfaces.IUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Pasha Fentisov
 * @version 3.0
 * @since 31.12.22
 */

public class Main extends WindowAdapter {
    private IUser user;
    private JPanel MainPanel;
    private JRadioButton sixthOption;
    private JRadioButton firstOption;
    private JRadioButton fifthOption;
    private JRadioButton fourthOption;
    private JRadioButton thirdOption;
    private JRadioButton secondOption;
    private JButton apply;
    private JButton exitButton;

    private JFrame mainFrame;

    public JFrame getMainFrame() {
        return mainFrame;
    }


    /**
     * При закритті додатка, з'явиться вікно з попередженням.
     *
     * @param e the event to be processed
     */
    public void windowClosing(WindowEvent e) {
        int a = JOptionPane.showConfirmDialog(mainFrame, "Don`t forget to save changes!\n                   Close?", "Confirm closing", 0);
        user.writeUserToFile();
        if (a == JOptionPane.YES_OPTION) {
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } else if (a == JOptionPane.NO_OPTION) {
            mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
    }

    /**
     * Конструктор класа Main, виконується на початку, для початкових налаштувань вікна.
     */
    public Main() {
        apply.setBackground(Color.lightGray);
        user = User.getInstance();
        try{
            user.readUserFromFile();
        }catch(Exception e){}
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension d = toolkit.getScreenSize();
        mainFrame = new JFrame("TODO Holder");
        mainFrame.addWindowListener(this);
        mainFrame.setVisible(true);
        mainFrame.setBounds(d.width / 2 - 450, d.height / 2 - 250, 900, 400);
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
        exitButton.addActionListener((r) -> {
            user.writeUserToFile();
            System.exit(0);
        });
    }

    /**
     * Викликається для переходу на головне вікно.
     */
    public void showMainPanel() {
        mainFrame.setContentPane(MainPanel);
        MainPanel.setVisible(true);
    }


    /**
     * При виборі потрібної опції викликається метод який її виконує і відкривається нове вікно.
     *
     * @param main поточний об'єкт Main класа
     */
    private void actionForButtonApply(Main main) {
        apply.setBackground(Color.lightGray);
        MainPanel.setVisible(false);
        if (firstOption.isSelected()) {
            new ClassForFormForFillingListWithTasks(this).show(user);
        } else if (secondOption.isSelected()) {
            user.showTasksInFile(main);
        } else if (thirdOption.isSelected()) {
            new ClassForFormForMakeTaskDone(this).show(user);
        } else if (fourthOption.isSelected()) {
            user.showDoneTasks(main);
        } else if (fifthOption.isSelected()) {
            user.showTasksInProgress(main);
        } else if (sixthOption.isSelected()) {
            new ClassForFormForDeletingTasks(this).show(user);
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
