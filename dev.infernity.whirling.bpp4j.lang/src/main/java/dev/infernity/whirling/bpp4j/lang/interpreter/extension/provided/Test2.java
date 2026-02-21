package dev.infernity.whirling.bpp4j.lang.interpreter.extension.provided;

import dev.infernity.whirling.bpp4j.lang.interpreter.Environment;
import dev.infernity.whirling.bpp4j.lang.interpreter.extension.BppExtension;
import dev.infernity.whirling.bpp4j.lang.interpreter.extension.BppFunction;
import dev.infernity.whirling.bpp4j.lang.interpreter.types.BppString;

public class Test2 implements BppExtension {
    @Override
    public BppExtension withRootEnvironment(Environment rootEnvironment) {
        return this;
    }

    @BppFunction("bar")
    public void bar(BppString string) {

    }
}
