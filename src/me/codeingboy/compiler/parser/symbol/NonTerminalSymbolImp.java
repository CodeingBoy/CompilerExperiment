package me.codeingboy.compiler.parser.symbol;

import me.codeingboy.compiler.parser.ast.Node;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class NonTerminalSymbolImp implements NonTerminalSymbol {
    private int ID;
    private String name;
    private Node node;

    public NonTerminalSymbolImp(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    @Override
    public String toString() {
        return name + "{" +
                "node=" + node +
                '}';
    }

    @Override
    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    @Override
    public Node getNode() {
        return node;
    }

    @Override
    public void setNode(Node node) {
        this.node = node;
    }

    public NonTerminalSymbolImp createAnother() {
        return new NonTerminalSymbolImp(this.ID, this.name);
    }
}
