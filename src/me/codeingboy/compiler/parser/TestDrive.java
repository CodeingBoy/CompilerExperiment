package me.codeingboy.compiler.parser;

import me.codeingboy.compiler.exception.Error;
import me.codeingboy.compiler.lexer.AbstractLexer;
import me.codeingboy.compiler.lexer.ConcreteLexer;
import me.codeingboy.compiler.tokenbuffer.TokenBuffer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Description here...
 *
 * @author CodeingBoy
 * @version 1
 * @see
 */
public class TestDrive {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: me.codeingboy.compiler.abstractLexer [--version] file");
            return;
        }

        if (args[0].contains("--ver")) {
            System.out.println("me.codeingboy.compiler.abstractLexer for Expression language 0.1");
            return;
        }

        String fileName = args[args.length - 1];

        long startTime = System.currentTimeMillis();

        FileReader reader;
        try {
            reader = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("File " + fileName + " not found.");
            return;
        }

        AbstractLexer abstractLexer = new ConcreteLexer(new BufferedReader(reader));
        List<Error> errorList = new ArrayList<>();
        TokenBuffer tokenBuffer = new TokenBuffer(abstractLexer, 1);
        AbstractSimpleLRParser parser = new ConcreteParser(tokenBuffer);
        parser.parse(errorList);
        long endTime = System.currentTimeMillis();

        System.out.println();
        errorList.forEach(System.out::println);

        System.out.println("=======================================");
        System.out.println("Parse complete. " + errorList.size() + " error(s). Used " + (endTime - startTime) + " ms.");
    }
}
