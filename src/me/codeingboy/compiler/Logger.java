package me.codeingboy.compiler;

import me.codeingboy.compiler.exception.Error;
import me.codeingboy.compiler.lexer.token.Token;
import me.codeingboy.compiler.tokenbuffer.TokenBuffer;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class Logger {
    public static void d(String tag, String content) {
        System.out.println(tag + ": " + content);
    }

    public static void logPosition(String tag, TokenBuffer buffer, Token token, Error e) {
        logPosition(tag, buffer.getCurrentLineContent(), token.getLinePos(), token.getCharPos(), e);
    }

    public static void logPosition(String tag, String lineContent, int linePosition, int charPosition, Error e) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Error: (%d, %d) %s: %s", linePosition, charPosition, tag, e.getMessage()));
        if (lineContent != null) {
            builder.append("\n");
            builder.append(lineContent);
            for (int i = 0; i < charPosition - 1; i++) {
                builder.append(" ");
            }
            builder.append("^");
        }
        System.out.println(builder.toString());
    }
}
