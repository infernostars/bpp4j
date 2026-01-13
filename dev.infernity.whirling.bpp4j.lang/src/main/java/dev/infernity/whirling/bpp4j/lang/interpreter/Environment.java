package dev.infernity.whirling.bpp4j.lang.interpreter;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class Environment {
    /// If this is null, this means we are the root environment.
    @Nullable Environment parent;
    HashMap<String, Object> variables;
}
