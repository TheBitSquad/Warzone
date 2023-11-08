package org.bitsquad.warzone.logger;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Concrete Implementation of Observer, used to write to Log File
 */
public class LogFileWriter implements LogObserver {
    private String d_logFileName;

    /**
     * Parameterized constructor
     * @param p_logFileName
     */
    public LogFileWriter(String p_logFileName) {
        this.d_logFileName = p_logFileName;
    }

    /**
     * Used by observable to notify this observer
     * @param p_message String message
     */
    @Override
    public void notify(String p_message) {
        try (FileWriter l_fileWriter = new FileWriter(d_logFileName, true)) {
            l_fileWriter.write(p_message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
