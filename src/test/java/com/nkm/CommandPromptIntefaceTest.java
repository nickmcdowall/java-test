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

import static com.nkm.CommandPromptInteface.*;
import static java.lang.System.lineSeparator;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CommandPromptIntefaceTest {

    private OutputStream outputStream = new ByteArrayOutputStream();

    @Test
    void handleExit() {
        Scanner scanner = new Scanner(lines("exit"));

        CommandPromptInteface.start(scanner, new PrintStream(outputStream));

        assertThat(outputStream.toString()).isEqualToIgnoringWhitespace(
                lines(GREETING, INSTRUCTIONS, GOODBYE)
        );
    }

    @ParameterizedTest
    @MethodSource("variousValidAddCommands")
    void handleAddItemCommand(String addCommand, List<String> expectedOutputLines) {
        Scanner scanner = new Scanner(lines(addCommand, "exit"));

        CommandPromptInteface.start(scanner, new PrintStream(outputStream));

        assertThat(outputStream.toString()).contains(lines(expectedOutputLines));
    }

    @Test
    void checkoutWithItemsTest() {
        Scanner scanner = new Scanner(lines("add 1 Bread", "checkout"));

        CommandPromptInteface.start(scanner, new PrintStream(outputStream));

        assertThat(outputStream.toString()).isEqualToIgnoringWhitespace(
                lines(GREETING, INSTRUCTIONS, "+ 1 Bread added", "= [Total Cost (today): 0.80]")
        );
    }

    private static Stream<Arguments> variousValidAddCommands() {
        return Stream.of(
                arguments("add 2 Apple", List.of("+ 2 Apple added")),
                arguments("add 1 TinSoup add 1 TinSoup", List.of("+ 1 TinSoup added", "+ 1 TinSoup added")),
                arguments("add 1 Bread add 1 BottleMilk", List.of("+ 1 Bread added", "+ 1 BottleMilk added")),
                arguments("add 13 Apple add 0 TinSoup", List.of("+ 13 Apple added", "+ 0 TinSoup added"))
        );
    }

    private String lines(String... userInput) {
        return lines(asList(userInput));
    }

    private String lines(List<String> userInput) {
        return userInput.stream()
                .collect(joining(lineSeparator()));
    }
}