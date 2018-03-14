package me.codeingboy.compiler.lexer;

import me.codeingboy.compiler.exception.LexerError;
import me.codeingboy.compiler.exception.UnexpectedCharacterError;
import me.codeingboy.compiler.lexer.token.Token;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class AbstractLexer {
    protected char curChar;
    private BufferedReader reader;
    private int curPos = 0;
    private int curLinePos = 0;
    private String curLine;
    private boolean isEOF = false;
    private String previousLine;

    public AbstractLexer(BufferedReader reader) {
        this.reader = reader;
        initial();
    }

    private void initial() {
        lineFeed();
        curChar = curLine.charAt(0);
    }

    public abstract Token nextToken() throws LexerError;

    public boolean isEOF() {
        return isEOF;
    }

    protected void consume() {
        if (isEOF) {
            return;
        }
        curPos++;
        if (curPos >= curLine.length()) {
            lineFeed();
            if (isEOF) {
                return;
            }
        }
        curChar = curLine.charAt(curPos);
    }

    private void lineFeed() {
        curLinePos++;
        curPos = 0;
        try {
            previousLine = curLine;
            curLine = reader.readLine();
            if (curLine == null) {
                isEOF = true;
            }
            curLine += "\n";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getCurPos() {
        return curPos + 1;
    }

    public int getCurLinePos() {
        return curLinePos;
    }

    public String getCurLineContent(){
        if(curPos == 0){
            return new String(previousLine);
        }
        return new String(curLine);
    }

    protected boolean match(char c) throws UnexpectedCharacterError {
        if (c == curChar) {
            consume();
        }
        throw new UnexpectedCharacterError(curLinePos, curPos, c, curChar);
    }
}
