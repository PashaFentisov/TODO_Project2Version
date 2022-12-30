package FormsForInterface;

import Interfaces.IFormForDoneTasks;
import Interfaces.IUser;
import TestClasses.Main;

import javax.swing.*;
import java.awt.*;

public class FormForDoneTasks implements IFormForDoneTasks {
    private JPanel MainPanel;
    private JButton closeButton;
    private JList listOdDoneTasks;
    private JTextField madeOnTime;
    private JTextField madeNotOnTime;


    /**
     * Метод викликається в {@link Classes.TaskShower#showDoneTasks(IUser, Main)}
     * В listOdDoneTasks поміщається масив таксів у яких поле isDone = true
     * Текстове поле madeOnTime отримує значення з onTime.
     * Текстове поле madeNotOnTime отримує значення з notOnTime.
     * Встановлює своє вікно як поточне.
     * Задає дії для кнопок.
     *
     * @param arr       Масив таксів у яких поле isDone = true
     * @param onTime    текст з кількістю тасків виконаних вчасно
     * @param notOnTime текст з кількістю тасків виконаних не вчасно
     * @param main      Поточний об'єкт класа Main
     */
    @Override
    public void show(Object[] arr, String onTime, String notOnTime, Main main) {
        main.getMainFrame().setContentPane(MainPanel);
        MainPanel.setVisible(true);
        closeButton.addActionListener((e) -> actionForButtonClose(main));
        listOdDoneTasks.setListData(arr);
        listOdDoneTasks.setForeground(Color.GREEN);
        listOdDoneTasks.setBackground(Color.DARK_GRAY);
        madeOnTime.setText(onTime);
        madeNotOnTime.setText(notOnTime);
    }

    /**
     * Метод який спрацьовує після натиснення на кнопку back.
     * Для MainPanel setVisible встановлюється як false і викликається метод {@link Main#showMainPanel()}
     */
    private void actionForButtonClose(Main m) {
        MainPanel.setVisible(false);
        m.showMainPanel();
    }
}
