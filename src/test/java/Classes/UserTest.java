package Classes;

import Interfaces.IUser;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserTest {
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

    @Test
    @DisplayName("Якщо у класа тільки один об'єкт може бути то пройдено")
    void getInstanceTEST() {
        assertEquals(user, User.getInstance());
    }

    @Test
    @DisplayName("Якщо об'єкти однакові то пройдено")
    void fillList() {
        Task expected = new Task("my task");
        expected.setOnTime("");
        expected.setDoBefore(expected.getDoBefore().withDayOfMonth(31));
        expected.setDoBefore(expected.getDoBefore().withMonth(12));
        expected.setCreateDate(LocalDate.now());
        Task actual = user.fillList("my task", 31, 12);
        actual.setNumber(expected.getNumber());
        assertEquals(expected, actual);
    }
}