package me.codeingboy.compiler.exception;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class UnexpectedCharacterError extends LexerError {
    public UnexpectedCharacterError(int line, int charPosition, String exceptChar, char actualChar) {
        this(line, charPosition, exceptChar, actualChar, null);
    }

    public UnexpectedCharacterError(int line, int charPosition, char exceptChar, char actualChar) {
        this(line, charPosition, exceptChar, actualChar, null);
    }

    public UnexpectedCharacterError(int line, int charPosition, String message) {
        this(line, charPosition, message, null);
    }

    public UnexpectedCharacterError(int line, int charPosition, String exceptChar, char actualChar, String lineContent) {
        super(String.format("Unexpected character: expected %s, got %c", exceptChar, actualChar), line, charPosition, lineContent);
    }

    public UnexpectedCharacterError(int line, int charPosition, char exceptChar, char actualChar, String lineContent) {
        super(String.format("Unexpected character: expected %c, got %c", exceptChar, actualChar), line, charPosition, lineContent);
    }

    public UnexpectedCharacterError(int line, int charPosition, String message, String lineContent) {
        super(String.format("Unexpected character: %s", message), line, charPosition, lineContent);
    }
}
