package me.codeingboy.compiler.exception;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class UnrecoverableReductionError extends ParserError {
    public UnrecoverableReductionError(String message, int linePosition, int charPosition) {
        super(message, linePosition, charPosition);
    }

    public UnrecoverableReductionError(String errorMessage, int linePos, int charPos, String currentLineContent) {
        super(errorMessage, linePos, charPos, currentLineContent);
    }
}
