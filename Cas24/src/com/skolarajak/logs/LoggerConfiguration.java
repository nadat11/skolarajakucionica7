package com.skolarajak.logs;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

public class LoggerConfiguration {

    private static final LogManager logManager = LogManager.getLogManager();

    /*
     * Java static block always loads before constructors
     */
    static {
        try {
            InputStream inputStream = 
                ClassLoader.class.getResourceAsStream("/logger.properties");

            logManager.readConfiguration(inputStream);

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}