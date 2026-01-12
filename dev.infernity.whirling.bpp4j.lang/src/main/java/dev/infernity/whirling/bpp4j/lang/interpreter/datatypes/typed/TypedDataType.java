package dev.infernity.whirling.bpp4j.lang.interpreter.datatypes.typed;

import dev.infernity.whirling.bpp4j.lang.interpreter.datatypes.DataType;

public sealed interface TypedDataType<T> extends DataType permits IntDataType {

    T getValue();
    void setValue(T value);
    // <N extends DataType> N castInto(); TODO: how to do this well?

}
