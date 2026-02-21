package dev.infernity.whirling.bpp4j.lang.interpreter.types;

import org.jetbrains.annotations.NotNull;

public record BppFloat(
        double val
) implements BppNumericType {

    @Override
    public int intValue() {
        return (int) val;
    }

    @Override
    public long longValue() {
        return (long) val;
    }

    @Override
    public float floatValue() {
        return (float) val;
    }

    @Override
    public double doubleValue() {
        return val;
    }

    @Override
    public @NotNull BppNumericType add(@NotNull BppNumericType other) {
        if (other instanceof BppFloat(double otherVal)) {
            return new BppFloat(val + otherVal);
        } else if (other instanceof BppInt(long otherVal)) {
            return new BppFloat(val + ((double) otherVal));
        } else {
            throw new IllegalStateException("Wtf how did you break the type hierarchy");
        }
    }

    @Override
    public @NotNull BppNumericType subtract(@NotNull BppNumericType other) {
        if (other instanceof BppFloat(double otherVal)) {
            return new BppFloat(val - otherVal);
        } else if (other instanceof BppInt(long otherVal)) {
            return new BppFloat(val - ((double) otherVal));
        } else {
            throw new IllegalStateException("Wtf how did you break the type hierarchy");
        }
    }

    @Override
    public @NotNull BppNumericType multiply(@NotNull BppNumericType other) {
        if (other instanceof BppFloat(double otherVal)) {
            return new BppFloat(val * otherVal);
        } else if (other instanceof BppInt(long otherVal)) {
            return new BppFloat(val * ((double) otherVal));
        } else {
            throw new IllegalStateException("Wtf how did you break the type hierarchy");
        }
    }

    @Override
    public @NotNull BppNumericType divide(@NotNull BppNumericType other) {
        if (other instanceof BppFloat(double otherVal)) {
            return new BppFloat(val / otherVal);
        } else if (other instanceof BppInt(long otherVal)) {
            return new BppFloat(val / ((double) otherVal));
        } else {
            throw new IllegalStateException("Wtf how did you break the type hierarchy");
        }
    }
}