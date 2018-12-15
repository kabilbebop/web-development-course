package playground;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class CollectionPlayground {

    @Test
    public void testArrayList() {
        List<String> strings = new ArrayList<>();
        strings.add("a");
        strings.add("b");
        strings.add("c");
        System.out.println(strings.get(0));
        System.out.println(strings.get(1));
        System.out.println(strings.get(2));
    }

    @Test
    public void test() {
        Integer i = 1;
        assertEquals(2, i + i);
        assertNotEquals(3, i + i);
    }

}
