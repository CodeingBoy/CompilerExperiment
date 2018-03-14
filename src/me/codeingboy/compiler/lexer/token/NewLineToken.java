package me.codeingboy.compiler.lexer.token;

import me.codeingboy.compiler.parser.ast.Leaf;
import me.codeingboy.compiler.parser.ast.Node;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class NewLineToken extends Token {
    public final static int ID = 9;

    public NewLineToken(int linePos, int charPos) {
        super("\\n", linePos, charPos);
    }

    @Override
    public int getID() {
        return ID;
    }
}
