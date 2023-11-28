package org.bitsquad.warzone.player;

public class Player extends BasePlayer{
    public Player(String p_name) {
        super(p_name);
    }

    @Override
    public void issueOrder() {
        this.d_orderList.add(this.d_currentOrder);
        this.d_currentOrder = null;
    }
}
