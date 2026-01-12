package dev.infernity.whirling.bpp4j.lang.interpreter.datatypes.typed;

import dev.infernity.whirling.bpp4j.lang.exc.InvalidCastException;
import dev.infernity.whirling.bpp4j.lang.interpreter.datatypes.DataType;
import dev.infernity.whirling.bpp4j.lang.interpreter.datatypes.FunctionDataType;

public final class IntDataType implements TypedDataType<Integer> {
    Integer value;

    @Override
    public Integer getValue() {
        return value;
    }

    public int getAsInt() {
        return value;
    }

    @Override
    public void setValue(Integer newValue) {
        value = newValue;
    }

    public void setAsInt(int newValue){
        value = newValue;
    }
}
