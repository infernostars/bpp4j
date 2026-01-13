package dev.infernity.whirling.bpp4j.lang.tokenizer;

import java.util.List;

public sealed interface TokenizationResult {
    record Success(List<Token> tokens) implements TokenizationResult {
    }

    record Error(String message, SpanData location) implements TokenizationResult {
    }
}
