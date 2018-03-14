package me.codeingboy.compiler.lexer.token;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class LeftBracket extends Token{
    public final static int ID = 7;

    public LeftBracket(String lexme) {
        super(lexme);
    }

    public LeftBracket(String lexme, int linePos, int charPos) {
        super(lexme, linePos, charPos);
    }

    @Override
    public int getID() {
        return ID;
    }
}
