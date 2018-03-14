package me.codeingboy.compiler.exception;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class Error extends Exception {
    protected String lineContent = null;
    protected int linePosition, charPosition;

    public Error(String message, int linePosition, int charPosition) {
        super(message);
        this.linePosition = linePosition;
        this.charPosition = charPosition;
    }

    public Error(String message, int linePosition, int charPosition, final String lineContent) {
        this(message, linePosition, charPosition);
        this.lineContent = lineContent;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Error: (%d, %d) %s", linePosition, charPosition, getMessage()));
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
