package org.bitsquad.warzone;

import org.bitsquad.warzone.cli.CliParser;

import java.util.Scanner;

/**
 * Main Class
 */
public class Main {
    public static void main(String[] args) {

        System.out.println("Hello and welcome to WARZONE!");
        CliParser parser = new CliParser();
        Scanner scanner = new Scanner(System.in);

        String ip;
        while (true) {
            System.out.print(">");
            ip = scanner.nextLine();
            try {
                parser.parseCommandString(ip);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
