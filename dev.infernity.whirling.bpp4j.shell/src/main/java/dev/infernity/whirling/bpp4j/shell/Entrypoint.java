package dev.infernity.whirling.bpp4j.shell;

import dev.infernity.whirling.bpp4j.lang.Bpp;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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

    static void main(String[] args){
        int exitCode = new CommandLine(new Entrypoint()).execute(args);
        System.exit(exitCode);
    }
}
