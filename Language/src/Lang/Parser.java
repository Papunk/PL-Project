package Lang;

import LangTools.*;

import java.io.*;
import java.util.*;

public class Parser {

    private final ArrayList<String> output = new ArrayList<>();
    private final ScopeSystem programScope = new ScopeSystem();
    private final List<Error> errors = new ArrayList<>();
    private final Stack<State> stateStack = new Stack<>();
    private final Deque<Token> currentSentence = new ArrayDeque<>();
    private Token currentToken = null;
    private int currentLine = 1;


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
        System.out.println(currentToken);
//        System.out.println(currentSentence);
        switch (stateStack.peek()) {
            case program: break;
            case var_def: var_def(); break;
            case if_stmt: break;
            default: break;
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
     * Writes the output to an external file
     */
    public void end() {
        if (!errors.isEmpty()) {
            String errorTrace = "";
            for (Error e: errors) {
                errorTrace += e.toString() + "\n";
            }
            System.out.print(errorTrace);
            return;
        }
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


    // Parser Functions


    private void scope() {}
    private void stmt() {}
    private void var_def() {
        switch (currentToken.type) {
            case ID:
                if (currentSentence.isEmpty()) currentSentence.addLast(currentToken);
                else addError(ErrorType.SyntaxError);
                break;
            case newline: newline();
            default: break;
        }
    }
    private void var_assign() {}
    private void func_dec() {}
    private void command() {}
    private void id() {}
    private void if_stmt() {}
    private void for_loop() {}
    private void while_loop() {}
    private void newline() {
        currentLine++;
    }


    // Function Toolkit


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

    private void addError(ErrorType t) {
        errors.add(new Error(currentLine, t));
    }



    // Internal Classes


    private static class Token {
        public String text;
        public TokenType type;

        public Token(String text, TokenType type) {
            this.text = text;
            this.type = type;
        }

        @Override
        public String toString() {
            if (type == TokenType.newline) return "'\\n' <"+ type.name() + ">";
            return "'" + text + "' <"+ type.name() + ">";
        }
    }

    private static class Error {
        public int line;
        public ErrorType type;

        public Error(int line, ErrorType type) {
            this.line = line;
            this.type = type;
        }

        @Override
        public String toString() {
            return type.name() + " at line " + Integer.toString(line);
        }
    }
}

/**
 * Note: architecture for functions:
 * default case -> wrong type of token for this state (add error; skip to the next line)
 * in other cases, add the relevant bit to the top of the deque
 * whe we reach the terminator, pop stateStack and remove the sentence from the bottom of the deque
 */