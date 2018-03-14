package me.codeingboy.compiler.parser.ast;

import me.codeingboy.compiler.lexer.token.Token;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class Leaf extends Node {
    private Token token;

    public Leaf(Token token) {
        super(token, token.getClass().getSimpleName());
        this.token = token;
        properties.put("address", token.getLexme());
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Leaf{" + token + '}';
    }

    @Override
    public String getProperty(String key) {
        if (key.equals("value")) {
            return token.getLexme();
        }
        return super.getProperty(key);
    }

    @Override
    public String doGenerateCode() {
        return "";
    }
}
