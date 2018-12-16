package playground;

import static playground.CollectionLoop.myObjects;

import java.util.Arrays;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Loggers {

    Logger logger = LoggerFactory.getLogger(Loggers.class);

    @Test
    public void testLogException() {
        try {
            int i = 0;
            int j = 3 / i;
        } catch (Exception e) {
            logger.error("Une erreur est survenue", e);
        }
    }

    @Test
    public void testLogForLoop() {
        myObjects.forEach(myObject -> logger.info(myObject.toString()));
    }


    private void logLevels() {
        for (String s : Arrays.asList("A", "B", "C")) {
            logger.info(s);
            for (int j = 1; j <= 3; j++) {
                String element = s+j;
                logger.debug(element);
            }
        }
    }

    @Test
    public void testLogLevel() {
        logger.warn("Start");
        logLevels();
        logger.warn("End");
    }
}
