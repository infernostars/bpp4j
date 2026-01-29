package dev.infernity.whirling.bpp4j.lang.parsing;

import dev.infernity.whirling.bpp4j.lang.SpanData;
import dev.infernity.whirling.bpp4j.lang.parsing.Node;

import java.util.List;

public sealed interface ParsingResult {
    record Success(List<Node> nodes) implements ParsingResult {
        public String pretty(){
            StringBuilder str = new StringBuilder();
            str.append(nodes.size()).append(" nodes:");
            for (Node node : nodes) {
                str.append("\n");
                str.append(node.toString());
            }
            return str.toString();
        }
    }

    record Error(String message, SpanData location) implements ParsingResult {
    }
}
