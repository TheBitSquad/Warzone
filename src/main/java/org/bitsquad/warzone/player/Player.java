package org.bitsquad.warzone.player;

/**
 * Implementation of Human player strategy
 */
public class Player extends BasePlayer{

    /**
     * Parameterized constructor
     * @param p_name
     */
    public Player(String p_name) {
        super(p_name);
    }

    /**
     * Issue Order method
     */
    @Override
    public void issueOrder() {
        this.d_orderList.add(this.d_currentOrder);
        this.d_currentOrder = null;
    }
}
