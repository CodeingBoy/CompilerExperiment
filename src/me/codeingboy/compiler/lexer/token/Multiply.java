package me.codeingboy.compiler.lexer.token;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class Multiply extends Token {
    public final static int ID = 3;

    public Multiply(String lexme) {
        super(lexme);
    }

    public Multiply(String lexme, int linePos, int charPos) {
        super(lexme, linePos, charPos);
    }

    @Override
    public int getID() {
        return ID;
    }
}
