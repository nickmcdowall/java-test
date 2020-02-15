package com.nkm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import static com.nkm.CommandPromptInterface.*;
import static java.lang.System.lineSeparator;
import static java.time.Duration.ofMillis;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CommandPromptInterfaceTest {

    private OutputStream outputStream = new ByteArrayOutputStream();

    @Test
    void handleExit() {
        Scanner scanner = new Scanner("exit");

        CommandPromptInterface.start(scanner, new PrintStream(outputStream));

        assertThat(outputStream.toString()).isEqualToIgnoringWhitespace(
                joinedByNewline(GREETING, INSTRUCTIONS, GOODBYE)
        );
    }

    @ParameterizedTest
    @MethodSource("variousValidAddCommands")
    void handleAddItemCommand(String command, List<String> expectedOutputLines) {
        Scanner scanner = new Scanner(joinedByNewline(command, "exit"));

        CommandPromptInterface.start(scanner, new PrintStream(outputStream));

        assertThat(outputStream.toString()).contains(joinedByNewline(expectedOutputLines));
    }

    @Test
    void checkoutWithItemsTest() {
        Scanner scanner = new Scanner(joinedByNewline("add 1 Bread", "checkout"));

        CommandPromptInterface.start(scanner, new PrintStream(outputStream));

        assertThat(outputStream.toString()).isEqualToIgnoringWhitespace(
                joinedByNewline(GREETING, INSTRUCTIONS, "+ 1 Bread added", "= [Total Cost (today): 0.80]")
        );
    }

    @Test
    void doesNotHangWithUnknownCommands() {
        Scanner scanner = new Scanner(joinedByNewline("asdfsafa", "add 1 Bread", "checkout"));

        assertTimeoutPreemptively(ofMillis(100), () -> {
                    CommandPromptInterface.start(scanner, new PrintStream(outputStream));
                    assertThat(outputStream.toString()).isEqualToIgnoringWhitespace(
                            joinedByNewline(GREETING, INSTRUCTIONS,
                                    "? unknown command 'asdfsafa'",
                                    "+ 1 Bread added",
                                    "= [Total Cost (today): 0.80]")
                    );
                }
        );
    }

    @Test
    void handleUnknownStockItems() {
        Scanner scanner = new Scanner(joinedByNewline("add 1 Cheese","add 1 Butter", "add 1 Bread", "checkout"));

        CommandPromptInterface.start(scanner, new PrintStream(outputStream));

        assertThat(outputStream.toString()).isEqualToIgnoringWhitespace(
                joinedByNewline(GREETING, INSTRUCTIONS,
                        "! No stock item exists with key: Cheese",
                        "! No stock item exists with key: Butter",
                        "+ 1 Bread added",
                        "= [Total Cost (today): 0.80]")
        );
    }

    @Test
    void negativeQuantitiesHRejectedGracefully() {
        Scanner scanner = new Scanner(joinedByNewline("add -1 Bread", "checkout"));

        CommandPromptInterface.start(scanner, new PrintStream(outputStream));

        assertThat(outputStream.toString()).isEqualToIgnoringWhitespace(
                joinedByNewline(GREETING, INSTRUCTIONS,
                        "! negative quantities not supported",
                        "= [Total Cost (today): 0.00]")
        );
    }

    private static Stream<Arguments> variousValidAddCommands() {
        return Stream.of(
                arguments("add 2 Apple", List.of("+ 2 Apple added")),
                arguments("add 1 Soup add 1 Soup", List.of("+ 1 Soup added", "+ 1 Soup added")),
                arguments("add 1 Bread add 1 Milk", List.of("+ 1 Bread added", "+ 1 Milk added")),
                arguments("add 13 Apple add 0 Soup", List.of("+ 13 Apple added", "+ 0 Soup added"))
        );
    }

    private String joinedByNewline(String... userInput) {
        return joinedByNewline(asList(userInput));
    }

    private String joinedByNewline(List<String> userInput) {
        return userInput.stream()
                .collect(joining(lineSeparator()));
    }
}