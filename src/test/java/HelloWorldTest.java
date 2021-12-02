import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloWorldTest {

    @Test
    void echoInt() {
        int output = HelloWorld.echoInt(3);
        Assertions.assertEquals(3,output);
    }
}