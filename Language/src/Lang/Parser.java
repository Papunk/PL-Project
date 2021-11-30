package Lang;

import LangTools.*;

import java.io.*;
import java.util.*;

public class Parser {





    // Variables





    private final List<String> output = new ArrayList<>();
    private final ScopeSystem scopeSys = new ScopeSystem();
    private final List<Error> errors = new ArrayList<>();
    private int line = 1;
    private int tabLevel;





    // Public API





    public Parser() {
        tabLevel = 0;
        addToOutput("import java.io.*", true);
        addToOutput("import java.util.*", true);
        // add our custom packages
        addToOutput("public class Main {", false);
        tabLevel++;
        addToOutput("public static void main(String[] args) {", false);
        tabLevel++;
    }

//    public void receive(String[] input) {
//        sentence.addAll(Arrays.asList(input));
//    }

    /**
     * Writes the output to an external file
     */
    public void end() {
        if (!scopeSys.isEmpty()) addError(ErrorType.BracketMismatchError, "Must close all brackets; missing " + scopeSys.getCurrentScopeLevel() + " closing brackets");
        if (!errors.isEmpty()) {
            String errorTrace = "";
            for (Error e: errors) {
                errorTrace += e.toString() + "\n";
            }
            System.out.print(errorTrace);
            return;
        }
        for (int i = 0; i < 2; i++) {
            tabLevel--;
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
     * @return the divided string as an array
     */
    public String[] split(String s) {
        String matchWhitespace = "\\s+";
        return s.trim().split(matchWhitespace);
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
        //TODO ensure that the types are matched
        String name = tokens[1], eq = tokens[2], value = tokens[3], type = tokens[5];
        boolean success = scopeSys.addVariable(name, type);
        if (success) {
            String temp = toJavaWS(type, TokenType.type) + toJavaWS(name, TokenType.id) + toJavaWS(eq, TokenType.eq) + toJava(value, TokenType.literal);
            addToOutput(temp, true);
        }
        else {
            addError(ErrorType.OverloadedDefinitionError, "Cannot redeclare variable '" + name + " -> " + type + "'");
        }
    }
    public void var_assign(String[] tokens) {
        //TODO check that the new value conforms to the type
    }
    public void if_stmt(String[] tokens) {
        cond_block("if", tokens);
    }
    public void while_loop(String[] tokens) {
        cond_block("while", tokens);
    }
    private void cond_block(String typeOfBlock, String[] tokens) {
        StringBuilder temp = new StringBuilder(typeOfBlock + " (");
        for (int i = 1; i < tokens.length - 1; i++) {
            String token = tokens[i];
            temp.append(token).append(" "); // TODO strip the first and last elements of the list, evaluate the rest with condition()
            if (!token.equals("true") && !token.equals("false") && !token.contains("&&") && !token.contains("||")) { // TODO check if this collides with strings
                if (!scopeSys.hasVariable(token)) addError(ErrorType.UndeclaredIdentifierError, "Symbol '" + token + "' not defined");
            }
        }
        addToOutput(temp.deleteCharAt(temp.length() - 1) + ")", false);
    }
    private void condition(String[] tokens) {
        // TODO determine the makeup by the number of things
    }
    public void for_loop(String[] tokens) {
        String iterationVar = tokens[1], firstBound = tokens[3], secondBound = tokens[5];
        // TODO add checking
        double firstNum = Double.parseDouble(firstBound), secondNum = Double.parseDouble(secondBound);
        String operator = "++";
        if (firstNum > secondNum) operator = "--";
        String temp = "for (int " + iterationVar + " = " + firstBound + "; " + iterationVar + " <= " + secondBound + "; " + iterationVar + operator + ")";
        addToOutput(temp, false);
    }
    // TODO add functions
    // Consider holding info about existing types in the scopeSys
    public void lb() {
        addToOutput("{", false);
        scopeSys.enterScope();
        tabLevel++;
    }
    public void rb() {
        if (!scopeSys.leaveScope()) addError(ErrorType.BracketMismatchError, "Excess closing brackets");
        else tabLevel--;
        addToOutput("}", false);
    }
    public void newline() {
        line++;
    }





    // Internal Function Toolkit





//    /**
//     * Empties the current sentence
//     */
//    private void clearSentence() {
//        sentence.clear();
//    }

    /**
     * Checks if the current line contains an error
     */
    private boolean lineHasErrors() {
        for (Error e: errors) if (e.line == line) return true;
        return false;
    }

    private void addToOutput(String s, boolean semicolon) {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < tabLevel; i++) {
            temp.append("\t");
        }
        temp.append(s);
        if (semicolon) temp.append(";");
        output.add(temp.toString());
    }

//    private void clearSentence() {
//        sentence.clear();
//    }

    private boolean areMatched(TokenType tokenType, String type) {
        switch (type) {
            case "bool": return tokenType == TokenType.bool;
            case "string": return tokenType == TokenType.string;
            case "num": return tokenType == TokenType.num;
        }
        return false;
    }

    /**
     * Turn string to java code without a space at the end
     */
    public String toJava(String text, TokenType type) {
        return toJava(text, type, false);
    }

    /**
     * Turn string to java code with a space at the end
     */
    public String toJavaWS(String text, TokenType type) {
        return toJava(text, type, true);
    }

    public String toJava(String text, TokenType type, boolean space) {
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
                result = text;
                break;
        }
        if (space) result += " ";
        return result ;
    }



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

//        public String toJava(boolean space) {
//            String result = "<NIL>";
//            switch (type) {
//                case type:
//                    switch (text) {
//                        case "bool":
//                            result = "Boolean";
//                            break;
//                        case "num":
//                            result = "Double";
//                            break;
//                        case "string":
//                            result = "String";
//                            break;
//                    }
//                    break;
//                case eq:
//                    result = "=";
//                    break;
//                case id: case literal: case num: case string: case bool:
//                    result = this.text;
//                    break;
//            }
//            if (space) result += " ";
//            return result ;
//        }
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