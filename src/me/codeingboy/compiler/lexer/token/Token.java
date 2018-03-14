package me.codeingboy.compiler.lexer.token;

import me.codeingboy.compiler.parser.ast.Leaf;
import me.codeingboy.compiler.parser.ast.Node;
import me.codeingboy.compiler.parser.symbol.TerminalSymbol;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public abstract class Token implements TerminalSymbol {
    protected String lexme;
    protected int linePos, charPos;

    public Token(String lexme, int linePos, int charPos) {
        this.lexme = lexme;
        this.linePos = linePos;
        this.charPos = charPos;
    }

    public Token(String lexme) {
        this.lexme = lexme;
    }

    public Token() {
    }

    public String getLexme() {
        return lexme;
    }

    public int getLinePos() {

        return linePos;
    }

    public int getCharPos() {
        return charPos;
    }

    public abstract int getID();

    @Override
    public Node getNode() {
        return new Leaf(this);
    }

    @Override
    public String toString() {
        return "(" + getID() + "," + lexme + ",null,null)";
    }
}
