package org.bitsquad.warzone.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class LogStdoutWriter implements LogObserver {
    @Override
    public void notify(String p_message) {
        System.out.println(p_message);
    }
}
