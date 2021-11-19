package Lang;

import LangTools.ParserMessage;
import LangTools.ScopeSystem;

import java.io.*;
import java.util.*;

public class Parser {

    // TODO make message infrastructure  to communicate between lexer and parser (i.e. endOfLine, newScope, etc)
    private String output = "";
    private String currentStatement = "";
    private final ScopeSystem parentScope = new ScopeSystem();
    private final Stack<String> errorStack = new Stack<String>();


    public Parser() {
        addHeader();
    }

    /**
     * Receives matched input from the lexer
     * @param match input from the lexer
     */
    public void receive(String match, ParserMessage msg) {
        System.out.println(match);
        switch (msg) {
            case var_def: var_def(match);
        }
        output += match;
    }

    /**
     * Writes the output to an external file
     */
    public void end() {
        FileWriter file = null;
        try {
            file = new FileWriter("");
        }
        catch(Exception e) {
            // do something
        }
    }


    private void addHeader() {
        output += "import java.io.*\n";
        output += "import java.util.*\n";
        // add our custom packages
        output += "import java.util.*\n";
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

    // ======================= State ======================= //

    private static enum State {
        init,
        var_def,
        if_stmt,
        for_loop,
        while_loop
    }
}
