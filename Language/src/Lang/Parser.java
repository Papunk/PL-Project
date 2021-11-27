package Lang;

import LangTools.*;

import java.io.*;
import java.util.*;

public class Parser {

    private final List<String> output = new ArrayList<>();
    private final ScopeSystem scopes = new ScopeSystem();
    private final List<Error> errors = new ArrayList<>();
    private final Stack<State> states = new Stack<>();
    private final List<Token> sentence = new ArrayList<>();
    private Token token = null;
    private int line = 1;


    public Parser() {
        addToOutput("import java.io.*", true);
        addToOutput("import java.util.*", true);
        // add our custom packages
        addToOutput("public class Main {", false);
        scopes.enterScope();
        addToOutput("public static void main(String[] args) {", false);
        scopes.enterScope();
        states.push(State.program);
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
        token = new Token(text, type);
        System.out.println(token);
//        System.out.println(currentSentence);
        switch (states.peek()) {
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
        states.push(state);
    }

    /**
     * Drops the current state
     */
    public void dropState() {
        // TODO dont destroy the base state
        states.pop();
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
        for (int i = 0; i < 2; i++) {
            scopes.leaveScope();
            addToOutput("}", false);
        }
        try {
            FileWriter file = new FileWriter("src/Lang/outputTest.txt");
            for (String s: output) {
                file.write(s + "\n");
            }
            file.close();
        }
        catch(Exception e) {
            // do something
        }
    }


    // Parser Functions


    private void program() {}
    private void stmt() {}
    private void var_def() {
        switch (token.type) {
            case id: case literal: case type: case eq: case arrow:
                sentence.add(token);
                break;
            case newline:
                System.out.print("CURRENT SENTENCE: ");
                System.out.println(sentence);
                if (sentence.size() != 5) addError(ErrorType.InvalidSentenceError);
                else {
                    String result = "";
                    // variable type
                    if (sentence.get(4).type == TokenType.type) result += sentence.get(4).toJava(true);
                    else addError(ErrorType.InvalidSentenceError);
                    // variable name
                    if (sentence.get(0).type == TokenType.id) result += sentence.get(0).toJava(true);
                    else addError(ErrorType.InvalidSentenceError);
                    // equal sign
                    if (sentence.get(1).type == TokenType.eq) result += sentence.get(1).toJava(true);
                    else addError(ErrorType.IncompleteExpressionError);
                    // literal
                    if (sentence.get(2).type == TokenType.literal) result += sentence.get(2).toJava(false);
                    else addError(ErrorType.InvalidSentenceError);
                    // checking for the arrow
                    if (sentence.get(3).type != TokenType.arrow) addError(ErrorType.IncompleteExpressionError);

                    if (!lineHasErrors()) {
                        scopes.addVariable(sentence.get(0).text, Type.getType(sentence.get(4).text));
                        addToOutput(result, true);
                    }
                    dropState();
                }
                clearSentence();
                newline();
                break;
            default:
                addError(ErrorType.SyntaxError);
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
        line++;
    }


    // Function Toolkit

    /**
     * Empties the current sentence
     */
    private void clearSentence() {
        sentence.clear();
    }

    private void addError(ErrorType t) {
        addError(t, line);
    }

    private void addError(ErrorType t, int line) {
        Error err = new Error(line, t);
        for (Error e: errors) if (e.equals(err)) return;
        errors.add(err);
    }

    /**
     * Checks if the current line contains an error
     */
    private boolean lineHasErrors() {
        for (Error e: errors) if (e.line == line) return true;
        return false;
    }

    private void addToOutput(String s, boolean semicolon) {
        String temp = "";
        for (int i = 0; i < scopes.getScopeLevel(); i++) {
            temp += "\t";
        }
        temp += s;
        if (semicolon) temp += ";";
        output.add(temp);
    }


    // TODO make this debug function
    private void printState() {

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

        public String toJava(boolean space) {
            String result = "<NIL>";
            switch (type) {
                case type:
                    switch (text) {
                        case "bool":
                            result = "Boolean";
                            break;
                        case "num":
                            result = "Double";
                            break;
                        case "string":
                            result = "String";
                            break;
                    }
                    break;
                case eq:
                    result = "=";
                    break;
                case id: case literal:
                    result = this.text;
                    break;
            }
            if (space) result += " ";
            return result ;
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

        @Override
        public boolean equals(Object o) {
            return ((Error) o).line == this.line && ((Error) o).type == this.type;
        }
    }

    private static class Sentence {
        public String[] words;

        public Sentence(int n) {
            this.words = new String[n];
        }

    }

}

/**
 * Note: architecture for functions:
 * default case -> wrong type of token for this state (add error; skip to the next line)
 * in other cases, add the relevant bit to the top of the deque
 * whe we reach the terminator, pop stateStack and remove the sentence from the bottom of the deque
 */