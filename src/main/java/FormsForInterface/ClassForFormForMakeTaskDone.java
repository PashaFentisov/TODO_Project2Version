package FormsForInterface;

import Classes.Task;
import Interfaces.IClassForFormForMakeTaskDone;
import Interfaces.IUser;
import TestClasses.Main;

import javax.swing.*;

public class ClassForFormForMakeTaskDone implements IClassForFormForMakeTaskDone {
    private JPanel MainPanel;
    private JList listOfTasksWillBeDone;
    private JButton DONEButton;
    private JButton backButton;
    private Main main;

    public ClassForFormForMakeTaskDone(Main main) {
        this.main = main;
    }

    /**
     * Метод викликається після вибору в головному вікні опції: Помітити таск як виконаний.
     * В listOfTasksWillBeDone додає таски у яких поле isDone = false.
     * Встановлює своє вікно як поточне.
     * Задає дії для кнопок.
     *
     * @param user Поточний об'єкт класа User
     */
    @Override
    public void show(IUser user) {
        listOfTasksWillBeDone.setListData(user.getTasksList().stream().filter((t) -> !t.isDone()).toArray());
        main.getMainFrame().setContentPane(MainPanel);
        MainPanel.setVisible(true);
        backButton.addActionListener((e) -> actionForBackButton());
        DONEButton.addActionListener((e) -> actionForDONEButton(user));
    }

    /**
     * Службовий метод, задає оновлений список випадаючому списку в графічному інтерфейсі.
     *
     * @param user Поточний об'єкт класа User
     */
    private void readUserMethod(IUser user) {
        listOfTasksWillBeDone.setListData(user.getTasksList().stream().filter((t) -> !t.isDone()).toArray());
    }

    /**
     * Метод який спрацьовує після натиснення на кнопку DONE.
     * Якщо не вибрано ніякий таск який має бути помічений як DONE з'явиться вікно з попередженням,
     * інакше буде викликано метод {@link Classes.User#makeTaskDone(Task)}.
     * Після цього обраний таск буде помічено як DONE і спрацює службовий метод {@link #readUserMethod(IUser)}.
     *
     * @param user Поточний об'єкт класа User
     */
    private void actionForDONEButton(IUser user) {
        if (listOfTasksWillBeDone.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(new JFrame(), "Choose task!", "Alert", JOptionPane.WARNING_MESSAGE);
        } else if (listOfTasksWillBeDone.getSelectedValue() != null) {
            user.makeTaskDone((Task) listOfTasksWillBeDone.getSelectedValue());
            readUserMethod(user);
        }
    }

    /**
     * Метод який спрацьовує після натиснення на кнопку back.
     * Для MainPanel setVisible встановлюється як false і викликається метод {@link Main#showMainPanel()}
     */
    private void actionForBackButton() {
        MainPanel.setVisible(false);
        main.showMainPanel();
    }
}
