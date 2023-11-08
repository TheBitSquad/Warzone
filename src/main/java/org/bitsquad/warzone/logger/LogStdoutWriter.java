package org.bitsquad.warzone.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Concrete Observer of the logger to write to STDOUT
 */
public class LogStdoutWriter implements LogObserver {

    /**
     * Used to notify the observer
     * @param p_message String message
     */
    @Override
    public void notify(String p_message) {
        System.out.print(p_message);
    }
}
