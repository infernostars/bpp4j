package dev.infernity.whirling.bpp4j.lang.interpreter.extension;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface BppFunction {
    String value();
}
