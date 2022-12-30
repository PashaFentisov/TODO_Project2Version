package Interfaces;

public interface ITaskReaderWriter {
    void writeUserToFile(IUser user);

    IUser readUserFromFile();
}
