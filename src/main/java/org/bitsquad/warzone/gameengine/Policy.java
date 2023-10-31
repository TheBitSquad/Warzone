package org.bitsquad.warzone.gameengine;
import org.bitsquad.warzone.order.Order;

/**
 * Policy interface
 */
public interface Policy {

    /**
     * Checks if the policy is being followed
     * @param p_order Order
     * @return boolean
     */
    public boolean check(Order p_order);
}

