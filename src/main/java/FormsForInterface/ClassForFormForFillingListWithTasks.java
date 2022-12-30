package FormsForInterface;

import Classes.Task;
import Interfaces.IClassForFormForFillingListWithTasks;
import Interfaces.IUser;
import TestClasses.Main;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ClassForFormForFillingListWithTasks implements IClassForFormForFillingListWithTasks {
    private JPanel MainPanel;
    private JTextField TextFieldForTextOfTask;
    private JComboBox DayComboBox;
    private JComboBox MonthComboBox;
    private JLabel EnterTaskLabel;
    private JLabel EnterMainDateLabel;
    private JLabel MonthLabel;
    private JLabel DayLabel;
    private JButton addTaskButton;
    private JButton backButton;

    private Main main;

    public ClassForFormForFillingListWithTasks(Main main) {
        this.main = main;
    }

    /**
     * Метод викликається після вибору в головному вікні опції: Видалити таск.
     * Зчитує user з файлу.
     * Встановлює своє вікно як поточне.
     * Задає дії для кнопок.
     * Викликає метод {@link #workingMethod()}
     *
     * @param user Поточний об'єкт класа User
     */
    @Override
    public void show(IUser user) {
        try {
            user.readUserFromFile();
        } catch (Exception e) {
        }
        main.getMainFrame().setContentPane(MainPanel);
        MainPanel.setVisible(true);
        backButton.addActionListener((e) -> actionForBackButton(user));
        addTaskButton.addActionListener((e) -> actionForAddTaskButton(user));
        MonthComboBox.addActionListener((e) -> ActionWhenMonthIsChosen());
        addTaskButton.setBackground(Color.GREEN);
        workingMethod();
    }

    /**
     * Службовий метод який спрацьовує коли користувач обрав значення для MonthComboBox.
     * DayComboBox - ініціалізується значеннями від 1 до останнього дня вибраного місяця.
     */
    private void ActionWhenMonthIsChosen() {
        DayComboBox.removeAllItems();
        LocalDate d = LocalDate.now().withMonth((Integer) MonthComboBox.getSelectedItem());
        for (int i = 1; i <= d.getMonth().length(LocalDate.now().isLeapYear()); i++) {
            DayComboBox.addItem(i);
        }
    }

    /**
     * Службовий метод для ініціалізації полей початковими значеннями.
     * TextFieldForTextOfTask - встановлюється як пусте текстове поле.
     * MonthComboBox - заповнюється значеннями від 1 до 12 (початкове вибране значення 1).
     * DayComboBox - очищається від будь яких значень.
     */
    private void workingMethod() {
        TextFieldForTextOfTask.setText("");
        for (int i = 1; i <= 12; i++) {
            MonthComboBox.addItem(i);
        }
        MonthComboBox.setSelectedIndex(0);
        DayComboBox.removeAllItems();
    }

    /**
     * Метод який спрацьовує після натиснення на кнопку Додати таск
     * tempTextOfTask, tempSelectedDay, tempSelectedMonth - ініціалізуються отриманими з графічного інтерфейсу значеннями,
     * і передаємо їх у метод {@link Classes.TaskEditor#fillList(IUser, Task, List, String, Integer, Integer)}
     * для створення таска і додавання його до списку.
     * Якщо будь яке з полей пусте, з'являється вікно з попередженням яке не дозволяє створити таск поки поля не будуть ініціалізовані.
     * Після створення таску з'являється вікно для уточнення чи користувач підтверджує створення таску,
     * якщо так то викликається {@link #workingMethod()}, якщо ні створений раніше таск видаляється з списку,
     * setCountAllTasks у user зменшується на 1
     * setCountOfTasks у Task зменшується на 1
     *
     * @param user Поточний об'єкт класа User
     */
    private void actionForAddTaskButton(IUser user) {
        String tempTextOfTask = TextFieldForTextOfTask.getText();
        Integer tempSelectedDay = (Integer) DayComboBox.getSelectedItem();
        Integer tempSelectedMonth = (Integer) MonthComboBox.getSelectedItem();
        if (tempTextOfTask.isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Enter text for task!", "Alert", JOptionPane.WARNING_MESSAGE);
        } else if (tempSelectedDay == null) {
            JOptionPane.showMessageDialog(new JFrame(), "Enter day!", "Alert", JOptionPane.WARNING_MESSAGE);
        } else {
            Task s = user.fillList(tempTextOfTask, tempSelectedDay, tempSelectedMonth);
            int a = JOptionPane.showConfirmDialog(new Frame(), ("This task will be added:\n" + s), "Confirm changes", 0);
            if (a == JOptionPane.YES_OPTION) {
                workingMethod();
            } else if (a == JOptionPane.NO_OPTION) {
                user.getTasksList().remove(s);
                if (s.isDone()) {
                    user.setCountDoneTasks(user.getCountDoneTasks() - 1);
                }
                user.setCountAllTasks(user.getCountAllTasks() - 1);
                Task.setCountOfTasks(Task.getCountOfTasks() - 1);
            }
        }
    }

    /**
     * Метод який спрацьовує після натиснення на кнопку back.
     * Записує і зчитує об'єкт класа User з файлу.
     * Для MainPanel setVisible встановлюється як false і викликається метод {@link Main#showMainPanel()}
     */
    private void actionForBackButton(IUser user) {
        user.writeUserToFile();
        try {
            user.readUserFromFile();
        } catch (Exception e) {
        }
        MainPanel.setVisible(false);
        main.showMainPanel();
    }
}
