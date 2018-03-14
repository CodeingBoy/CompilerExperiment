package me.codeingboy.compiler.parser;

import java.util.Stack;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public abstract class DynamicRecord {
    public abstract SimpleLRAnalysisTable.SimpleLRAnalysisRecord getRecord(int currentState, Stack<Integer> stack);
}
