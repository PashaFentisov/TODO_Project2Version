package FormsForInterface;

import TestClasses.Main;

import javax.swing.*;
import java.awt.*;

public class ClassForFormShowingTasksInProgress {
    private JPanel MainPanel;
    private JList listOfTasksInProgress;
    private JTextField WithActualDeadline;
    private JTextField TextWithMissedDeadline;
    private JButton backButton;
    private JLabel Label;

    public void show(Main main, Object[] arr, String countNotOnTime, String countOnTime){
        main.getMainFrame().setContentPane(MainPanel);
        MainPanel.setVisible(true);
        backButton.addActionListener((e)->actionForBackButton(main));
        listOfTasksInProgress.setListData(arr);
        listOfTasksInProgress.setForeground(Color.GREEN);
        listOfTasksInProgress.setBackground(Color.DARK_GRAY);
        WithActualDeadline.setText(countOnTime);
        TextWithMissedDeadline.setText(countNotOnTime);
    }

    public void actionForBackButton(Main m){
        MainPanel.setVisible(false);
        m.showMainPanel();
    }
}
