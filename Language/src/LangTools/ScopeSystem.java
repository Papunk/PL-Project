package LangTools;


import java.util.*;

/**
 * This class handles the scopes for the programming language.
 */
public class ScopeSystem {
    private final Stack<Scope> scopeStack = new Stack<>();
    private final HashSet<Function> functions = new HashSet<>();
    private Function currentFunction = null;

    private int currentScopeLevel; // 0 maps to one scope

    public ScopeSystem() {
        currentScopeLevel = 0;
        scopeStack.add(new Scope(0, ScopeType.main)); // create program scope
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
            Scope s = scopeStack.pop();
            if(s.type == ScopeType.func) currentFunction = null;
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
    public boolean addFunction(String name, Variable[] args, String returnType) {
        Function function = new Function(name, args, returnType);
        if (functions.add(function)) {
            currentFunction = function;
            return true;
        }
        return false;
    }

    public boolean hasVariable(String name) {
        return !isValid(new Variable(name,""));
    }

    public boolean hasFunction(String name, int numOfArgs) {
        for (Function f: functions) {
            if (f.name.equals(name) && f.args.size() == numOfArgs) return true;
        }
        return false;
    }


    // these two functions could be condensed into one:

    private boolean isValid(Variable variable) {
        for (Scope s: scopeStack) {
            if (s.contains(variable)) return false;
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
     * @return the current function if it exists or null otherwise
     */
    public Function getCurrentFunction() {
        return currentFunction;
    }

    public Variable getVariableWithName(String name) {
        for (Scope s: scopeStack) {
            for (Variable v: s.variables) {
                if (v.name.equals(name)) return v;
            }
        }
        return null;
    }



    /**
     * This class internally represents a scope.
     * It keeps track of the functions and variables in each scope.
     */
    private static class Scope {
        private final int scopeLevel;
        private final ScopeType type;
        private final HashSet<Variable> variables = new HashSet<>();

        public Scope(int scopeLevel, ScopeType type) {
            this.scopeLevel = scopeLevel;
            this.type = type;
        }

        public boolean addVariable(Variable variable) {
            return variables.add(variable);
        }

        public boolean contains(Variable variable) {
            for (Variable v: variables) {
                if (v.hasSameNameAs(variable)) return true;
            }
            return false;
        }

        public ScopeType getScopeType() {
            return type;
        }
    }
}