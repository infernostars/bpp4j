package dev.infernity.whirling.bpp4j.lang.parsing;

import dev.infernity.whirling.bpp4j.lang.SpanData;

import java.util.List;

public sealed interface Node {

    record Error(String message, SpanData range) implements Node {}

    record Function(String name, List<Node> arguments, SpanData range) implements Node {}

    record VariableExpression(String variable, SpanData range) implements Node {}

    record Number(String value, SpanData range) implements Node {}

    record StringNode(String value, SpanData range) implements Node {}

    record OuterText(String value, SpanData range) implements Node {}

    record Complete(SpanData range) implements Node {} // This Node will never show up to any users of this library, it is purely internal.
}
