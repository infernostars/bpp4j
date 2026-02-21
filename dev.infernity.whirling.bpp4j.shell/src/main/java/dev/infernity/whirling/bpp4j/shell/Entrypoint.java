package dev.infernity.whirling.bpp4j.shell;

import dev.infernity.whirling.bpp4j.lang.SpanData;
import dev.infernity.whirling.bpp4j.lang.interpreter.InterpreterState;
import dev.infernity.whirling.bpp4j.lang.parsing.Node;
import dev.infernity.whirling.bpp4j.lang.parsing.Parser;
import dev.infernity.whirling.bpp4j.lang.parsing.ParsingResult;
import dev.infernity.whirling.bpp4j.lang.tokenizer.TokenizationResult;
import dev.infernity.whirling.bpp4j.lang.tokenizer.Tokenizer;
import org.fusesource.jansi.AnsiConsole;
import org.fusesource.jansi.AnsiPrintStream;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.fusesource.jansi.Ansi.*;

@CommandLine.Command(name = "bpp4j", subcommands = {CommandLine.HelpCommand.class, InteractivePrompt.class})
public class Entrypoint {

    @SuppressWarnings("unused")
    @CommandLine.Command(name = "run", description = "Run a bpp file.")
    void run(
            @CommandLine.Parameters(arity = "1", paramLabel = "<file>", description = "the file to run") File file
    ) {

        String content;
        try {
            content = Files.readString(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var res = Tokenizer.tokenize(file.toPath().toString(), content);
        if (res instanceof TokenizationResult.Error(
                String message, SpanData location
        )) {
            try (AnsiPrintStream aps = AnsiConsole.out()) {
                aps.print(ansi().fg(Color.RED).a("An error occured in tokenization:\n")
                        .a(location.debugInfo().toString()).a("\n")
                        .bold().fgBright(Color.RED).a(message).reset().a("\n\n"));
            }
            return;
        }
        assert res instanceof TokenizationResult.Success;
        var tokens = ((TokenizationResult.Success) res).tokens();
        var res2 = Parser.parse(file.toPath().toString(), content, tokens);
        if (res2 instanceof ParsingResult.Error(
                String message, SpanData location
        )) {
            try (AnsiPrintStream aps = AnsiConsole.out()) {
                aps.print(ansi().fg(Color.RED).a("An error occured in parsing:\n")
                        .a(location.debugInfo().toString()).a("\n")
                        .bold().fgBright(Color.RED).a(message).reset().a("\n\n"));
            }
            return;
        }
        runInner(content, ((ParsingResult.Success) res2).nodes());
    }

    private void runInner(String source, List<Node> nodes) {
        InterpreterState state = new InterpreterState(source, nodes);
    }


    @SuppressWarnings("unused")
    @CommandLine.Command(name = "tokenize", description = "Tokenize a bpp file.")
    void tokenize(
            @CommandLine.Parameters(arity = "1", paramLabel = "<file>", description = "the file to tokenize") File file
    ) {

        String content;
        try {
            content = Files.readString(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var res = Tokenizer.tokenize(file.toPath().toString(), content);
        switch (res) {
            case TokenizationResult.Error error -> {
                try (AnsiPrintStream aps = AnsiConsole.out()) {
                    aps.print(ansi().fg(Color.RED).a("An error occured in tokenization:\n")
                            .a(error.location().debugInfo().toString()).a("\n")
                            .bold().fgBright(Color.RED).a(error.message()).reset().a("\n\n"));
                }
            }
            case TokenizationResult.Success success -> {
                try (AnsiPrintStream aps = AnsiConsole.out()) {
                    aps.print(ansi().fg(Color.GREEN).a("Tokenization successful.\n").reset()
                            .a(success.pretty()).a("\n\n"));
                }
            }
        }
    }


    @SuppressWarnings("unused")
    @CommandLine.Command(name = "parse", description = "Parse a bpp file.")
    void parse(
            @CommandLine.Parameters(arity = "1", paramLabel = "<file>", description = "the file to parse") File file
    ) {

        String content;
        try {
            content = Files.readString(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var res = Tokenizer.tokenize(file.toPath().toString(), content);
        if (res instanceof TokenizationResult.Error(
                String message, SpanData location
        )) {
            try (AnsiPrintStream aps = AnsiConsole.out()) {
                aps.print(ansi().fg(Color.RED).a("An error occured in tokenization:\n")
                        .a(location.debugInfo().toString()).a("\n")
                        .bold().fgBright(Color.RED).a(message).reset().a("\n\n"));
            }
        }
        assert res instanceof TokenizationResult.Success;
        var tokens = ((TokenizationResult.Success) res).tokens();
        var res2 = Parser.parse(file.toPath().toString(), content, tokens);
        switch (res2) {
            case ParsingResult.Error error -> {
                try (AnsiPrintStream aps = AnsiConsole.out()) {
                    aps.print(ansi().fg(Color.RED).a("An error occured in parsing:\n")
                            .a(error.location().debugInfo().toString()).a("\n")
                            .bold().fgBright(Color.RED).a(error.message()).reset().a("\n\n"));
                }
            }
            case ParsingResult.Success success -> {
                try (AnsiPrintStream aps = AnsiConsole.out()) {
                    aps.print(ansi().fg(Color.GREEN).a("Parsing successful.\n").reset()
                            .a(success.pretty()).a("\n\n"));
                }
            }
        }
    }

    static void main(String[] args){
        AnsiConsole.systemInstall();
        int exitCode = new CommandLine(new Entrypoint()).execute(args);
        AnsiConsole.systemUninstall();
        System.exit(exitCode);
    }
}
