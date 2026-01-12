package dev.infernity.whirling.bpp4j.lang.interpreter;

import dev.infernity.whirling.bpp4j.lang.interpreter.datatypes.DataType;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class Environment {
    @Nullable Environment parent;
    HashMap<String, DataType> variables;
}
