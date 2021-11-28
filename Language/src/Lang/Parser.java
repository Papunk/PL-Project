package Lang;

import LangTools.*;

import java.io.*;
import java.sql.SQLOutput;
import java.util.*;

public class Parser {





    // Variables





    private final List<String> output = new ArrayList<>();
    private final ScopeSystem scopes = new ScopeSystem();
    private final List<Error> errors = new ArrayList<>();
//    private final Stack<State> states = new Stack<>();
    private final List<Token> sentence = new ArrayList<>();
    private Token token = null;
    private int line = 1;





    // Public API





    public Parser() {
        addToOutput("import java.io.*", true);
        addToOutput("import java.util.*", true);
        // add our custom packages
        addToOutput("public class Main {", false);
        scopes.enterScope();
        addToOutput("public static void main(String[] args) {", false);
        scopes.enterScope();
//        states.push(State.program);
    }

    /**
     * Receives matched input as token from the lexer from the lexer
     * @param text input from the lexer
     * @param state the state of the parser
     */
    public void receive(String text, TokenType type, State state) {
//        setState(state);
        receive(text, type);
    }

    /**
     * Receives matched input without changing state
     * @param text input from the lexer
     */
    public void receive(String text, TokenType type) {
        token = new Token(text, type);
//        System.out.println(token);
//        System.out.println(currentSentence);
    }

//    /**
//     * Sets the current parser state
//     * @param state the state of the parser
//     */
//    public void setState(State state) {
//        states.push(state);
//    }
//
//    /**
//     * Drops the current state
//     */
//    public void dropState() {
//        // TODO dont destroy the base state
//        states.pop();
//    }

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


    /**
     * Divides a string by whitespace
     * @param s the string to divide
     * @return the divided string as a list
     */
    public String[] split(String s) {
        return s.split("\\s+");
    }

    /**
     * Adds an error to the given line
     * @param t type of error
     * @param msg the message to be displayed
     */
    public void addError(ErrorType t, String msg) {
        Error err = new Error(line, t, msg);
        for (Error e: errors) if (e.equals(err)) return;
        errors.add(err);
    }





    // Parser Functions





    public void var_def(String[] tokens) {

    }
    public void newline() {
        line++;
    }





    // Internal Function Toolkit





    /**
     * Empties the current sentence
     */
    private void clearSentence() {
        sentence.clear();
    }

    /**
     * Checks if the current line contains an error
     */
    private boolean lineHasErrors() {
        for (Error e: errors) if (e.line == line) return true;
        return false;
    }

    private void addToOutput(String s, boolean semicolon) {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < scopes.getScopeLevel(); i++) {
            temp.append("\t");
        }
        temp.append(s);
        if (semicolon) temp.append(";");
        output.add(temp.toString());
    }

    private boolean areMatched(TokenType tokenType, String type) {
        switch (type) {
            case "bool": return tokenType == TokenType.bool;
            case "string": return tokenType == TokenType.string;
            case "num": return tokenType == TokenType.num;
        }
        return false;
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
                case id: case literal: case num: case string: case bool:
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
        public String msg;

        public Error(int line, ErrorType type, String msg) {
            this.line = line;
            this.type = type;
            this.msg = msg;
        }

        @Override
        public String toString() {
            return  type.name() +
                    " at line " +
                    Integer.toString(line) +
                    ": " +
                    msg;
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