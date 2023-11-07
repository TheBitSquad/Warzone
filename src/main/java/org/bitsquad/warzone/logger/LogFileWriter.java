package org.bitsquad.warzone.logger;

import java.io.FileWriter;
import java.io.IOException;

public class LogFileWriter implements LogObserver {
    private String d_logFileName;

    public LogFileWriter(String p_logFileName) {
        this.d_logFileName = p_logFileName;
    }

    @Override
    public void notify(String p_message) {
        try (FileWriter l_fileWriter = new FileWriter(d_logFileName, true)) {
            l_fileWriter.write(p_message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
