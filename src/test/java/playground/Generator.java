package playground;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class Generator {

    private static final Logger LOGGER = LoggerFactory.getLogger(Generator.class);

    @Test
    public void testGenerator1() {
//        Film h2g2 = new Film("The hitchhiker's guide to the galaxy",
//            50_000_000, LocalDate.of(2005, 8, 18));
//        LOGGER.info(h2g2.toString());
    }

    @Test
    public void testGenerator2() {
//        Film snatch = new Film()
//            .setName("Snatch")
//            .setDate(LocalDate.of(2000, 11, 15))
//            .setBudget(10_000_000);
//        LOGGER.info(snatch.toString());
    }

}

class Film {

    String name;
    Integer budget;
    LocalDate date;

}

