package FormsForInterface;

import Interfaces.IFormForShowTaskClass;
import Interfaces.IUser;
import TestClasses.Main;

import javax.swing.*;

public class FormForShowTaskClass implements IFormForShowTaskClass {
    private JPanel showPanel;
    private JList ListOfTasks;
    private JButton closeButton;

    /**
     * Метод викликається в {@link Classes.TaskShower#showTasksInFile(IUser, Main)}
     * В ListOfTasks поміщається отриманий масив всіх тасків.
     * Встановлює своє вікно як поточне.
     * Задає дії для кнопок.
     *
     * @param arr  Масив всіх таксів
     * @param main Поточний об'єкт класа Main
     */
    @Override
    public void show(Object[] arr, Main main) {
        main.getMainFrame().setContentPane(showPanel);
        showPanel.setVisible(true);
        closeButton.addActionListener((e) -> actionForButtonClose(main));
        ListOfTasks.setListData(arr);
    }

    /**
     * Метод який спрацьовує після натиснення на кнопку back.
     * Для MainPanel setVisible встановлюється як false і викликається метод {@link Main#showMainPanel()}
     */
    private void actionForButtonClose(Main m) {
        showPanel.setVisible(false);
        m.showMainPanel();
    }
}
