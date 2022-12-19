package Interfaces;

import Classes.User;

public interface ITaskReaderWriter {
    void writeUserToFile(User user);

    User readUserFromFile();
}
