package me.codeingboy.compiler.parser.ast;

import me.codeingboy.compiler.parser.symbol.Symbol;

import java.util.*;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public abstract class Node {
    protected Map<String, String> properties = new HashMap<>();
    private Symbol symbol;
    private String operator;
    private List<Node> nodes = new ArrayList<>();

    public Node(Symbol symbol, String operator) {
        this.symbol = symbol;
        this.operator = operator;
    }

    public static String generateFormattedCode(String operator, String leftAddress, String rightAddress, String resultAddress) {
        return String.format("(%s, %s, %s, %s)", operator, leftAddress, rightAddress, resultAddress);
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void setProperty(String key, String value) {
        properties.put(key, value);
    }

    public void setProperty(String key, int value) {
        properties.put(key, String.valueOf(value));
    }

    public void setProperty(String key, double value) {
        properties.put(key, String.valueOf(value));
    }

    public int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }

    public double getDoubleProperty(String key) {
        return Double.parseDouble(getProperty(key));
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(
                operator + "{" +
                        "nodes=" + nodes +
                        ", properties=" + properties +
                        '}');
        return builder.toString();
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    public Node getNode(int index) {
        return nodes.get(index);
    }

    public List<String> generateCode() {
        List<String> codeList = new ArrayList<>();
        for (Node node : nodes) {
            List<String> code = node.generateCode();
            codeList.addAll(code);
        }
        String code = doGenerateCode();
        if(!code.isEmpty()){
            codeList.add(code);
        }
        return codeList;
    }

    public void prettyPrint(String prefix, boolean isTail){
        System.out.println(prefix + (isTail ? "└── " : "├── ") + this.getOperator() + "(" + this.getProperty("value") + ")");
        for (int i = 0; i < nodes.size() - 1; i++) {
            nodes.get(i).prettyPrint(prefix + (isTail ? "    " : "│   "), false);
        }
        if (nodes.size() > 0) {
            nodes.get(nodes.size() - 1)
                    .prettyPrint(prefix + (isTail ?"    " : "│   "), true);
        }
    }

    protected abstract String doGenerateCode();
}
