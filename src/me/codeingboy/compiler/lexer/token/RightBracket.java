package me.codeingboy.compiler.lexer.token;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class RightBracket extends Token {
    public final static int ID = 8;

    public RightBracket(String lexme) {
        super(lexme);
    }

    public RightBracket(String lexme, int linePos, int charPos) {
        super(lexme, linePos, charPos);
    }

    @Override
    public int getID() {
        return ID;
    }
}
