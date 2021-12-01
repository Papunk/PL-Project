package LangTools;


import java.util.HashSet;
import java.util.Set;

public class Function {
    public String name;
    public HashSet<Variable> args;
    public String returnType;
    public boolean isValid = true;

    public Function(String name, Variable[] variables, String returnType) {
        this.name = name;
        args = new HashSet<>();
        for (Variable v: variables) if (!args.add(v)) isValid = false;
        this.returnType = returnType;
    }

    @Override
    public boolean equals(Object o) {
        if (getClass() != o.getClass()) return false;
        Function other = (Function) o;
        return name.equals(other.name) && args.equals(other.args) && args.containsAll(other.args);
    }

    public boolean hasSameNameAs(Function f) {
        return name.equals(f.name);
    }
}
