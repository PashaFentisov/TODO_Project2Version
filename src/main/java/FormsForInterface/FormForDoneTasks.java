package FormsForInterface;

import TestClasses.Main;

import javax.swing.*;
import java.awt.*;

public class FormForDoneTasks {
    private JPanel MainPanel;
    private JButton closeButton;
    private JList listOdDoneTasks;
    private JTextField madeOnTime;
    private JTextField madeNotOnTime;

    public void show(Object[] arr, String onTime, String notOnTime, Main main){
        main.getMainFrame().setContentPane(MainPanel);
        MainPanel.setVisible(true);
        closeButton.addActionListener((e)->actionForButtonClose(main));
        listOdDoneTasks.setListData(arr);
        listOdDoneTasks.setForeground(Color.GREEN);
        listOdDoneTasks.setBackground(Color.DARK_GRAY);
        madeOnTime.setText(onTime);
        madeNotOnTime.setText(notOnTime);
    }

    public void actionForButtonClose(Main m){
        MainPanel.setVisible(false);
        m.showMainPanel();
    }
}
