package me.codeingboy.compiler.lexer.token;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class Divide extends Token {
    public final static int ID = 4;

    public Divide(String lexme, int linePos, int charPos) {
        super(lexme, linePos, charPos);
    }

    public Divide(String lexme) {
        super(lexme);
    }

    @Override
    public int getID() {
        return ID;
    }
}
