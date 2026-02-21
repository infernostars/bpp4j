package dev.infernity.whirling.bpp4j.lang.interpreter.extension.provided;

import dev.infernity.whirling.bpp4j.lang.interpreter.Environment;
import dev.infernity.whirling.bpp4j.lang.interpreter.extension.BppExtension;
import dev.infernity.whirling.bpp4j.lang.interpreter.extension.BppFunction;

public class Test1 implements BppExtension {
    @Override
    public BppExtension withRootEnvironment(Environment rootEnvironment) {
        return this;
    }

    @BppFunction("foo")
    public void test() {

    }
}
