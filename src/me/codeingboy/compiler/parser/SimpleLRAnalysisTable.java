package me.codeingboy.compiler.parser;

import me.codeingboy.compiler.parser.production.Production;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class SimpleLRAnalysisTable {
    private int stateCount, tokenCount, nonTerminalSymbolCount;
    private SimpleLRAnalysisRecord[][] actionTable;
    private int[][] gotoTable;

    public SimpleLRAnalysisTable(int stateCount, int tokenCount, int nonTerminalSymbolCount) {
        this.stateCount = stateCount;
        this.tokenCount = tokenCount;
        this.nonTerminalSymbolCount = nonTerminalSymbolCount;
        this.actionTable = new SimpleLRAnalysisRecord[stateCount][tokenCount];
        this.gotoTable = new int[stateCount][nonTerminalSymbolCount];
    }

    public static SimpleLRAnalysisTable fromArray(int[][][] actionArray, int[][] gotoArray, AbstractSimpleLRParser parser) {
        if (actionArray.length != gotoArray.length) {
            throw new IllegalArgumentException("Token count between two array is inconsistent");
        }

        int stateCount = actionArray.length, tokenCount = actionArray[0].length;
        int nonTerminalSymbolCount = gotoArray[0].length;

        SimpleLRAnalysisTable table = new SimpleLRAnalysisTable(stateCount, tokenCount, nonTerminalSymbolCount);
        for (int state = 0; state < actionArray.length; state++) {
            for (int tokenID = 0; tokenID < actionArray[state].length; tokenID++) {
                int[] arrayRecord = actionArray[state][tokenID];
                if (arrayRecord.length == 0) {
                    continue;
                }
                SimpleLRAnalysisRecord tableRecord;
                switch (arrayRecord[0]) {
                    case 1: // stack
                        assert arrayRecord.length >= 2;
                        tableRecord = new SimpleLRAnalysisRecord(arrayRecord[1]);
                        break;
                    case 2: // reduction
                        assert arrayRecord.length >= 2;
                        tableRecord = new SimpleLRAnalysisRecord(parser.getProduction(arrayRecord[1]));
                        break;
                    case 3: // accept
                        tableRecord = SimpleLRAnalysisRecord.ACCEPT;
                        break;
                    case 4: // error
                        tableRecord = new SimpleLRAnalysisRecord(parser.getErrorMessage(arrayRecord[1]));
                        break;
                    case 5:
                        tableRecord = new SimpleLRAnalysisRecord(SimpleLRAnalysisRecord.TYPE.DYNAMIC, arrayRecord[1], null);
                        break;
                    default:
                        throw new IllegalArgumentException("Illegal input: record type " + arrayRecord[0]);
                }
                table.addAction(state, tokenID, tableRecord);
            }

            if (gotoArray[state].length < 1) {
                continue;
            }
            for (int nonTerminalID = 0; nonTerminalID < gotoArray[state].length; nonTerminalID++) {
                int goalState = gotoArray[state][nonTerminalID];
                if (goalState == -1) {
                    continue;
                }
                table.addGoto(state, nonTerminalID, goalState);
            }
        }
        return table;
    }

    /**
     * Return a record corresponding current state and look ahead token
     *
     * @param currentState current state
     * @param tokenID      look ahead token's ID
     * @return a record object if valid, or null which meaning error occurred
     */
    public SimpleLRAnalysisRecord getAction(int currentState, int tokenID) {
        return actionTable[currentState][tokenID - 1];
    }

    public int getGoto(int currentState, int nonTerminalSymbolID) {
        return gotoTable[currentState][nonTerminalSymbolID - 1];
    }


    public void addAction(int state, int tokenID, SimpleLRAnalysisRecord record) {
        actionTable[state][tokenID] = record;
    }

    public void addGoto(int state, int nonTerminalSymbolID, int goalState) {
        gotoTable[state][nonTerminalSymbolID] = goalState;
    }

    public static class SimpleLRAnalysisRecord {
        public final static SimpleLRAnalysisRecord ACCEPT = new SimpleLRAnalysisRecord(TYPE.ACCEPT);

        TYPE type;
        int state = -1;
        Production production;
        String errorMessage;

        public SimpleLRAnalysisRecord(TYPE type) {
            this.type = type;
        }

        public SimpleLRAnalysisRecord(Production production) {
            this.type = TYPE.REDUCTION;
            this.production = production;
        }

        public SimpleLRAnalysisRecord(TYPE type, int state, Production production) {
            this.type = type;
            this.state = state;
            this.production = production;
        }

        public SimpleLRAnalysisRecord(int state) {
            this.type = TYPE.STACK;
            this.state = state;
        }

        public SimpleLRAnalysisRecord(String errorMessage) {
            this.type = TYPE.ERROR;
            this.errorMessage = errorMessage;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            switch (type) {
                case ACCEPT:
                    builder.append("accept");
                    break;
                case REDUCTION:
                    builder.append("reduce by production " + production);
                    break;
                case STACK:
                    builder.append("consume token and push state " + state);
                    break;
                case ERROR:
                    builder.append("throw error whose message is \"" + errorMessage + "\"");
                    break;
                case DYNAMIC:
                    builder.append("determine next action according current state and stack");
                    break;
            }
            return builder.toString();
        }

        enum TYPE {
            STACK, REDUCTION, ACCEPT, ERROR, DYNAMIC
        }
    }

}
