package dev.infernity.whirling.bpp4j.lang.interpreter.datatypes.typed;

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
