import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ExampleTest {
    private Calculator calc = new Calculator();

    @Test
    public void TestAddition() {
        assertEquals(3, calc.Add(1, 2));
    }

    @Test
    public void TestMultiplication() {
        assertEquals(4, calc.Multiply(2, 2));
    }
}
