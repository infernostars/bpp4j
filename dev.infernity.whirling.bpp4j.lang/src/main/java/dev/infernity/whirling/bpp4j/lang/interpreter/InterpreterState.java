package dev.infernity.whirling.bpp4j.lang.interpreter;

public class InterpreterState {
    String code;
    Environment topLevelEnvironment;

    public InterpreterState(String source) {
        this.code = source;
        this.topLevelEnvironment = new Environment();
    }
}
