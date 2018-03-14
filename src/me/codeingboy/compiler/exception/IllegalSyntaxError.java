package me.codeingboy.compiler.exception;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class IllegalSyntaxError extends ParserError {
    public IllegalSyntaxError(String message, int linePosition, int charPosition, String currentLineContent) {
        super("Illegal syntax: " + message, linePosition, charPosition, currentLineContent);
    }
}
