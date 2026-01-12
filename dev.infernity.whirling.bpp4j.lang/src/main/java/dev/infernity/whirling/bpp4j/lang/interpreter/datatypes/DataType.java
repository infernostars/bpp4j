package dev.infernity.whirling.bpp4j.lang.interpreter.datatypes;

import dev.infernity.whirling.bpp4j.lang.interpreter.datatypes.typed.TypedDataType;

public sealed interface DataType permits FunctionDataType, TypedDataType {
}
