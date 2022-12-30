package FormsForInterface;

import Interfaces.IClassForFormShowingTasksInProgress;
import Interfaces.IUser;
import TestClasses.Main;

import javax.swing.*;
import java.awt.*;

public class ClassForFormShowingTasksInProgress implements IClassForFormShowingTasksInProgress {
    private JPanel MainPanel;
    private JList listOfTasksInProgress;
    private JTextField WithActualDeadline;
    private JTextField TextWithMissedDeadline;
    private JButton backButton;

    /**
     * Метод викликається в {@link Classes.TaskShower#showTasksInProgress(IUser, Main)}
     * В listOfTasksInProgress поміщається масив таксів у яких поле isDone = false
     * Текстове поле WithActualDeadline отримує значення з countOnTime.
     * Текстове поле TextWithMissedDeadline отримує значення з countNotOnTime.
     * Встановлює своє вікно як поточне.
     * Задає дії для кнопок.
     *
     * @param main           Поточний об'єкт класа Main
     * @param arr            Масив таксів у яких поле isDone = false
     * @param countNotOnTime текст з кількістю тасків з пропущеним строком виконання
     * @param countOnTime    текст з кількістю тасків з актуальним строком виконання
     */
    @Override
    public void show(Main main, Object[] arr, String countNotOnTime, String countOnTime) {
        main.getMainFrame().setContentPane(MainPanel);
        MainPanel.setVisible(true);
        backButton.addActionListener((e) -> actionForBackButton(main));
        listOfTasksInProgress.setListData(arr);
        listOfTasksInProgress.setForeground(Color.RED);
        listOfTasksInProgress.setBackground(Color.DARK_GRAY);
        WithActualDeadline.setText(countOnTime);
        TextWithMissedDeadline.setText(countNotOnTime);
    }

    /**
     * Метод який спрацьовує після натиснення на кнопку back.
     * Для MainPanel setVisible встановлюється як false і викликається метод {@link Main#showMainPanel()}
     */
    private void actionForBackButton(Main m) {
        MainPanel.setVisible(false);
        m.showMainPanel();
    }
}
