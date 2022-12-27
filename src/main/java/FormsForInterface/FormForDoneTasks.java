package FormsForInterface;

import TestClasses.Main;

import javax.swing.*;

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
        madeOnTime.setText(onTime);
        madeNotOnTime.setText(notOnTime);
    }

    public void actionForButtonClose(Main m){
        closeButton.setVisible(false);
        m.showMainPanel();
    }
}
