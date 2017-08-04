package de.sgoral.darkestalmanac;

import de.sgoral.darkestalmanac.control.DarkestAlmanac;

public class Logger {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DarkestAlmanac.class.getCanonicalName());

    private Logger() {
    }

    public static void info(String msg) {
        logger.info(msg);
    }

    public static void info(Object... msg) {
        StringBuilder builder = new StringBuilder();
        for (Object part : msg) {
            builder.append(part == null ? "null" : part.toString());
        }
        logger.info(builder.toString());
    }

    public static void info(String format, Object... msg) {
        logger.info(String.format(format, msg));
    }

}
