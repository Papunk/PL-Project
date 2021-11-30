package LangTools;


public class Variable {
    public String name;
    public String type;

    public Variable(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        Variable other = (Variable) o;
        return name.equals(other.name);
    }

    @Override
    public String toString() {
        return name + " -> " + type;
    }
}
