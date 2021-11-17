package Lang;

import LangTools.ScopeSystem;

import java.io.*;
import java.util.*;

public class Parser {

    // TODO make message infrastrucute  to communicate between lexer and parser (i.e. endOfLine, newScope, etc)
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
    public void receive(String match) {
        switch (match) {
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
     }

     // ======================= Recursive Descent Parser Functions ======================= //

    private void program() {}
    private void scope() {}
    private void stmt() {}
    private void var_dec() {}
    private void var_assign() {}
    private void func_dec() {}
    private void command() {}
    private void id() {}
    private void if_stmt() {}
    private void for_loop() {}
    private void while_loop() {}


}
