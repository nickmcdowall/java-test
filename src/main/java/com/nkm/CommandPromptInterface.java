package com.nkm;

import com.nkm.config.AppConfig;
import com.nkm.config.StockConfig;
import com.nkm.stock.NoSuchStockItemException;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.time.LocalDate.now;
import static java.util.regex.Pattern.compile;

public class CommandPromptInterface {

    public static final Pattern ADD_ITEM_PATTERN = compile("add ([-+]?[0-9]+) ([A-Za-z]+)");
    public static final String GREETING = "~~ Welcome to Henry’s Grocery! Use the following commands to purchase items. ~~";
    public static final String GOODBYE = "Bye";
    public static final String INSTRUCTIONS =
            " ~ Type 'add <x> <ItemType>' to add x number of items to the basket.\n" +
                    " ~ Type 'exit' to quit to application.\n" +
                    " ~ Type 'checkout' to get the final price.";

    public static void main(String[] args) {
        start(new Scanner(System.in), System.out);
    }

    public static void start(Scanner scanner, PrintStream out) {
        new CommandPromptInterface(scanner, out);
    }

    private final Scanner scanner;
    private final PrintStream out;
    private final AppConfig appConfig = new AppConfig();
    private final StockConfig stockConfig = new StockConfig();
    private Application app = appConfig.getApplication();

    private CommandPromptInterface(Scanner scanner, PrintStream out) {
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
            if (scanner.hasNext("checkout")) {
                scanner.nextLine();
                out.println(format("= [Total Cost (today): %.2f]", app.priceUp(now())));
                continue;
            }
            String unknown = scanner.nextLine();
            out.println(format("? unknown command '%s'", unknown));
        }
    }

    private void processAddCommand(String text) {
        Matcher matcher = ADD_ITEM_PATTERN.matcher(text);
        while (matcher.find()) {
            try {
                addItemToBasket(matcher, parseInt(matcher.group(1)));
            } catch (NoSuchStockItemException e) {
                out.println(format("! %s", e.getMessage()));
            } catch (NegativeQuantitiesNotSupportedException e) {
                out.println("! negative quantities not supported");
            }
        }
    }

    private void addItemToBasket(Matcher matcher, int count) {
        String itemType = matcher.group(2);
        app.addBasketItem(count, stockConfig.getItemByKey(itemType));
        out.println(format("+ %s %s added", count, itemType));
    }

}
