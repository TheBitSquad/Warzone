package org.bitsquad.warzone.card;

import java.util.Random;

/**
 * CardGenerator is responsible for generating new cards
 */
public class CardGenerator {
    /**
     * It will generate a new random Card
     *
     * @return a Random Card
     */
    public static Card generateRandomCard() {
        Card[] l_values = Card.values();
        Random l_random = new Random();
        return l_values[l_random.nextInt(l_values.length)];
    }
}
