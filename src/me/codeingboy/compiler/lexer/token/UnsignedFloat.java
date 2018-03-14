package me.codeingboy.compiler.lexer.token;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class UnsignedFloat extends NumberToken {
    public final static int ID = 6;

    public UnsignedFloat(String lexme, String type, double value) {
        super(lexme, type, value);
    }

    public UnsignedFloat(String lexme, String type, double value, int linePos, int charPos) {
        super(lexme, linePos, charPos, type, value);
    }


    @Override
    public int getID() {
        return ID;
    }
}

