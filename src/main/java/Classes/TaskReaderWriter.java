package Classes;

import Interfaces.ITaskReaderWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Клас відповідає за запис та читання об'єкта User з файлу
 */
public class TaskReaderWriter implements ITaskReaderWriter {

    public TaskReaderWriter() {
    }

    /**
     * Метод записує переданий об'єкт user у json файл використовуючи jackson
     *
     * @param user Об'єкт користувача
     */
    @Override
    public void writeUserToFile(User user) {
        String temp;
        ObjectMapper mapper = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        try {
            temp = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(User.file))) {
            writer.write(temp);
        } catch (Exception e) {
            System.out.println(User.ANSI_RED + "File not found!" + User.ANSI_RESET);
        }
        user.getTasksList().clear();
    }

    /**
     * Метод зчитує з json файлу об'єкт та повертає його
     *
     * @return Прочитаний об'єкт класа User
     */
    @Override
    public User readUserFromFile() {
        User user;
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        try {
            user = mapper.readValue(User.file, User.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
