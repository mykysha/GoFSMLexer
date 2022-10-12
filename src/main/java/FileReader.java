import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    public static Lexer processFile(String path) {
        Lexer finiteStateMachine = new Lexer();

        try (Scanner sc = new Scanner(new File(path))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                finiteStateMachine.processLine(line);
            }
        } catch (FileNotFoundException error) {
            System.out.println("File not found: " + error.getMessage());
        }

        return finiteStateMachine;
    }
}
