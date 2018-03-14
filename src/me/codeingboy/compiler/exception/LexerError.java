package me.codeingboy.compiler.exception;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class LexerError extends Error {

    public LexerError(String message, int linePosition, int charPosition, String lineContent) {
        super(message, linePosition, charPosition, lineContent);
    }

    public LexerError(String message, int line, int charPosition) {
        super(message, line, charPosition);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Error: (%d, %d) Lexer: %s", linePosition, charPosition, getMessage()));
        if (lineContent != null) {
            builder.append("\n");
            builder.append(lineContent);
            for (int i = 0; i < charPosition - 1; i++) {
                builder.append(" ");
            }
            builder.append("^");
        }
        return builder.toString();
    }
}
