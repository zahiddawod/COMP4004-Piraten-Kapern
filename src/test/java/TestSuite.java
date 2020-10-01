import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SuiteDisplayName("JUnit Platform Suite")
@SelectClasses({
        TestNetworking.class,
        TestPlayer.class,
        TestGame.class,
        TestGridPartOne.class,
        TestGridPartTwo.class
})
public class TestSuite {}
