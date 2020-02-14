package com.nkm;

import java.io.PrintStream;
import java.util.Scanner;

public class CommandPromptInteface {

    public static final String GREETING = "Welcome to Henry’s Grocery! Use the following commands to purchase items.";
    public static final String GOODBYE = "Bye";
    public static final String INSTRUCTIONS =
            "Type 'add <x> <ItemType>' to add x number of items to the basket.\n" +
                    "Type 'exit' to quit to application.\n" +
                    "Type 'checkout' to get the final price.\n" +
                    "Type 'restart' to empty the basket and start again.";

    public static void start(Scanner scanner, PrintStream out) {
        new CommandPromptInteface(scanner, out);
    }

    private final Scanner scanner;

    private CommandPromptInteface(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        engageWithUser(out);
    }

    private void engageWithUser(PrintStream out) {
        out.println(GREETING);
        out.println(INSTRUCTIONS);
        while (scanner.hasNext()) {
            if (scanner.hasNext("exit")) {
                out.println(GOODBYE);
                return;
            }
            scanner.next();
        }
    }

}
