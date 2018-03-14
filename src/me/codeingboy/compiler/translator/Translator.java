package me.codeingboy.compiler.translator;

import me.codeingboy.compiler.parser.ast.Node;

import java.util.List;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class Translator {

    public void translate(Node node) {
        List<String> codes = node.generateCode();
        int num = 0;
        for (String code : codes) {
            System.out.println(num++ + ": " + code);
        }
    }

}
