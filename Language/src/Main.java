import java.io.File;
import java.util.Scanner;
import Lang.Lexer;

public class Main {
    // launch this from language command
    public static void main(String[] args) {
        try {
            if (args.length == 0) Lexer.main(new String[]{"src/Lang/testInput.paje"});
            else Lexer.main(args);
        }
        catch (Exception e) {
            System.out.println("Enter the source file path as an argument to this program or leave empty to run the test file.");
        }
    }
}
