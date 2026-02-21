package dev.infernity.whirling.bpp4j.lang.interpreter;

import dev.infernity.whirling.bpp4j.lang.SpanData;

public sealed interface Output permits Output.SuccessOutput, Output.FailureOutput {
    boolean isSuccess();
    
    String contentOrThrow() throws IllegalStateException;
    
    final class SuccessOutput implements Output {
        String content;

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public String contentOrThrow() throws IllegalStateException {
            return content;
        }

        public void append(String string) {
            content += string;
        }
    }
    
    record FailureOutput(
      SpanData location,
      String message
    ) implements Output{
        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public String contentOrThrow() throws IllegalStateException {
            throw new IllegalStateException("Cannot get content of a failure output");
        }
    }
}
