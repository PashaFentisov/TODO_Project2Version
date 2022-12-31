package Classes;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({TaskEditorTest.class, UserTest.class})
public class AllTests {
}
