package me.codeingboy.compiler.parser;

import me.codeingboy.compiler.parser.ast.Node;
import me.codeingboy.compiler.parser.production.Production;
import me.codeingboy.compiler.parser.symbol.NonTerminalSymbol;
import me.codeingboy.compiler.parser.symbol.NonTerminalSymbolImp;
import me.codeingboy.compiler.parser.symbol.Symbol;
import me.codeingboy.compiler.tokenbuffer.TokenBuffer;
import me.codeingboy.compiler.translator.Translator;

import java.util.Stack;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class ConcreteParser extends AbstractSimpleLRParser {
    private final static int[][][] ACTION_TABLE = {
            {{}, {1, 5}, {}, {}, {1, 6}, {1, 6}, {1, 7}, {4, 1}, {}},
            {{4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 2}, {4, 1}, {3}},
            {{1, 8}, {1, 9}, {}, {}, {1, 6}, {1, 6}, {1, 7}, {2, 3}, {2, 3}},
            {{2, 6}, {2, 6}, {1, 10}, {1, 11}, {}, {}, {}, {2, 6}, {2, 6}},
            {{2, 7}, {2, 7}, {2, 7}, {2, 7}, {}, {}, {}, {2, 7}, {2, 7}},
            {{}, {1, 5}, {}, {}, {1, 6}, {1, 6}, {1, 7}, {}, {}},
            {{2, 7}, {2, 7}, {2, 7}, {2, 7}, {}, {}, {}, {2, 7}, {2, 7}},
            {{}, {1, 5}, {}, {}, {1, 6}, {1, 6}, {1, 7}, {}, {}},
            {{4, 3}, {1, 9}, {4, 3}, {4, 3}, {1, 6}, {1, 6}, {1, 7}, {}, {}},
            {{}, {1, 9}, {}, {}, {1, 6}, {1, 6}, {1, 7}, {}, {}},
            {{4, 3}, {1, 5}, {4, 3}, {4, 3}, {1, 6}, {1, 6}, {1, 7}, {}, {}},
            {{}, {1, 5}, {}, {}, {1, 6}, {1, 6}, {1, 7}, {}, {}},
            {{2, 8}, {2, 8}, {2, 8}, {2, 8}, {}, {}, {}, {2, 8}, {2, 8}},
            {{2, 7}, {2, 7}, {2, 7}, {2, 7}, {}, {}, {}, {2, 7}, {2, 7}},
            {{4, 1}, {4, 1}, {4, 1}, {4, 1}, {4, 1}, {4, 1}, {4, 1}, {1, 19}, {4, 1}},
            {{}, {}, {}, {}, {}, {}, {}, {2, 1}, {2, 1}},
            {{}, {}, {}, {}, {}, {}, {}, {2, 2}, {2, 2}},
            {{2, 4}, {2, 4}, {}, {}, {}, {}, {}, {2, 4}, {2, 4}},
            {{2, 5}, {2, 5}, {}, {}, {}, {}, {}, {2, 5}, {2, 5}},
            {{2, 10}, {2, 10}, {2, 10}, {2, 10}, {}, {}, {}, {2, 10}, {2, 10}},
            {{5, 1}, {5, 1}, {5, 1}, {5, 1}, {}, {}, {}, {5, 1}, {5, 1}},
            {{2, 7}, {2, 7}, {2, 7}, {2, 7}, {}, {}, {}, {2, 7}, {2, 7}},
            {{2, 9}, {2, 9}, {2, 9}, {2, 9}, {}, {}, {}, {2, 9}, {2, 9}},
    };
    private final static int[][] GOTO_TABLE = {
            {1, 2, 3, 4},
            {},
            {-1, 2, -1, -1},
            {},
            {},
            {-1, -1, 12, 13},
            {},
            {14, 2, 3, 3},
            {15, 2, 3, 4},
            {16, 2, 20, 4},
            {-1, 17, 3, 4},
            {-1, 18, 3, 4},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
    };
    private final static NonTerminalSymbolImp[] NON_TERMINAL_SYMBOLS = {
            new NonTerminalSymbolImp(1, "EXPR"),
            new NonTerminalSymbolImp(2, "TERM"),
            new NonTerminalSymbolImp(3, "FACTOR"),
            new NonTerminalSymbolImp(4, "CALCVAL"),
    };
    private final static Production[] PRODUCTIONS = {
            new Production(1, NON_TERMINAL_SYMBOLS[0].createAnother(), "EXPR->TERM+EXPR", 3) {
                @Override
                public void beforeReturnSymbol(NonTerminalSymbol nonTerminalSymbol, Stack<Symbol> symbols) {
                    Node node = new Node(nonTerminalSymbol, "+") {
                        @Override
                        public String doGenerateCode() {
                            Node thisNode = this, leftNode = getNode(0), rightNode = getNode(1);
                            return generateFormattedCode(thisNode.getOperator(),
                                    leftNode.getProperty("address"), rightNode.getProperty("address"), thisNode.getProperty("address"));
                        }
                    };
                    Node left = symbols.get(symbols.size() - 3).getNode(), right = symbols.get(symbols.size() - 1).getNode();
                    node.addNode(left);
                    node.addNode(right);

                    node.setProperty("value", left.getDoubleProperty("value") + right.getDoubleProperty("value"));
                    node.setProperty("address", AddressAllocator.allocateAddress());

                    nonTerminalSymbol.setNode(node);
                }
            },
            new Production(2, NON_TERMINAL_SYMBOLS[0].createAnother(), "EXPR->TERM-EXPR", 3) {
                @Override
                public void beforeReturnSymbol(NonTerminalSymbol nonTerminalSymbol, Stack<Symbol> symbols) {
                    Node node = new Node(nonTerminalSymbol, "-") {
                        @Override
                        public String doGenerateCode() {
                            Node thisNode = this, leftNode = getNode(0), rightNode = getNode(1);
                            return generateFormattedCode(thisNode.getOperator(),
                                    leftNode.getProperty("address"), rightNode.getProperty("address"), thisNode.getProperty("address"));
                        }
                    };
                    Node left = symbols.get(symbols.size() - 3).getNode(), right = symbols.get(symbols.size() - 1).getNode();
                    node.addNode(left);
                    node.addNode(right);

                    node.setProperty("value", left.getDoubleProperty("value") - right.getDoubleProperty("value"));
                    node.setProperty("address", AddressAllocator.allocateAddress());

                    nonTerminalSymbol.setNode(node);
                }
            },
            new Production(3, NON_TERMINAL_SYMBOLS[0].createAnother(), "EXPR->TERM", 1) {
                @Override
                public void beforeReturnSymbol(NonTerminalSymbol nonTerminalSymbol, Stack<Symbol> symbols) {
                    nonTerminalSymbol.setNode(symbols.peek().getNode());
                }
            },
            new Production(4, NON_TERMINAL_SYMBOLS[1].createAnother(), "TERM->FACTOR*TERM", 3) {
                @Override
                public void beforeReturnSymbol(NonTerminalSymbol nonTerminalSymbol, Stack<Symbol> symbols) {
                    Node node = new Node(nonTerminalSymbol, "*") {
                        @Override
                        public String doGenerateCode() {
                            Node thisNode = this, leftNode = getNode(0), rightNode = getNode(1);
                            return generateFormattedCode(thisNode.getOperator(),
                                    leftNode.getProperty("address"), rightNode.getProperty("address"), thisNode.getProperty("address"));
                        }
                    };
                    Node left = symbols.get(symbols.size() - 3).getNode(), right = symbols.get(symbols.size() - 1).getNode();
                    node.addNode(left);
                    node.addNode(right);

                    node.setProperty("value", left.getDoubleProperty("value") * right.getDoubleProperty("value"));
                    node.setProperty("address", AddressAllocator.allocateAddress());

                    nonTerminalSymbol.setNode(node);
                }
            },
            new Production(5, NON_TERMINAL_SYMBOLS[1].createAnother(), "TERM->FACTOR/TERM", 3) {
                @Override
                public void beforeReturnSymbol(NonTerminalSymbol nonTerminalSymbol, Stack<Symbol> symbols) {
                    Node node = new Node(nonTerminalSymbol, "/") {
                        @Override
                        public String doGenerateCode() {
                            Node thisNode = this, leftNode = getNode(0), rightNode = getNode(1);
                            return generateFormattedCode(thisNode.getOperator(),
                                    leftNode.getProperty("address"), rightNode.getProperty("address"), thisNode.getProperty("address"));
                        }
                    };
                    Node left = symbols.get(symbols.size() - 3).getNode(), right = symbols.get(symbols.size() - 1).getNode();
                    node.addNode(left);
                    node.addNode(right);

                    node.setProperty("value", left.getDoubleProperty("value") / right.getDoubleProperty("value"));
                    node.setProperty("address", AddressAllocator.allocateAddress());

                    nonTerminalSymbol.setNode(node);
                }
            },
            new Production(6, NON_TERMINAL_SYMBOLS[1].createAnother(), "TERM->FACTOR", 1) {
                @Override
                public void beforeReturnSymbol(NonTerminalSymbol nonTerminalSymbol, Stack<Symbol> symbols) {
                    nonTerminalSymbol.setNode(symbols.peek().getNode());
                }
            },
            new Production(7, NON_TERMINAL_SYMBOLS[2].createAnother(), "FACTOR->CALCVAL", 1) {
                @Override
                public void beforeReturnSymbol(NonTerminalSymbol nonTerminalSymbol, Stack<Symbol> symbols) {
                    nonTerminalSymbol.setNode(symbols.peek().getNode());
                }
            },
            new Production(8, NON_TERMINAL_SYMBOLS[2].createAnother(), "FACTOR->-FACTOR", 2) {
                @Override
                public void beforeReturnSymbol(NonTerminalSymbol nonTerminalSymbol, Stack<Symbol> symbols) {
                    Node node = new Node(nonTerminalSymbol, "-") {
                        @Override
                        public String doGenerateCode() {
                            Node thisNode = this, leftNode = getNode(0);
                            return generateFormattedCode(thisNode.getOperator(),
                                    leftNode.getProperty("address"), null, thisNode.getProperty("address"));
                        }
                    };
                    node.addNode(symbols.peek().getNode());
                    node.setProperty("value", -(symbols.peek().getNode().getDoubleProperty("value")));
                    node.setProperty("address", AddressAllocator.allocateAddress());

                    nonTerminalSymbol.setNode(node);
                }
            },
            new Production(9, NON_TERMINAL_SYMBOLS[3].createAnother(), "CALCVAL->CONSTANT", 1) {
                @Override
                public void beforeReturnSymbol(NonTerminalSymbol nonTerminalSymbol, Stack<Symbol> symbols) {
                    nonTerminalSymbol.setNode(symbols.peek().getNode());
                }
            },
            new Production(10, NON_TERMINAL_SYMBOLS[3].createAnother(), "CALCVAL->(EXPR)", 3) {
                @Override
                public void beforeReturnSymbol(NonTerminalSymbol nonTerminalSymbol, Stack<Symbol> symbols) {
                    nonTerminalSymbol.setNode(symbols.get(symbols.size() - 2).getNode());
                }
            },
    };
    private final static String[] ERROR_MESSAGES = {
            "Parse failed",
            "Missing right bracket ')'",
            "Unexpected end of line",
            "Illegal operand",
    };
    private final static DynamicRecord[] DYNAMIC_RECORD = {
            new DynamicRecord() {
                @Override
                public SimpleLRAnalysisTable.SimpleLRAnalysisRecord getRecord(int currentState, Stack<Integer> stack) {
                    if (stack.get(stack.size() - 3) == 2) { // EXPR->TERM-EXPR
                        return new SimpleLRAnalysisTable.SimpleLRAnalysisRecord(PRODUCTIONS[5]);
                    } else {
                        return new SimpleLRAnalysisTable.SimpleLRAnalysisRecord(PRODUCTIONS[7]);
                    }
                }
            },
    };

    public ConcreteParser(TokenBuffer tokens) {
        super(tokens);
    }

    @Override
    protected SimpleLRAnalysisTable getAnalysisTable() {
        return SimpleLRAnalysisTable.fromArray(ACTION_TABLE, GOTO_TABLE, this);
    }

    @Override
    protected Production getProduction(int ID) {
        return PRODUCTIONS[ID - 1];
    }

    @Override
    protected String getErrorMessage(int ID) {
        return ERROR_MESSAGES[ID];
    }

    @Override
    protected DynamicRecord getDynamicRecord(int ID) {
        return DYNAMIC_RECORD[ID - 1];
    }

    @Override
    protected Symbol getStartSymbol() {
        return new NonTerminalSymbolImp(0, "START");
    }

    @Override
    protected void onAccept() {
        System.out.println();

        System.out.println("Origin data structure:");
        Node node = symbolStack.peek().getNode();
        System.out.println(node);
        System.out.println();

        System.out.println("Pretty printed abstract syntax tree:");
        node.prettyPrint("", false);
        System.out.println();

        System.out.println("Code list:");
        Translator translator = new Translator();
        translator.translate(node);
    }
}
