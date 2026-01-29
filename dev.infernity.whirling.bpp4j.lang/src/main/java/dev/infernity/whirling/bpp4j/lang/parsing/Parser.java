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


    public static ParsingResult parse(Path fileName, String file, List<Token> tokenList){
        var parser = new Parser();
        parser.fileName = fileName;
        parser.contents = file;
        parser.tokenList = tokenList;
        parser.nestingLevel = 0;
        parser.nestingTokenIndexes = new ArrayList<>();
        parser.index = 0;
        parser.isFunctionDeclaration = false;

        return parser.parseLoop();
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
        Token token = tokenList.get(index);

        if (token instanceof Token.EndOfFile) {
            if (nestingLevel > 0) {
                int bracketIndex = nestingTokenIndexes.getLast();
                return new Node.Error("Unclosed bracket.", tokenList.get(bracketIndex).range());
            } else {
                return new Node.Complete(token.range());
            }
        }

        if (token instanceof Token.Error(String message, SpanData range)) {
            index++;
            return new Node.Error(message, range);
        }

        if (nestingLevel == 0) {
            return switch (token) {
                case Token.OuterString os -> {
                    index++;
                    yield new Node.OuterText(os.value(), os.range());
                }
                case Token.ShortVariableExpression sve -> {
                    index++;
                    yield new Node.VariableExpression(sve.variable(), sve.range());
                }
                case Token.OpenBracket _ -> parseFunction();
                default -> {
                    index++;
                    yield new Node.Error("Unexpected token '" + contents.substring(token.range().cursorStart(), token.range().cursorEnd()) + "' at top level. Only text and [...] blocks are allowed.", token.range());
                }
            };
        } else {
            index++;
            return new Node.Error("Parser bug: `parseInner` called while (somehow??) nested. Please report this!", token.range());
        }
    }

    private Node parseFunction() {
        Token openBracket = tokenList.get(index); // this is always an OpenBracket
        index++; // consume

        nestingLevel++;
        nestingTokenIndexes.add(index - 1);

        if (index >= tokenList.size()) {
            nestingLevel--;
            nestingTokenIndexes.removeLast();
            return new Node.Error("Unclosed bracket, expected function name after '['.", openBracket.range());
        }

        Token nameToken = tokenList.get(index);

        if (nameToken instanceof Token.CloseBracket) {
            index++; // consume
            nestingLevel--;
            nestingTokenIndexes.removeLast();
            return new Node.Error("Function call cannot be empty `[]`.", createSpan(openBracket, nameToken));
        }

        if (!(nameToken instanceof Token.UnquotedString)) {
            nestingLevel--;
            nestingTokenIndexes.removeLast();
            return new Node.Error("Function name must be an unquoted string.", nameToken.range());
        }
        index++; // consume

        String functionName = ((Token.UnquotedString) nameToken).value();
        var arguments = new ArrayList<Node>();

        while (true) {
            if (index >= tokenList.size()) {
                nestingLevel--;
                nestingTokenIndexes.removeLast();
                return new Node.Error("Unclosed bracket.", openBracket.range());
            }

            Token current = tokenList.get(index);
            if (current instanceof Token.CloseBracket) {
                index++; // consume
                nestingLevel--;
                nestingTokenIndexes.removeLast();
                return new Node.Function(functionName, arguments, createSpan(openBracket, current));
            }

            Node argument = parseExpression();
            if (argument instanceof Node.Error) {
                return argument;
            }
            arguments.add(argument);
        }
    }

    private Node parseExpression() {
        Token token = tokenList.get(index);

        if (token instanceof Token.Error(String message, SpanData range)) {
            index++;
            return new Node.Error(message, range);
        }

        return switch (token) {
            case Token.OpenBracket _ -> parseFunction();
            case Token.Number n -> {
                index++;
                yield new Node.Number(n.value(), n.range());
            }
            case Token.QuotedString qs -> {
                index++;
                yield new Node.StringNode(qs.value(), qs.range());
            }
            case Token.ShortVariableExpression sve -> {
                index++;
                yield new Node.VariableExpression(sve.variable(), sve.range());
            }
            case Token.UnquotedString us -> {
                index++;
                yield new Node.VariableExpression(us.value(), us.range());
            }
            case Token.EndOfFile _ -> {
                int bracketIndex = nestingTokenIndexes.getLast();
                yield new Node.Error("Unclosed bracket.", tokenList.get(bracketIndex).range());
            }
            default -> {
                index++;
                yield new Node.Error("Unexpected token while parsing arguments.", token.range());
            }
        };
    }

    public SpanData createSpan(Token start, Token end) {
        int startInt = start.range().cursorStart();
        int endInt = end.range().cursorEnd();
        return new SpanData(startInt, endInt, contents.substring(startInt, endInt), fileName);
    }
}