package playground;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static playground.CollectionLoop.myObjects;


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
        for (char c : "ABCDEF".toCharArray()) {
            logger.info(""+c);
            for (int j = 0; j < 10; j++) {
                logger.debug(""+c+j);
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
