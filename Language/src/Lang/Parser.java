package Lang;

import LangTools.ScopeSystem;

import java.util.Stack;

public class Parser {

    // TODO make message infrastrucute  to communicate between lexer and parser (i.e. endOfLine, newScope, etc)
    private String output = "";
    private final ScopeSystem parentScope = new ScopeSystem();
    private final Stack<String> errorStack = new Stack<String>();


    /**
     * Receives matched input from the lexer
     * @param match input from the lexer
     */
    public void receive(String match) {
        switch (match) {
        }
        output += match;
    }

    /**
     * Writes the output to an external file
     */
    public void end() {
    }

    private final String VAR = "var";

}
