package org.bitsquad.warzone;

import org.bitsquad.warzone.cli.CliParser;
import org.bitsquad.warzone.gameengine.GameEngine;
import org.bitsquad.warzone.gamerunner.GameRunner;
import org.bitsquad.warzone.logger.LogEntryBuffer;
import org.bitsquad.warzone.logger.LogFileWriter;
import org.bitsquad.warzone.logger.LogStdoutWriter;

import java.util.Scanner;

/**
 * Main Class
 *
 * @author Group_W16
 * @version BUILD 3.0
 */
public class Main {

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {

        // Setup the logger
        LogEntryBuffer l_logger = LogEntryBuffer.getInstance();
        LogFileWriter l_logFileWriter = new LogFileWriter("warzone.log");
        LogStdoutWriter l_logStdoutWriter = new LogStdoutWriter();
        l_logger.addObserver(l_logFileWriter);
        l_logger.addObserver(l_logStdoutWriter);

        GameRunner.getInstance().runGame();
    }
}
