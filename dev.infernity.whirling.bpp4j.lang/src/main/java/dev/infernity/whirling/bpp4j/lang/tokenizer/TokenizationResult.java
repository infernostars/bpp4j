package dev.infernity.whirling.bpp4j.lang.tokenizer;

import java.util.List;

public sealed interface TokenizationResult {
    record Success(List<Token> tokens) implements TokenizationResult {
        public String pretty(){
            StringBuilder str = new StringBuilder();
            str.append(tokens.size()).append(" tokens:");
            for (Token token : tokens) {
                str.append("\n");
                str.append(token.toString());
            }
            return str.toString();
        }
    }

    record Error(String message, SpanData location) implements TokenizationResult {
    }
}
