package Lang;

import LangTools.ScopeSystem;

import java.util.Stack;

public class Parser {

    private String output = "";
    private ScopeSystem scope = new ScopeSystem();
    private Stack<String> errorStack = new Stack<String>();

    // add variable register structure (considers scope) {checks all scopes stsarting from the innermost to outermost}

    // keep similar register for functions and their arguments

    public void receive(String match) {
        output += match;
    }
}
