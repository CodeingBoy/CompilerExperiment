package me.codeingboy.compiler.lexer.token;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public abstract class NumberToken extends Token {
    protected String type;
    protected double value; // TODO:

    public NumberToken(String lexme, int linePos, int charPos, String type, double value) {
        super(lexme, linePos, charPos);
        this.type = type;
        this.value = value;
    }

    public NumberToken(String lexme, String type, double value, int linePos, int charPos) {
        super(lexme, linePos, charPos);
        this.type = type;
        this.value = value;
    }

    public NumberToken(String lexme, String type, double value) {
        super(lexme);
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "(" + getID() + "," + lexme + "," + value + "," + type + ")";
    }
}
