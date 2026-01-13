package dev.infernity.whirling.bpp4j.shell;

import dev.infernity.whirling.bpp4j.lang.Bpp;
import dev.infernity.whirling.bpp4j.lang.tokenizer.TokenizationResult;
import dev.infernity.whirling.bpp4j.lang.tokenizer.Tokenizer;
import org.fusesource.jansi.AnsiConsole;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.fusesource.jansi.Ansi.*;

@CommandLine.Command(name = "bpp4j", subcommands = {CommandLine.HelpCommand.class, InteractivePrompt.class})
public class Entrypoint {

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
        IO.println(content);
        var intState = Bpp.create(content);
        throw new RuntimeException("Waaaa not implemented yet!!! Waaaaa");
    }


    @CommandLine.Command(name = "parse", description = "Parse a bpp file.")
    void parse(
            @CommandLine.Parameters(arity = "1", paramLabel = "<file>", description = "the file to parse") File file
    ) {

        AnsiConsole.systemInstall();
        String content;
        try {
            content = Files.readString(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        IO.println(content);
        var res = Tokenizer.tokenize(file.toPath(), content);
        switch (res) {
            case TokenizationResult.Error error -> {
                IO.println(ansi().fg(Color.RED).a("An error occured in tokenization:\n").a(error.location().debugInfo().toString()));
            }
            case TokenizationResult.Success success -> {
            }
        }
        AnsiConsole.systemUninstall();
        IO.println(res);
    }

    static void main(String[] args){
        int exitCode = new CommandLine(new Entrypoint()).execute(args);
        System.exit(exitCode);
    }
}
