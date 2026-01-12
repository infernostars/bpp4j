package dev.infernity.whirling.bpp4j.shell;

import picocli.CommandLine;

@CommandLine.Command(name = "interactive", aliases = {"i", "int"}, description = "Run an interactive prompt.")
class InteractivePrompt implements Runnable {
    @Override
    public void run() {
        throw new RuntimeException("Waaaa not implemented yet!!! Waaaaa");
    }
}