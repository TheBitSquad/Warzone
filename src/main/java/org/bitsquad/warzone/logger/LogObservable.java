package org.bitsquad.warzone.logger;

import java.util.ArrayList;

/**
 * Abstract Observable class
 */
abstract public class LogObservable {
    private ArrayList<LogObserver> l_observers = new ArrayList<>();

    /**
     * Used to add an observer
     * @param p_observer
     */
    public void addObserver(LogObserver p_observer) {
        l_observers.add(p_observer);
    }

    /**
     * Used to remove an observer
     * @param p_observer
     */
    public void removeObserver(LogObserver p_observer) {
        l_observers.remove(p_observer);
    }

    /**
     * Notifies all observers
     * @param p_message
     */
    public void notifyObservers(String p_message) {
        for (LogObserver l_observer : l_observers) {
            l_observer.notify(p_message);
        }
    }
}
