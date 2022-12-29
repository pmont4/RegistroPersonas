package utils;

import org.apache.log4j.Logger;

public class Log {
    
    private static Logger logger;
    
    public static <T, V> void write(T c, V toWrite, int level) {
        logger = Logger.getLogger(c.getClass());
        switch (level) {
            case 0: {
                logger.debug(toWrite);
                break;
            }
            case 1: {
                logger.info(toWrite);
                break;
            }
            case 2: {
                logger.warn(toWrite);
                break;
            }
            case 3: {
                logger.error(toWrite);
                break;
            }
            case 4: {
                logger.fatal(toWrite);
                break;
            }
        }
    }
    
    public static <T> void write(T c, Throwable t) {
        logger = Logger.getLogger(c.getClass());
        logger.error(t.getLocalizedMessage(), t);
    }
}
