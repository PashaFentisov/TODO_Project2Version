package FormsForInterface;

import Classes.Task;
import Classes.User;
import Interfaces.IClassForFormForDeletingTasks;
import Interfaces.IUser;
import TestClasses.Main;

import javax.swing.*;

public class ClassForFormForDeletingTasks implements IClassForFormForDeletingTasks {
    private JPanel MainPanel;
    private JList listOfTasksToDelete;
    private JButton deleteSelectedButton;
    private JButton allTasksButton;
    private JButton backAndSaveButton;
    private JButton doneTasksButton;

    private Main main;

    public ClassForFormForDeletingTasks(Main main) {
        this.main = main;
    }

    /**
     * Метод викликається після вибору в головному вікні опції: Додати таск.
     * Зчитує user з файлу.
     * Встановлює своє вікно як поточне.
     * Задає дії для кнопок.
     * В listOfTasksToDelete додає список тасків з зчитаного user
     *
     * @param user Поточний об'єкт класа User
     */
    @Override
    public void show(IUser user) {
        try {
            user.readUserFromFile();
        } catch (Exception e) {
        }
        listOfTasksToDelete.setListData(user.getTasksList().toArray());
        main.getMainFrame().setContentPane(MainPanel);
        MainPanel.setVisible(true);
        backAndSaveButton.addActionListener((e) -> actionForBackButton());
        deleteSelectedButton.addActionListener((e) -> actionForDeleteSelectedButton(user));
        allTasksButton.addActionListener((e) -> actionForDeleteAllTasksButton(user));
        doneTasksButton.addActionListener((e) -> actionForDeleteDoneTasksButton(user));
    }

    /**
     * Службовий метод, зчитує user з файлу і задає його список випадаючому списку в графічному інтерфейсі.
     *
     * @param user Поточний об'єкт класа User
     */
    private void readUserMethod(IUser user) {
        try {
            user.readUserFromFile();
        } catch (Exception e) {
        }
        listOfTasksToDelete.setListData(user.getTasksList().toArray());
    }

    /**
     * Метод який спрацьовує після натиснення на кнопку deleteSelected.
     * Якщо не вибрано нічого що буде видалено з'явиться вікно з попередженням,
     * інакше буде викликано метод {@link Classes.User#deleteSelectedTasksFromFile(Task)}.
     * Після цього обраний таск видалиться і спрацює службовий метод {@link #readUserMethod(IUser)}.
     *
     * @param user Поточний об'єкт класа User
     */
    private void actionForDeleteSelectedButton(IUser user) {
        if (listOfTasksToDelete.getSelectedValue() == null) {
            JOptionPane.showMessageDialog(new JFrame(), "Choose task to delete or choose another option!", "Alert", JOptionPane.WARNING_MESSAGE);
        } else if (listOfTasksToDelete.getSelectedValue() != null) {
            user.deleteSelectedTasksFromFile((Task) listOfTasksToDelete.getSelectedValue());
            readUserMethod(user);
        }
    }

    /**
     * Метод який спрацьовує після натиснення на кнопку all tasks.
     * З'явиться вікно з попередженням, при підтвердженні викликається метод {@link User#deleteAllTasksFromFile()},
     * інакше дія буде проігнорована.
     * Після цього спрацює службовий метод {@link #readUserMethod(IUser)}, і метод для кнопки back {@link #actionForBackButton()}.
     *
     * @param user Поточний об'єкт класа User
     */
    private void actionForDeleteAllTasksButton(IUser user) {
        int k = JOptionPane.showConfirmDialog(new JFrame(), "All tasks are gonna be deleted", "Confirm changes", JOptionPane.YES_NO_OPTION);
        if (k == JOptionPane.YES_OPTION) {
            user.deleteAllTasksFromFile();
            readUserMethod(user);
            actionForBackButton();
        }
    }

    /**
     * Метод який спрацьовує після натиснення на кнопку Done tasks.
     * З'явиться вікно з попередженням, при підтвердженні викликається метод {@link User#deleteDoneTasksFromFile()},
     * інакше дія буде проігнорована.
     * Після цього спрацює службовий метод {@link #readUserMethod(IUser)}.
     *
     * @param user Поточний об'єкт класа User
     */
    private void actionForDeleteDoneTasksButton(IUser user) {
        int k = JOptionPane.showConfirmDialog(new JFrame(), "All done tasks are gonna be deleted", "Confirm changes", JOptionPane.YES_NO_OPTION);
        if (k == JOptionPane.YES_OPTION) {
            user.deleteDoneTasksFromFile();
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
