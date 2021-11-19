package Lang;

import LangTools.*;

import java.io.*;
import java.util.*;

public class Parser {

    private ArrayList<String> output = new ArrayList<>();
    private final ScopeSystem programScope = new ScopeSystem();
    private final Stack<String> errorStack = new Stack<String>();


    public Parser() {
        addHeader();
    }


    /**
     * Receives matched input as token from the lexer from the lexer
     * @param token input from the lexer
     * @param type the type of token that is being received
     */
    public void receive(Token token, State type) {
        switch (type) {
            case var_def: var_def(token.text);
            case if_stmt: ;
        }
        output.add(token.text);
    }


    /**
     * Wrapper method for receiving text instead of tokens
     */
    public void receive(String text, State type) {
        receive(new Token(text), type);
    }


    /**
     * Writes the output to an external file
     */
    public void end() {
        try {
            FileWriter file = new FileWriter("src/Lang/outputTest.txt");
            for (String s: output) {
                file.write(s);
            }
            file.close();
        }
        catch(Exception e) {
            // do something
        }
    }


    /**
     * Adds crucial imports to the file
     */
    private void addHeader() {
        output.add("import java.io.*\n");
        output.add("import java.util.*\n");
        // add our custom packages
        output.add("\n");
    }

     // ======================= Recursive Descent Parser Functions ======================= //

    private void program(String s) {}
    private void scope(String s) {}
    private void stmt(String s) {}
    private void var_def(String s) {}
    private void var_assign(String s) {}
    private void func_dec(String s) {}
    private void command(String s) {}
    private void id(String s) {}
    private void if_stmt(String s) {}
    private void for_loop(String s) {}
    private void while_loop(String s) {}
}
