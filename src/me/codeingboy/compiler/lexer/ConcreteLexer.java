package me.codeingboy.compiler.lexer;

import me.codeingboy.compiler.exception.EOFError;
import me.codeingboy.compiler.exception.LexerError;
import me.codeingboy.compiler.exception.UnexpectedCharacterError;
import me.codeingboy.compiler.lexer.token.*;

import java.io.BufferedReader;

public class ConcreteLexer extends AbstractLexer {
    boolean EOFTokenGiven = false;

    public ConcreteLexer(BufferedReader reader) {
        super(reader);
    }

    private boolean isSpace(char c) {
        return c == ' ' || c == '\n' || c == '\r' || c == '\t';
    }

    private boolean isNumberWithZero(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isNumber(char c) {
        return c >= '1' && c <= '9';
    }

    @Override
    public Token nextToken() throws LexerError {
        while (!isEOF()) {
            if (curChar == '\n') {
                int linePos = getCurLinePos(), curPos = getCurPos();
                consume();
                return new NewLineToken(linePos, curPos);
            }

            if (isSpace(curChar)) {
                consume();
                continue;
            }
            switch (curChar) {
                case '+':
                    consume();
                    return new Plus("+", getCurLinePos(), getCurPos());
                case '-':
                    consume();
                    return new Minus("-", getCurLinePos(), getCurPos());
                case '*':
                    consume();
                    return new Multiply("*", getCurLinePos(), getCurPos());
                case '/':
                    consume();
                    return new Divide("/", getCurLinePos(), getCurPos());
                case '(':
                    consume();
                    return new LeftBracket("(", getCurLinePos(), getCurPos());
                case ')':
                    consume();
                    return new RightBracket(")", getCurLinePos(), getCurPos());
                default:
                    if (isNumber(curChar)) {
                        StringBuilder builder = new StringBuilder();
                        do {
                            builder.append(curChar);
                            consume();
                        } while (isNumberWithZero(curChar));

                        if (curChar == '.') {
                            do {
                                builder.append(curChar);
                                consume();
                            } while (isNumberWithZero(curChar));

                            if (builder.charAt(builder.length() - 1) == '.') {
                                throw new UnexpectedCharacterError(getCurLinePos(), getCurPos(), "numbers", curChar, getCurLineContent());
                            }
                            return new UnsignedFloat(builder.toString(), "double", Double.parseDouble(builder.toString()), getCurLinePos(), getCurPos());
                        } else {
                            return new UnsignedInteger(builder.toString(), "int", Integer.parseInt(builder.toString()), getCurLinePos(), getCurPos());
                        }
                    }
                    if (curChar == '0') {
                        StringBuilder builder = new StringBuilder("0");
                        consume();

                        if (curChar == '.') {
                            do {
                                builder.append(curChar);
                                consume();
                            } while (isNumberWithZero(curChar));

                            if (builder.charAt(builder.length() - 1) == '.') {
                                throw new UnexpectedCharacterError(getCurLinePos(), getCurPos(), "numbers", curChar, getCurLineContent());
                            }
                            return new UnsignedFloat(builder.toString(), "double", Double.parseDouble(builder.toString()), getCurLinePos(), getCurPos());
                        } else {
                            if (isNumber(curChar)) {
                                throw new UnexpectedCharacterError(getCurLinePos(), getCurPos(), "numbers", curChar, getCurLineContent());
                            } else {
                                return new UnsignedInteger(builder.toString(), "int", Integer.parseInt(builder.toString()), getCurLinePos(), getCurPos());
                            }
                        }
                    }
            }
            char c = curChar;
            consume();
            throw new UnexpectedCharacterError(getCurLinePos(), getCurPos() - 1, "Illegal character " + c, getCurLineContent());
        }
        if(!EOFTokenGiven) {
            EOFTokenGiven = true;
            return new EOFToken();
        }else{
            throw new EOFError();
        }
    }
}
