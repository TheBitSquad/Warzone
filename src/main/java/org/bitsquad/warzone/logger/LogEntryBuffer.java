package org.bitsquad.warzone.logger;

import org.bitsquad.warzone.gameengine.GameEngine;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of Observable, the Entry Buffer to write
 */
public class LogEntryBuffer extends LogObservable {
    private static LogEntryBuffer d_instance;
    private StringBuilder d_logBuffer;
    private HashMap<String, Object> d_fields = new HashMap<>();

    /**
     * Singleton getter
     * @return LogEntryBuffer
     */
    public static LogEntryBuffer getInstance(){
        if (d_instance == null) {
            d_instance = new LogEntryBuffer();
        }
        return d_instance;
    }

    /**
     * Default Constructor
     */
    private LogEntryBuffer() {
        d_logBuffer = new StringBuilder();
    }

    /**
     * Used to write to buffer using key:value fields
     * @param p_key
     * @param p_value
     * @return
     */
    public LogEntryBuffer withField(String p_key, String p_value) {
        d_fields.put(p_key, p_value);
        return this;
    }

    /**
     * Used to log messages
     * @param p_message
     */
    public void log(String p_message) {
        d_logBuffer.append(p_message);
        for (Map.Entry<String, Object> l_field : d_fields.entrySet()) {
            d_logBuffer.append("\t").append(l_field.getKey()).append("=").append(l_field.getValue());
        }
        d_logBuffer.append("\n");
        notifyObservers(d_logBuffer.toString());
        clear();
    }

    /**
     * Used to clear the buffer
     */
    private void clear() {
        d_logBuffer = new StringBuilder();
        d_fields = new HashMap<>();
    }
}
