package dev.infernity.whirling.bpp4j.lang.parsing;

import dev.infernity.whirling.bpp4j.lang.SpanData;
import dev.infernity.whirling.bpp4j.lang.tokenizer.Token;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class Parser {
    int nestingLevel;
    List<Integer> nestingTokenIndexes;
    Path fileName;
    String contents;
    List<Token> tokenList;
    boolean isFunctionDeclaration;
    int index;


    public static ParsingResult tokenize(Path fileName, String file, List<Token> tokenList){
        var parser = new Parser();
        parser.fileName = fileName;
        parser.contents = file;
        parser.tokenList = tokenList;

        var res = parser.parseLoop();
        return res;
    }

    private ParsingResult parseLoop() {
        var list = new ArrayList<Node>();
        while(true) {
            Node node = parseOnce();
            if(node instanceof Node.Error(String message, SpanData range)) {
                return new ParsingResult.Error(message, range);
            } else if (node instanceof Node.Complete) {
                return new ParsingResult.Success(list);
            }
            list.add(node);
        }

    }

    private Node parseOnce() {
        return parseInner();
    }

    private Node parseInner() {

    }

    public SpanData createSpan(Token start, Token end) {
        int startInt = start.range().cursorStart();
        int endInt = end.range().cursorEnd();
        return new SpanData(startInt, endInt, contents.substring(startInt, endInt), fileName);
    }
}
