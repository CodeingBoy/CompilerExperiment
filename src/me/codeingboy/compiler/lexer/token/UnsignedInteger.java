package me.codeingboy.compiler.lexer.token;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class UnsignedInteger extends NumberToken {
    public final static int ID = 5;

    public UnsignedInteger(String lexme, String type, double value, int linePos, int charPos) {
        super(lexme, type, value, linePos, charPos);
    }

    public UnsignedInteger(String lexme, String type, double value) {
        super(lexme, type, value);
    }

    public int getID() {
        return ID;
    }

}
