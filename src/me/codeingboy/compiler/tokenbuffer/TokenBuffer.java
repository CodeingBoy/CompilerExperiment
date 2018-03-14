package me.codeingboy.compiler.tokenbuffer;

import me.codeingboy.compiler.exception.LexerError;
import me.codeingboy.compiler.exception.UnexpectedCharacterError;
import me.codeingboy.compiler.lexer.AbstractLexer;
import me.codeingboy.compiler.lexer.token.EOFToken;
import me.codeingboy.compiler.lexer.token.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class TokenBuffer {
    private int tokenCount;
    private AbstractLexer lexer;
    private Token[] buffer;
    private int position = 0;
    private String currentLineContent;

    public TokenBuffer(AbstractLexer lexer, int tokenCount) {
        this.tokenCount = tokenCount;
        this.lexer = lexer;
        this.buffer = new Token[tokenCount];
        position = tokenCount;
    }

    private void refreshToken() throws LexerError {
        List<Token> newBuffer = new ArrayList<>(tokenCount);
        int fillPosition = 0;
        if (position < buffer.length) {
            // copy unused token
            System.arraycopy(buffer, position, newBuffer, 0, buffer.length - position);
            fillPosition = buffer.length - position;
        }
        for (; fillPosition < tokenCount; fillPosition++) {
            Token token= lexer.nextToken();
            newBuffer.add(token);
            if (token.getID() == EOFToken.ID) {
                break;
            }
        }
        buffer = newBuffer.toArray(new Token[0]);
        currentLineContent = lexer.getCurLineContent();
        position = 0;
    }

    public Token nextToken() throws LexerError {
        Token token = peekToken();
        position++;
        return token;
    }

    public Token peekToken() throws LexerError {
        if(position >= buffer.length){
            refreshToken();
        }
        return buffer[position];
    }

    public Token getToken(int aheadCount) throws LexerError {
        if(position >= buffer.length){
            refreshToken();
        }
        return buffer[position + aheadCount];
    }

    public String getCurrentLineContent() {
        return currentLineContent;
    }

    public boolean isEOF(){
        return lexer.isEOF();
    }
}
