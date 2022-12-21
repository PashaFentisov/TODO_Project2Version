package TestClasses;

import Classes.TaskEditor;
import Classes.TaskReaderWriter;
import Classes.TaskShower;
import Classes.User;
import ServiceClasses.OwnReader;

public class Main {
    public static void main(String[] args) {
        User user = new User(new TaskEditor(new OwnReader()), new TaskShower(), new TaskReaderWriter());
        user.fillList();
        user.makeTaskDone();
    }
}
