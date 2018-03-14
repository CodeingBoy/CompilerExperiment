package me.codeingboy.compiler.lexer;

import me.codeingboy.compiler.exception.LexerError;
import me.codeingboy.compiler.exception.UnexpectedCharacterError;
import me.codeingboy.compiler.lexer.token.EOFToken;
import me.codeingboy.compiler.lexer.token.Token;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TestDrive {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: me.codeingboy.compiler.abstractLexer [--version] file");
            return;
        }

        if(args[0].contains("--ver")){
            System.out.println("me.codeingboy.compiler.abstractLexer for Expression language 0.1");
            System.out.println("DFA Designed by Liang Weiying, implemented by Huang Feihao");
            return;
        }

        String fileName = args[args.length - 1];

        FileReader reader;
        try {
            reader = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("File " + fileName + " not found.");
            return;
        }

        System.out.println("Fetching tokens from " + fileName);
        long startTime = System.currentTimeMillis();

        AbstractLexer abstractLexer = new ConcreteLexer(new BufferedReader(reader));
        boolean stop = false;
        int errorCount = 0;
        List<LexerError> errorList = new ArrayList<>();
        while (!stop && errorCount < 100) {
            Token t;
            try {
                while ((t = abstractLexer.nextToken()) != null) {
                    if (t.getID() == EOFToken.ID) {
                        stop = true;
                        break;
                    }
                    System.out.println(t);
                }
            } catch (LexerError e) {
                errorList.add(e);
                errorCount++;
            }
        }
        long endTime = System.currentTimeMillis();
        errorList.forEach(lexerError -> System.out.println(lexerError));
        if(errorCount >= 100){
            System.out.println("Error: Too many errors");
        }

        System.out.println(String.format("Fetch complete, %d error(s), used %d ms", errorCount, endTime - startTime));
    }

}
