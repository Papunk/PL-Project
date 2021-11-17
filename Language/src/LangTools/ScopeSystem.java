package LangTools;


import API.LNFunction;
import API.LNVariable;

import java.util.ArrayList;
import java.util.Stack;

public class ScopeSystem {
    private int scopeLevel;
    private ArrayList<LNVariable> variables = new ArrayList<>();
    private ArrayList<LNFunction> functions = new ArrayList<>();
    private Stack<ScopeSystem> innerScopes = new Stack<>();

    public void addScope() {
        innerScopes.add(new ScopeSystem());
    }
}
