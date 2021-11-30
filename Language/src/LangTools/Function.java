package LangTools;

public class Function {
    private String name;
    private Variable[] args;
    private String returnType;

    public Function(String name, Variable[] args) {
        this.name = name;
        this.args = args;
    }

    @Override
    public boolean equals(Object o) {
        Function other = (Function) o;
        return name.equals(other.name) && args.equals(other.args);
    }
}
