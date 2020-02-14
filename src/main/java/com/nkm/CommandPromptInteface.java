package com.nkm;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;
import static java.util.regex.Pattern.compile;

public class CommandPromptInteface {

    public static final Pattern ADD_ITEM_PATTERN = compile("add ([0-9]+) ([A-Za-z]+)");
    public static final String GREETING = "Welcome to Henry’s Grocery! Use the following commands to purchase items.";
    public static final String GOODBYE = "Bye";
    public static final String INSTRUCTIONS =
            " - Type 'add <x> <ItemType>' to add x number of items to the basket.\n" +
                    " - Type 'exit' to quit to application.\n" +
                    " - Type 'checkout' to get the final price.\n" +
                    " - Type 'restart' to empty the basket and start again.";

    public static void start(Scanner scanner, PrintStream out) {
        new CommandPromptInteface(scanner, out);
    }

    private final Scanner scanner;
    private final PrintStream out;

    private CommandPromptInteface(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
        engageWithUser();
    }

    private void engageWithUser() {
        out.println(GREETING);
        out.println(INSTRUCTIONS);
        while (scanner.hasNext()) {
            if (scanner.hasNext("exit")) {
                out.println(GOODBYE);
                return;
            }
            if (scanner.hasNext("add")) {
                processAddCommand(scanner.nextLine());
                continue;
            }
        }
    }

    private void processAddCommand(String text) {
        Matcher matcher = ADD_ITEM_PATTERN.matcher(text);
        while (matcher.find()) {
            out.println(format("+ %s %s added", matcher.group(1), matcher.group(2)));
        }
    }

}
