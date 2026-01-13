package dev.infernity.whirling.bpp4j.lang.tokenizer;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class WhirlStringReader extends StringReader {
    String unquotedStringExceptions;

    public WhirlStringReader(StringReader other) {
        super(other);
    }

    public WhirlStringReader(String string, String unquotedStringExceptions) {
        super(string);
        this.unquotedStringExceptions = unquotedStringExceptions;
    }

    public boolean isAllowedInUnquotedString2(char c) {
        return (!Character.isWhitespace(c)) && (unquotedStringExceptions.indexOf(c) == -1);
    }

    @Override
    public String readUnquotedString() {
        int start = this.getCursor();

        while(this.canRead() && isAllowedInUnquotedString2(this.peek())) {
            this.skip();
        }

        return this.getString().substring(start, this.getCursor());
    }


    public String readStringUntilExceptingCharacter() throws CommandSyntaxException {
        StringBuilder result = new StringBuilder();
        boolean escaped = false;

        while(this.canRead()) {
            char c = this.read();
            if (escaped) {
                if ((unquotedStringExceptions.indexOf(c) == -1) && c != '\\') {
                    this.setCursor(this.getCursor() - 1);
                    throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidEscape().createWithContext(this, String.valueOf(c));
                }

                result.append(c);
                escaped = false;
            } else if (c == '\\') {
                escaped = true;
            } else {
                if (unquotedStringExceptions.indexOf(c) != -1) {
                    this.setCursor(this.getCursor() - 1);
                    return result.toString();
                }

                result.append(c);
            }
        }

        return result.toString();
    }

    public String readNumberString() throws CommandSyntaxException {
        final int start = getCursor();
        while (canRead() && isAllowedNumber(peek())) {
            skip();
        }
        final String number = getString().substring(start, getCursor());
        if (number.isEmpty()) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerExpectedFloat().createWithContext(this);
        }
        try {
            Float.parseFloat(number);
            return number;
        } catch (final NumberFormatException ex) {
            setCursor(start);
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidFloat().createWithContext(this, number);
        }
    }
}
