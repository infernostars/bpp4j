package dev.infernity.whirling.bpp4j.lang.parsing;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.infernity.whirling.bpp4j.lang.SpanData;
import dev.infernity.whirling.bpp4j.lang.tokenizer.Token;

import java.nio.file.Path;
import java.rmi.UnexpectedException;
import java.util.ArrayList;

public final class Parser {
    int nestingLevel;
    Path fileName;
    String contents;
    boolean isFunctionDeclaration;


    public static ParsingResult tokenize(Path fileName, String file){
        var parser = new Parser();
        parser.fileName = fileName;
        parser.contents = file;

        var res = parser.parseLoop();
        return res;
    }

    private ParsingResult parseLoop() {
        var list = new ArrayList<Node>();
        while(true) {
            Node node = parseOnce();
            if(node instanceof Node.Error(String message, SpanData range)) {
                return new ParsingResult.Error(message, range);
            }
            list.add(node);
        }

    }

    private Node parseOnce() {
        return parseInner();
    }

    private Node parseInner() {
        throw new RuntimeException("TODO");
    }

    public SpanData createSpan(Token start, Token end) {
        int startInt = start.range().cursorStart();
        int endInt = end.range().cursorEnd();
        return new SpanData(startInt, endInt, contents.substring(startInt, endInt), fileName);
    }
}
