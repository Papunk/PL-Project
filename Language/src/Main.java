import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import Lang.Lexer;
import java.io.IOException;

public class Main {
    // launch this from language command
    public static void main(String[] args) {

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter file path: ");
            String filePath = scanner.next();

            System.out.println(" The file chosen was " + filePath + ".");

            File file = new File("/PL-Project/Language/src/" + filePath);
            Lexer.main(args);

        // takes the file
        // runs lexer
            // lexer generates preliminary file
        //
    }
}
