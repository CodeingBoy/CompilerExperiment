package me.codeingboy.compiler.lexer.token;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class Minus extends Token {
    public final static int ID = 2;

    public Minus(String lexme, int linePos, int charPos) {
        super(lexme, linePos, charPos);
    }

    public Minus(String lexme) {
        super(lexme);
    }

    @Override
    public int getID() {
        return ID;
    }
}
