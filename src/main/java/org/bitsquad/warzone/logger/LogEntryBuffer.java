package org.bitsquad.warzone.logger;

import java.util.HashMap;
import java.util.Map;

public class LogEntryBuffer extends LogObservable {
    private StringBuilder d_logBuffer;
    private HashMap<String, Object> d_fields = new HashMap<>();

    public LogEntryBuffer() {
        d_logBuffer = new StringBuilder();
    }

    public LogEntryBuffer withField(String p_key, String p_value) {
        d_fields.put(p_key, p_value);
        return this;
    }

    public void log(String p_message) {
        d_logBuffer.append(p_message);
        for (Map.Entry<String, Object> l_field : d_fields.entrySet()) {
            d_logBuffer.append("\t").append(l_field.getKey()).append("=").append(l_field.getValue());
        }
        d_logBuffer.append("\n");
        notifyObservers(d_logBuffer.toString());
        clear();
    }

    private void clear() {
        d_logBuffer = new StringBuilder();
        d_fields = new HashMap<>();
    }
}
