package Lang;

import LangTools.*;

import java.io.*;
import java.util.*;

public class Parser {

    private final ArrayList<String> output = new ArrayList<>();
    private final ScopeSystem programScope = new ScopeSystem();
    private final Stack<String> errorStack = new Stack<>();
    private final Stack<State> stateStack = new Stack<>();
    private final Deque<Token> currentSentence = new ArrayDeque<>();
    private Token currentToken = null;
    private Token prevToken = null;


    /**
     * Constructor
     */
    public Parser() {
        addHeader();
        stateStack.push(State.program);
    }

    /**
     * Receives matched input as token from the lexer from the lexer
     * @param text input from the lexer
     * @param state the state of the parser
     */
    public void receive(String text, TokenType type, State state) {
        setState(state);
        receive(text, type);
    }

    /**
     * Receives matched input without changing state
     * @param text input from the lexer
     */
    public void receive(String text, TokenType type) {
        currentToken = new Token(text, type);
        switch (stateStack.peek()) {
            case var_def: var_def();
            case if_stmt: ;
            default: // throw syntax error;
        }
    }

    /**
     * Sets the current parser state
     * @param state the state of the parser
     */
    public void setState(State state) {
        stateStack.push(state);
    }

    /**
     * Drops the current state
     */
    public void dropState() {
        // TODO dont destroy the base state
        stateStack.pop();
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

    /**
     * Empties the current sentence
     */
    private void clearSentence() {
        currentSentence.clear();
    }

    /**
     * Writes the output to an external file
     */
    public void end() {
        // TODO print error trace
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


     // ======================= Recursive Descent Parser Functions ======================= //

    private void scope() {}
    private void stmt() {}
    private void var_def() {
        switch (currentToken.type) {
            case ID:;
            case n:;
            default: //error ;
        }
    }
    private void var_assign() {}
    private void func_dec() {}
    private void command() {}
    private void id() {}
    private void if_stmt() {}
    private void for_loop() {}
    private void while_loop() {}


    private static class Token {
        public String text;
        public TokenType type;

        public Token(String text, TokenType type) {
            this.text = text;
            this.type = type;
        }

    }
}

/**
 * Note: architecture for functions:
 * default case -> wrong type of token for this state
 * in other cases, add the relevant bit to the top of the deque
 * whe we reach the terminator, pop stateStack and remove the sentence from the bottom of the deque
 */