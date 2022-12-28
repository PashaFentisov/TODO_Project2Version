package FormsForInterface;

import TestClasses.Main;

import javax.swing.*;

public class FormForShowTaskClass {
    private JPanel showPanel;
    private JList ListOfTasks;
    private JButton closeButton;

    public void show(Object[] arr, Main main) {
        main.getMainFrame().setContentPane(showPanel);
        showPanel.setVisible(true);
        closeButton.addActionListener((e) -> actionForButtonClose(main));
        ListOfTasks.setListData(arr);
    }

    public void actionForButtonClose(Main m) {
        showPanel.setVisible(false);
        m.showMainPanel();
    }
}
