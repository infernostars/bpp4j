package dev.infernity.whirling.bpp4j.lang.tokenizer;

import dev.infernity.whirling.bpp4j.lang.SpanData;

public sealed interface Token {
    SpanData range();

    record Error(String message, SpanData range) implements Token {}

    record OpenBracket(SpanData range) implements Token {}
    record CloseBracket(SpanData range) implements Token {}

    record OuterString(String value, SpanData range) implements Token {}
    record UnquotedString(String value, SpanData range) implements Token {}
    record QuotedString(String value, SpanData range) implements Token {}

    record ShortVariableExpression(String variable, SpanData range) implements Token {}

    record Number(String value, SpanData range) implements Token {}

    record EndOfFile(SpanData range) implements Token {}
}
