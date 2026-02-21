package dev.infernity.whirling.bpp4j.lang.interpreter.types;

import org.jetbrains.annotations.NotNull;

public record BppInt(
        long val
) implements BppNumericType {

    @Override
    public int intValue() {
        return (int) val;
    }

    @Override
    public long longValue() {
        return val;
    }

    @Override
    public float floatValue() {
        return (float) val;
    }

    @Override
    public double doubleValue() {
        return (double) val;
    }


    @Override
    public @NotNull BppNumericType add(@NotNull BppNumericType other) {
        if (other instanceof BppFloat(double otherVal)) {
            return new BppFloat((double) val + otherVal);
        } else if (other instanceof BppInt(long otherVal)) {
            try {
                return new BppInt(Math.addExact(val, otherVal));
            } catch (ArithmeticException e) {
                return new BppFloat((double) val + (double) otherVal);
            }
        } else {
            throw new IllegalStateException("Wtf how did you break the type hierarchy");
        }
    }

    @Override
    public @NotNull BppNumericType subtract(@NotNull BppNumericType other) {
        if (other instanceof BppFloat(double otherVal)) {
            return new BppFloat((double) val - otherVal);
        } else if (other instanceof BppInt(long otherVal)) {
            try {
                return new BppInt(Math.subtractExact(val, otherVal));
            } catch (ArithmeticException e) {
                return new BppFloat((double) val - (double) otherVal);
            }
        } else {
            throw new IllegalStateException("Wtf how did you break the type hierarchy");
        }
    }

    @Override
    public @NotNull BppNumericType multiply(@NotNull BppNumericType other) {
        if (other instanceof BppFloat(double otherVal)) {
            return new BppFloat((double) val * otherVal);
        } else if (other instanceof BppInt(long otherVal)) {
            try {
                return new BppInt(Math.multiplyExact(val, otherVal));
            } catch (ArithmeticException e) {
                return new BppFloat((double) val * (double) otherVal);
            }
        } else {
            throw new IllegalStateException("Wtf how did you break the type hierarchy");
        }
    }

    @Override
    public @NotNull BppNumericType divide(@NotNull BppNumericType other) {
        if (other instanceof BppFloat(double otherVal)) {
            return new BppFloat((double) val / otherVal);
        } else if (other instanceof BppInt(long otherVal)) {
            try {
                return new BppInt(Math.divideExact(val, otherVal));
            } catch (ArithmeticException e) {
                return new BppFloat((double) val / (double) otherVal);
            }
        } else {
            throw new IllegalStateException("Wtf how did you break the type hierarchy");
        }
    }
}