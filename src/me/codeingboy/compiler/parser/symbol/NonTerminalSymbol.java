package me.codeingboy.compiler.parser.symbol;

import me.codeingboy.compiler.parser.ast.Node;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public interface NonTerminalSymbol extends Symbol, Cloneable {
    Node getNode();

    void setNode(Node node);
}
