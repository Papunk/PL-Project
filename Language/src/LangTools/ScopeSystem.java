package LangTools;


import java.util.*;

/**
 * This class handles the scopes for the programming language.
 */
public class ScopeSystem {
    private final Stack<Scope> scopeStack = new Stack<>();

    private int currentScopeLevel; // 0 maps to one scope

    public ScopeSystem() {
        currentScopeLevel = 0;
        scopeStack.add(new Scope(0, ScopeType.main)); // create program scope
        // TODO add built in types to parent scope
    }

    /**
     * Enables a new stack one level below the current one
     */
    public void enterScope(ScopeType type) {
        scopeStack.add(new Scope(++currentScopeLevel, type));
    }

    /**
     * Deactivates current stack and enables the previous one
     */
    public boolean leaveScope() {
        if (!isEmpty()) {
            scopeStack.pop();
            currentScopeLevel--;
            return true;
        }
        return false;
    }

    /**
     * Adds a variable to the current if it does not exist in any scope
     * @param name of the variable
     * @param type of the variable
     * @return true if the operation was successful and false otherwise
     */
    public boolean addVariable(String name, String type) {
        Variable variable = new Variable(name, type);
        if (isValid(variable)) return scopeStack.peek().addVariable(variable);
        return false;
    }

    /**
     * Adds a function if it does not exist in any scope
     * @param name of the function
     * @param args of the function
     * @return true if the operation was successful and false otherwise
     */
    public boolean addFunction(String name, Variable[] args) {
        Function function = new Function(name, args);
        if (isValid(function)) return scopeStack.peek().addFunction(function);
        return false;
    }

    public boolean hasVariable(String name) {
        return !isValid(new Variable(name,""));
    }


    // these two functions could be condensed into one:

    private boolean isValid(Variable variable) {
        for (Scope s: scopeStack) {
            if (s.contains(variable)) return false;
        }
        return true;
    }

    private boolean isValid(Function function) {
        for (Scope s: scopeStack) {
            if (s.contains(function)) return false;
        }
        return true;
    }

    public int getScopeLevel() {
        return currentScopeLevel;
    }

    /**
     * @return true if there is only the base scope and false otherwise
     */
    public boolean isEmpty() {
        return currentScopeLevel == 0;
    }

    public int getCurrentScopeLevel() {
        return currentScopeLevel;
    }

    public boolean contains(ScopeType type) {
        for (Scope s: scopeStack) {
            if (s.getScopeType() == type) return true;
        }
        return false;
    }

    public boolean atTopLevel() {
        return getCurrentScopeLevel() == 0;
    }



    /**
     * This class internally represents a scope.
     * It keeps track of the functions and variables in each scope.
     */
    static class Scope {
        private final int scopeLevel;
        private final ScopeType type;
        private final HashSet<Variable> variables = new HashSet<>();
        private final HashSet<Function> functions = new HashSet<>();

        public Scope(int scopeLevel, ScopeType type) {
            this.scopeLevel = scopeLevel;
            this.type = type;
        }

        public boolean addVariable(Variable variable) {
            return variables.add(variable);
        }

        public boolean addFunction(Function function) {
            return functions.add(function);
        }

        public boolean contains(Variable variable) {
            for (Variable v: variables) {
                if (v.equals(variable)) return true;
            }
            return false;
        }

        public boolean contains(Function function) {
            for (Function f: functions) {
                if (f.equals(function)) return true;
            }
            return false;
        }

        public ScopeType getScopeType() {
            return type;
        }
    }
}