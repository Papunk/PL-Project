package LangTools;

/**
 * Represents all types in the language
 */
public enum Type {
    num,
    string,
    bool,
    CHONGUIIII;

    public static Type getType(String s) {
        switch (s) {
            case "num": return num;
            case "bool": return bool;
            case "string": return string;
            default:
        }
        return CHONGUIIII;
    }
}
