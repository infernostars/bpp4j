package dev.infernity.whirling.bpp4j.lang;

import dev.infernity.whirling.bpp4j.lang.interpreter.InterpreterState;

public class Bpp {
    public static InterpreterState create(String source){
        return new InterpreterState(source);
    }
}
