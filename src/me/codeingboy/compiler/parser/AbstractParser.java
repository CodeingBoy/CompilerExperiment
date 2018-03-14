package me.codeingboy.compiler.parser;

import me.codeingboy.compiler.tokenbuffer.TokenBuffer;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class AbstractParser {
    protected  TokenBuffer tokens;

    public AbstractParser(TokenBuffer tokens) {
        this.tokens = tokens;
    }
}
