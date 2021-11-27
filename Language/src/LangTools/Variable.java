package LangTools;

public class Variable {
    public String name;
    public Type type;

    public Variable(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        Variable other = (Variable) o;
        return name.equals(other.name);
    }
}
