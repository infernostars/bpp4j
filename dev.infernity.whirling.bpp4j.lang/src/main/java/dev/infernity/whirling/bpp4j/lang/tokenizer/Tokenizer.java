package dev.infernity.whirling.bpp4j.lang.tokenizer;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.infernity.whirling.bpp4j.lang.SpanData;

import java.nio.file.Path;
import java.util.ArrayList;

public final class Tokenizer {
    static String exceptions = "[]$";
    String fileName;
    WhirlStringReader reader;
    int nestingLevel;

    public static TokenizationResult tokenize(String fileName, String file){
        var tokenizer = new Tokenizer();
        tokenizer.fileName = fileName;
        tokenizer.reader = new WhirlStringReader(file, exceptions);

        return tokenizer.tokenizeLoop();
    }

    private TokenizationResult tokenizeLoop() {
        var list = new ArrayList<Token>();
        while(true) {
            Token token = tokenizeOnce();
            if(token instanceof Token.Error(String message, SpanData range)) {
                return new TokenizationResult.Error(message, range);
            }
            if(token instanceof Token.EndOfFile) {
                list.add(new Token.EndOfFile(createSpan()));
                list.add(new Token.EndOfFile(createSpan()));
                list.add(new Token.EndOfFile(createSpan()));
                list.add(new Token.EndOfFile(createSpan()));
                list.add(new Token.EndOfFile(createSpan()));

                return new TokenizationResult.Success(list);
            }
            list.add(token);
        }

    }

    private Token tokenizeOnce() {
        try {
            return tokenizeInner();
        } catch (CommandSyntaxException e) {
            return new Token.Error(e.getMessage(), createSpan());
        } catch (StringIndexOutOfBoundsException e) {
            return new Token.EndOfFile(createSpan());
        }
    }

    private Token tokenizeInner() throws CommandSyntaxException, StringIndexOutOfBoundsException {
        if (nestingLevel == 0 && exceptions.indexOf(reader.peek()) == -1) {
            var start = this.reader.getCursor();
            return new Token.OuterString(this.reader.readStringUntilExceptingCharacter(), createSpan(start));
        }

        reader.skipWhitespace();
        var start = this.reader.getCursor();

        return switch (reader.peek()) {
            case '"' -> new Token.QuotedString(reader.readQuotedString(), createSpan(start));
            case '[' -> {
                reader.expect('[');
                nestingLevel += 1;
                yield new Token.OpenBracket(createSpan(start));
            }
            case ']' -> {
                reader.expect(']');
                nestingLevel -= 1;
                if (nestingLevel < 0) {
                    yield new Token.Error("Right bracket not escaped or used to close function", createSpan(start));
                }
                yield new Token.CloseBracket(createSpan(start));
            }
            case '-' -> {
                char next = reader.peek(1);
                if ((next >= '0' && next <= '9') || next == '.') {
                    yield new Token.Number(reader.readNumberString(), createSpan(start));
                }
                yield new Token.UnquotedString(reader.readUnquotedString(), this.createSpan(start));
            }
            case '$' -> {
                reader.expect('$');
                String ident = reader.readIdentifier();
                if (ident.isEmpty()) {
                    yield new Token.Error("Short variable declaration without an identifier (hint: use backslashes to escape)", createSpan(start));
                }
                yield new Token.ShortVariableExpression(ident, this.createSpan(start));
            }
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.' -> new Token.Number(reader.readNumberString(), createSpan(start));
            default -> new Token.UnquotedString(reader.readUnquotedString(), this.createSpan(start));
        };
    }

    public SpanData createSpan() {
        return new SpanData(this.reader.getCursor(), this.reader.getCursor(), this.reader.getString(), fileName);
    }

    public SpanData createSpan(int start) {
        return new SpanData(start, this.reader.getCursor(), this.reader.getString(), fileName);
    }

    @SuppressWarnings("unused")
    public SpanData createSpan(int start, int end) {
        return new SpanData(start, end, this.reader.getString(), fileName);
    }

}
