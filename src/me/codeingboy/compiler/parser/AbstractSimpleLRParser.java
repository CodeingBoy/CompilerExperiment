package me.codeingboy.compiler.parser;

import me.codeingboy.compiler.Logger;
import me.codeingboy.compiler.exception.*;
import me.codeingboy.compiler.exception.Error;
import me.codeingboy.compiler.lexer.token.EOFToken;
import me.codeingboy.compiler.lexer.token.NewLineToken;
import me.codeingboy.compiler.lexer.token.Token;
import me.codeingboy.compiler.parser.production.Production;
import me.codeingboy.compiler.parser.symbol.NonTerminalSymbol;
import me.codeingboy.compiler.parser.symbol.Symbol;
import me.codeingboy.compiler.tokenbuffer.TokenBuffer;

import java.util.List;
import java.util.Stack;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public abstract class AbstractSimpleLRParser extends AbstractParser {
    private final static String TAG = AbstractSimpleLRParser.class.getSimpleName();
    private SimpleLRAnalysisTable table;
    protected Stack<Integer> stack = new Stack<>();
    protected Stack<Symbol> symbolStack = new Stack<>();
    private boolean shouldReset = false;

    public AbstractSimpleLRParser(TokenBuffer tokens) {
        super(tokens);
        this.table = getAnalysisTable();
        reset();
    }

    public void reset() {
        stack.clear();
        symbolStack.clear();
        AddressAllocator.reset();
        stack(0, getStartSymbol());
        System.out.println();
    }

    protected abstract SimpleLRAnalysisTable getAnalysisTable();

    protected abstract Production getProduction(int ID);

    protected abstract String getErrorMessage(int ID);

    protected abstract DynamicRecord getDynamicRecord(int ID);

    protected abstract Symbol getStartSymbol();

    protected abstract void onAccept();

    public void parse(List<Error> errorList) {
        while (true) {
            Logger.d(TAG, "Current state stack: " + stack);
            Logger.d(TAG, "Current symbol stack: " + symbolStack);
            try {
                if (!parseStep()) {
                    if (tokens.isEOF()) {
                        break;
                    }
                    tokens.nextToken();
                    reset();
                }
            } catch (EOFError e) {
                errorList.add(e);
                if (tokens.isEOF()) {
                    return;
                }
                reset();
            } catch (Error e) {
                errorList.add(e);
            }
        }
        Logger.d(TAG, "Accept state stack: " + stack);
    }

    private boolean parseStep() throws LexerError, ParserError {
        if (shouldReset) {
            reset();
            shouldReset = false;
        }

        Token token = tokens.peekToken();
        SimpleLRAnalysisTable.SimpleLRAnalysisRecord record = table.getAction(getCurrentState(), token.getID());
        if (record == null) {
            Error error = new ParserError(getErrorMessage(0), token.getLinePos(), token.getCharPos(), tokens.getCurrentLineContent());
            Logger.d(TAG, "Error occurred. Look ahead token: " + token.toString() + ".");
            Logger.logPosition(TAG, tokens, token, error);
            tokens.nextToken();
            throw (ParserError) error;
        }
        return action(token, record);
    }

    private boolean action(Token token, SimpleLRAnalysisTable.SimpleLRAnalysisRecord record) throws LexerError, ParserError {
        Logger.d(TAG, "Look ahead token: " + token.toString() + " on ("
                + token.getLinePos() + ", " + token.getCharPos() + "), action: " + record.toString());
        switch (record.type) {
            case STACK:
                stack(record.state, token);
                tokens.nextToken();
                break;
            case REDUCTION:
                reduction(token, record.production.createAnother().getNonTerminalSymbol(symbolStack), record.production.getTerminalCount());
                break;
            case ACCEPT:
                onAccept();
                return false;
            case ERROR:
                ParserError error = new IllegalSyntaxError(record.errorMessage, token.getLinePos(), token.getCharPos() - 1, tokens.getCurrentLineContent());
                tokens.nextToken();
                if (token.getID() == NewLineToken.ID) {
                    shouldReset = true;
                }
                throw error;
            case DYNAMIC:
                SimpleLRAnalysisTable.SimpleLRAnalysisRecord nextRecord = getDynamicRecord(record.state).getRecord(getCurrentState(), stack);
                return action(token, nextRecord);
        }

        return true;
    }

    public int getCurrentState() {
        return stack.peek();
    }

    private void stack(int state, Symbol symbol) {
        stack.push(state);
        symbolStack.push(symbol);
    }

    private void reduction(Token token, NonTerminalSymbol nonTerminalSymbol, int symbolCount) throws ParserError, LexerError {
        for (int i = 0; i < symbolCount; i++) {
            stack.pop();
            symbolStack.pop();
        }
        int currentState = stack.peek();
        int nextState = table.getGoto(currentState, nonTerminalSymbol.getID());
        Logger.d(TAG, "Popped " + symbolCount + " states. Current state is " + currentState + ". Pushing state " + nextState);
        if (nextState < 1) {
            onReductionError(token, nonTerminalSymbol);
            return;
        }
        stack(nextState, nonTerminalSymbol);
    }

    private void onReductionError(Token token, NonTerminalSymbol nonTerminalSymbol) throws ParserError, LexerError {
        Logger.d(TAG, "Reduction error occurred, try recovering...");
        int goalState = -1;
        for (int i = stack.size() - 1; i >= 0; i--) {
            int state = stack.get(i);
            int result = table.getGoto(state, nonTerminalSymbol.getID());
            if (result > 0) {
                goalState = result;
                break;
            }
        }
        if (goalState == -1) {
            throw new UnrecoverableReductionError("Unrecoverable reduction error occurred", token.getLinePos(), token.getCharPos(), tokens.getCurrentLineContent());
        }

        Token nextToken = tokens.peekToken();
        while (nextToken.getID() != EOFToken.ID) {
            SimpleLRAnalysisTable.SimpleLRAnalysisRecord record = table.getAction(getCurrentState(), token.getID());
            if (record != null) {
                break;
            }
            tokens.nextToken();
            nextToken = tokens.peekToken();
        }
        stack(goalState, nonTerminalSymbol);
        Logger.d(TAG, "Reduction error resolved, pushed state " + goalState);
    }

    @Override
    public String toString() {
        return "AbstractSimpleLRParser{" +
                "stack=" + stack +
                '}';
    }
}
