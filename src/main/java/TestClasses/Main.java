package TestClasses;

import Classes.User;
import Interfaces.IUser;

import javax.swing.*;
import java.awt.*;

/**
 * @author Pasha Fentisov
 * @version 2.0
 * @since 22.19.22
 */

public class Main {
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

    public Main() {
        apply.setBackground(Color.lightGray);
        user = User.getInstance();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension d = toolkit.getScreenSize();
        mainFrame = new JFrame("TODO Holder");
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
        apply.setBackground(Color.lightGray);
        MainPanel.setVisible(false);
        if (firstOption.isSelected()) {
            user.fillList();
        } else if (secondOption.isSelected()) {
            user.showTasksInFile(main);
        } else if (thirdOption.isSelected()) {
            user.makeTaskDone();
        } else if (fourthOption.isSelected()) {
            user.showDoneTasks(main);
        } else if (fifthOption.isSelected()) {
            user.showTasksInProgress();
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
