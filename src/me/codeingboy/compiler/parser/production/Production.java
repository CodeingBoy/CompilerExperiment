package me.codeingboy.compiler.parser.production;

import me.codeingboy.compiler.parser.symbol.NonTerminalSymbol;
import me.codeingboy.compiler.parser.symbol.NonTerminalSymbolImp;
import me.codeingboy.compiler.parser.symbol.Symbol;

import java.util.Stack;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public abstract class Production {
    private int ID;
    protected NonTerminalSymbol nonTerminalSymbol;
    private String productionString;
    private int terminalCount;

    public Production(int ID, NonTerminalSymbol nonTerminalSymbol, String productionString, int terminalCount) {
        this.ID = ID;
        this.nonTerminalSymbol = nonTerminalSymbol;
        this.productionString = productionString;
        this.terminalCount = terminalCount;
    }

    @Override
    public String toString() {
        return productionString;
    }

    public NonTerminalSymbol getNonTerminalSymbol(Stack<Symbol> symbols) {
        beforeReturnSymbol(nonTerminalSymbol, symbols);
        return nonTerminalSymbol;
    }

    public int getTerminalCount() {
        return terminalCount;
    }

    public int getID() {
        return ID;
    }

    public abstract void beforeReturnSymbol(NonTerminalSymbol nonTerminalSymbol, Stack<Symbol> symbols);

    public Production createAnother() {
        return new Production(this.ID, ((NonTerminalSymbolImp) this.nonTerminalSymbol).createAnother(), this.productionString, this.terminalCount) {
            @Override
            public void beforeReturnSymbol(NonTerminalSymbol nonTerminalSymbol, Stack<Symbol> symbols) {
                Production.this.beforeReturnSymbol(nonTerminalSymbol, symbols);
            }
        };
    }
}
