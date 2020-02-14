package com.nkm;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static com.nkm.CommandPromptInteface.*;
import static java.lang.System.lineSeparator;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;

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

    private String lines(String... userInput) {
        return asList(userInput).stream()
                .collect(joining(lineSeparator()));
    }
}