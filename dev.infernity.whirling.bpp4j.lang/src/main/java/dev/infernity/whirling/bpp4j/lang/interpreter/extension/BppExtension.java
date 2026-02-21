package dev.infernity.whirling.bpp4j.lang.interpreter.extension;

import dev.infernity.whirling.bpp4j.lang.interpreter.Environment;
import dev.infernity.whirling.bpp4j.lang.interpreter.extension.provided.Test1;
import dev.infernity.whirling.bpp4j.lang.interpreter.extension.provided.Test2;
import org.jetbrains.annotations.Contract;

import java.util.List;


public interface BppExtension {
    static List<BppExtension> defaultExtensions(){
        return List.of(
                new Test1(),
                new Test2()
        );
    }

    @Contract("_ -> this")
    BppExtension withRootEnvironment(Environment rootEnvironment);
}
