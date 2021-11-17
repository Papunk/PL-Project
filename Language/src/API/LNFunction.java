package API;

public class LNFunction {
    private String name;
    private LNFunctionArgument[] args;

    @Override
    public boolean equals(Object o) {
        LNFunction other = (LNFunction) o;
        return name.equals(other.name) && args.equals(other.args);
    }
}

class LNFunctionArgument {
    private String name;
    private LNType type;
}
