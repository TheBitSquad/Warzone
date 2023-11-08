package org.bitsquad.warzone.logger;

/**
 * Observer interface
 */
public interface LogObserver {
    /**
     * To be used to notify an observer
     * @param p_message String message
     */
    void notify(String p_message);
}
