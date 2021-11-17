package Lang;

public class Parser {

    private String output = "";
    // add variable register structure (considers scope) {checks all scopes stsarting from the innermost to outermost}

    // keep similar register for functions and their arguments

    public void receive(String match) {
        output += match;
    }
}
