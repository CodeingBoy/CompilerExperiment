package me.codeingboy.compiler.lexer.token;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class EOFToken extends Token {
    public final static int ID = 9;

    @Override
    public int getID() {
        return ID;
    }
}
