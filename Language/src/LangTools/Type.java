package LangTools;

/**
 * Represents all types in the language
 */
public enum Type {
    num("num"),
    string("string"),
    bool("bool"),
    CHONGUIIII("CHONGUIIII");

    Type(String s) {}


    public static Type getType(String s) {
        switch (s) {
            case "num": return num;
            case "bool": return bool;
            case "string": return string;
            default:
        }
        return CHONGUIIII;
    }

    // TODO implement this
    public static boolean isType() {
        return false;
    }
}
