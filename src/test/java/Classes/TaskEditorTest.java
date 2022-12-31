package Classes;

import Interfaces.IUser;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaskEditorTest {
    static IUser user;

    @BeforeAll
    void init() {
        user = User.getInstance();
    }

    @BeforeEach
    void deInit() {
        user.setCountAllTasks(0);
        user.setCountDoneTasks(0);
        user.setTasksList(new LinkedList<>());
        Task.setCountOfTasks(0);
    }

    @ParameterizedTest
    @CsvSource({"30, 12, 30, 12", "10, 12, 10, 12"})
    @DisplayName("Перевірка чи метод fillList повертає правильно ств таск")
    void fillListTEST1(int actualDay, int actualMonth, int expectedDay, int expectedMonth) {
        Task actual = new Task("my task");
        actual.setOnTime("");
        actual.setDoBefore(actual.getDoBefore().withDayOfMonth(actualDay));
        actual.setDoBefore(actual.getDoBefore().withMonth(actualMonth));
        actual.setCreateDate(LocalDate.now());
        Task expected = user.fillList("my task", expectedDay, expectedMonth);
        expected.setNumber(actual.getNumber());
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"30, 12, 30, 12, 0"})
    @DisplayName("Перевірка чи метод fillList додає таск в список")
    void fillListTEST2(int actualDay, int actualMonth, int expectedDay, int expectedMonth, int iter) {
        List<Task> list = new LinkedList<>();
        Task actual = new Task("my task");
        actual.setOnTime("");
        actual.setDoBefore(actual.getDoBefore().withDayOfMonth(actualDay));
        actual.setDoBefore(actual.getDoBefore().withMonth(actualMonth));
        actual.setCreateDate(LocalDate.now());

        user.fillList("my task", expectedDay, expectedMonth);
        actual.setNumber(user.getTasksList().get(iter).getNumber());
        list.add(actual);
        assertAll(() -> assertEquals(list.get(iter), user.getTasksList().get(iter)),
                () -> assertEquals(list.size(), user.getTasksList().size()));
    }

    @ParameterizedTest
    @CsvSource({"30, 12, 1", "10, 12, 1"})
    @DisplayName("З кожним новим таском поле countAllTasks збільш то пройдено")
    void fillListTEST3(int expectedDay, int expectedMonth, int countOfTasks) {
        user.fillList("my task", expectedDay, expectedMonth);
        assertEquals(countOfTasks, user.getCountAllTasks());
    }

    @Test
    @DisplayName("Список tasksList пустий якщо метод deleteAllTasksFromFile()")
    void deleteAllTasksFromFileTEST1() {
        List<Task> list = initListForTests();
        user.setTasksList(list);
        list.clear();
        user.deleteAllTasksFromFile();
        assertEquals(list, user.getTasksList());
    }

    @Test
    @DisplayName("Поле countAllTasks = 0 якщо метод deleteAllTasksFromFile()")
    void deleteAllTasksFromFileTEST2() {
        List<Task> list = initListForTests();
        user.setTasksList(list);
        user.deleteAllTasksFromFile();
        assertEquals(0, user.getCountAllTasks());
    }

    @Test
    @DisplayName("Поле countDoneTasks = 0 якщо метод deleteAllTasksFromFile()")
    void deleteAllTasksFromFileTEST3() {
        List<Task> list = initListForTests();
        user.setTasksList(list);
        user.setCountDoneTasks(2);
        user.deleteAllTasksFromFile();
        assertEquals(0, user.getCountDoneTasks());
    }

    private List<Task> initListForTests() {
        Task task1 = new Task("my task");
        task1.setOnTime("");
        task1.setDoBefore(task1.getDoBefore().withDayOfMonth(30));
        task1.setDoBefore(task1.getDoBefore().withMonth(12));
        task1.setCreateDate(LocalDate.now());

        Task task2 = new Task("my task");
        task2.setOnTime("");
        task2.setDoBefore(task2.getDoBefore().withDayOfMonth(30));
        task2.setDoBefore(task2.getDoBefore().withMonth(12));
        task2.setCreateDate(LocalDate.now());

        List<Task> list = new LinkedList<>();
        list.add(task1);
        list.add(task2);
        return list;
    }

    @Test
    @DisplayName("Якщо немає DONE тасків то список після deleteDoneTasksFromFile() не міняється")
    void deleteDoneTasksFromFileTEST1() {
        List<Task> list = initListForTests();
        user.setTasksList(list);
        user.deleteDoneTasksFromFile();
        assertEquals(list, user.getTasksList());
    }

    @Test
    @DisplayName("Список немає тасків якщо всі DONE і метод deleteDoneTasksFromFile()")
    void deleteDoneTasksFromFileTEST2() {
        List<Task> list = initListForTests();
        list.get(0).setDone(true);
        list.get(1).setDone(true);
        user.setTasksList(list);
        user.deleteDoneTasksFromFile();
        list.clear();
        assertEquals(list, user.getTasksList());
    }

    @Test
    @DisplayName("Поле countDoneTasks = 0 якщо метод deleteDoneTasksFromFile()")
    void deleteDoneTasksFromFileTEST3() {
        List<Task> list = initListForTests();
        list.get(0).setDone(true);
        list.get(1).setDone(true);
        user.setTasksList(list);
        user.setCountDoneTasks(2);
        user.deleteDoneTasksFromFile();
        assertEquals(0, user.getCountDoneTasks());
    }

    @Test
    @DisplayName("Якщо номера тасків які після видаленого зменшуються то пройдено")
    void deleteDoneTasksFromFileTEST4() {
        List<Task> list = initListForTests();
        list.get(0).setDone(true);
        user.setTasksList(list);
        user.setCountDoneTasks(2);
        user.deleteDoneTasksFromFile();
        assertEquals(1, user.getTasksList().get(0).getNumber());
    }


    @Test
    @DisplayName("Перевірка чи зменшився список тасків після видалення")
    void deleteSelectedTasksFromFileTEST1() {
        List<Task> list = initListForTests();
        user.setTasksList(list);
        user.deleteSelectedTasksFromFile(user.getTasksList().get(0));
        assertEquals(1, user.getTasksList().size());
    }

    @Test
    @DisplayName("Якщо номера тасків які після видаленого зменшуються то пройдено")
    void deleteSelectedTasksFromFileTEST2() {
        List<Task> list = initListForTests();
        user.setTasksList(list);
        user.deleteSelectedTasksFromFile(user.getTasksList().get(0));
        assertEquals(1, user.getTasksList().get(0).getNumber());
    }


    @Test
    @DisplayName("При видаленні DONE таска поле countDoneTasks зменш то пройдено")
    void deleteSelectedTasksFromFileTEST3() {
        List<Task> list = initListForTests();
        list.get(0).setDone(true);
        user.setTasksList(list);
        user.setCountDoneTasks(2);
        user.deleteSelectedTasksFromFile(user.getTasksList().get(0));
        assertEquals(1, user.getCountDoneTasks());
    }

    @Test
    @DisplayName("При видаленні таска поле countAllTasks зменш то пройдено")
    void deleteSelectedTasksFromFileTEST4() {
        List<Task> list = initListForTests();
        user.setTasksList(list);
        user.setCountAllTasks(2);
        user.deleteSelectedTasksFromFile(user.getTasksList().get(0));
        assertEquals(1, user.getCountAllTasks());
    }

    @Test
    @DisplayName("Після видалення таска наступний став на його місце то пройдено")
    void deleteSelectedTasksFromFileTEST5() {
        List<Task> list = initListForTests();
        Task task = list.get(1);
        user.setTasksList(list);
        user.deleteSelectedTasksFromFile(user.getTasksList().get(0));
        assertEquals(task, user.getTasksList().get(0));
    }

    @Test
    @DisplayName("Після makeTaskDone() поле isDone у таска true")
    void makeTaskDoneTEST1() {
        List<Task> list = initListForTests();
        user.setTasksList(list);
        user.makeTaskDone(user.getTasksList().get(0));
        assertTrue(user.getTasksList().get(0).isDone());
    }

    @Test
    @DisplayName("Після makeTaskDone() поле countDoneTasks збільш на 1")
    void makeTaskDoneTEST2() {
        List<Task> list = initListForTests();
        user.setTasksList(list);
        user.makeTaskDone(user.getTasksList().get(0));
        assertEquals(1, user.getCountDoneTasks());
    }

    @Test
    @DisplayName("Після makeTaskDone() якщо якщо таск помічено Done вчасно то поле onTime = Вчасно")
    void makeTaskDoneTEST3() {
        List<Task> list = initListForTests();
        list.get(0).setDoBefore(LocalDate.now());
        user.setTasksList(list);
        user.makeTaskDone(user.getTasksList().get(0));
        assertEquals("Вчасно", user.getTasksList().get(0).getOnTime());
    }

    @Test
    @DisplayName("Після makeTaskDone() якщо таск помічено Done з запізненням то поле onTime = З запізненням")
    void makeTaskDoneTEST4() {
        List<Task> list = initListForTests();
        list.get(0).setDoBefore(LocalDate.now().withDayOfMonth(23));
        user.setTasksList(list);
        user.makeTaskDone(user.getTasksList().get(0));
        assertEquals("З запізненням", user.getTasksList().get(0).getOnTime());
    }
}