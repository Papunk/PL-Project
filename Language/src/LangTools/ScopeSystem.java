package LangTools;


import API.LNFunction;
import API.LNVariable;

import java.util.*;

/**
 * This class handles the scopes for the programming language.
 */
public class ScopeSystem {
    private Stack<Scope> scopeStack = new Stack<>();
    int currentScopeLevel; // 0 maps to one scope

    public ScopeSystem() {
        currentScopeLevel = 0;
        scopeStack.add(new Scope(0)); // create root scope
    }

    /**
     * Enables a new stack one level below the current one
     */
    public void enterScope() {
        scopeStack.add(new Scope(++currentScopeLevel));
    }

    /**
     * Deactivates current stack and enables the previous one
     */
    public void leaveScope() {
        if (currentScopeLevel > 0) {
            scopeStack.pop();
            currentScopeLevel--;
        }
    }

    /**
     * Adds a variable if it does not exist in any scope
     * @param variable
     * @return true if the operation was successful and false otherwise
     */
    public boolean addVariable(LNVariable variable) {
        if (isValid(variable)) return scopeStack.peek().addVariable(variable);
        return false;
    }

    /**
     * Adds a function if it does not exist in any scope
     * @param function
     * @return true if the operation was successful and false otherwise
     */
    public boolean addFunction(LNFunction function) {
        if (isValid(function)) return scopeStack.peek().addFunction(function);
        return false;
    }

    // these two functions could be condensed into one:

    private boolean isValid(LNVariable variable) {
        Scope[] scopes = (Scope[]) scopeStack.toArray();
        for (Scope s: scopes) {
            if (s.contains(variable)) return false;
        }
        return true;
    }

    private boolean isValid(LNFunction function) {
        Scope[] scopes = (Scope[]) scopeStack.toArray();
        for (Scope s: scopes) {
            if (s.contains(function)) return false;
        }
        return true;
    }

    /**
     * This class internally represents a scope.
     * It keeps track of the functions and variables in each scope.
     */
    static class Scope {
        private final int scopeLevel;
        private final HashSet<LNVariable> variables = new HashSet<>();
        private final HashSet<LNFunction> functions = new HashSet<>();

        public Scope(int scopeLevel) {
            this.scopeLevel = scopeLevel;
        }

        public boolean addVariable(LNVariable variable) {
            return variables.add(variable);
        }

        public boolean addFunction(LNFunction function) {
            return functions.add(function);
        }

        public boolean contains(LNVariable variable) {
            return variables.contains(variable);
        }

        public boolean contains(LNFunction function) {
            return functions.contains(function);
        }

        public int getScopeLevel() {
            return scopeLevel;
        }
    }
}