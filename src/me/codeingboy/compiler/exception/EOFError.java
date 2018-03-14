package me.codeingboy.compiler.exception;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class EOFError extends LexerError {
    public EOFError() {
        super("Unexpected EOF", 0, 0);
    }
}
