public class Main {
    public static void main(String[] args) {
        String path = "example.go";

        if (args.length == 0) {
            System.out.println("No file path provided, using default path: " + path);
        } else {
            System.out.println("Using provided path: " + args[0]);

            path = args[0];
        }

        Lexer lexer = FileReader.processFile(path);

        lexer.printTokens();
    }
}
