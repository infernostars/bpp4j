package dev.infernity.whirling.bpp4j.lang.interpreter.types;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public sealed interface BppNumericType extends BppType permits BppFloat, BppInt {
    int intValue();

    long longValue();

    float floatValue();

    double doubleValue();

    @Contract("_ -> new")
    @NotNull BppNumericType add(@NotNull BppNumericType other);

    @Contract("_ -> new")
    @NotNull BppNumericType subtract(@NotNull BppNumericType other);

    @Contract("_ -> new")
    @NotNull BppNumericType multiply(@NotNull BppNumericType other);

    @Contract("_ -> new")
    @NotNull BppNumericType divide(@NotNull BppNumericType other);
}
