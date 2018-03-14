package me.codeingboy.compiler.lexer.token;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class Plus extends Token {
    public final static int ID = 1;

    public Plus(String lexme, int linePos, int charPos) {
        super(lexme, linePos, charPos);
    }

    public Plus(String lexme) {
        super(lexme);
    }

    @Override
    public int getID() {
        return ID;
    }
}
