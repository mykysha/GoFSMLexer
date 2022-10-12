import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final List<Token> tokens = new ArrayList<>();

    public void printTokens() {
        for (Token t : tokens) {
            if (t.getType() == TokenTypes.Comment) {
                continue;
            }

            System.out.println(t);
        }
    }

    public void commentState(String line) {
        if (line.startsWith("//") || line.startsWith("/*")) {
            tokens.add(new Token(TokenTypes.Comment, line));
        }
    }

    public void stringState(String line) {
        StringBuilder word = new StringBuilder();

        word.append('\"');

        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) != '\"') {
                word.append(line.charAt(i));
            } else {
                word.append('\"');
                tokens.add(new Token(TokenTypes.String_Constant, word.toString()));
                processLine(line.substring(i + 1));

                return;
            }
        }

        tokens.add(new Token(TokenTypes.Unknown, word.toString()));
    }

    public void numberState(String line) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            if (Character.isDigit(line.charAt(i)) || Character.isAlphabetic(line.charAt(i)) || line.charAt(i) == '.') {
                builder.append(line.charAt(i));
            } else {
                tokens.add(extractToken(builder.toString()));
                processLine(line.substring(i));

                return;
            }
        }

        tokens.add(extractToken(builder.toString()));
    }

    public void punctuationState(String line) {
        tokens.add(new Token(TokenTypes.Punctuation, line));
    }

    private void processWord(String line) {
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            if (Character.isAlphabetic(line.charAt(i)) || Character.isDigit(line.charAt(i)) || line.charAt(i) == '_' || line.charAt(i) == '\'') {
                word.append(line.charAt(i));
            } else {
                Token token = extractToken(word.toString());

                tokens.add(token);
                processLine(line.substring(i));

                return;
            }
        }

        tokens.add(extractType(word.toString()));
    }

    private Token extractType(String word) {
        if (Data.Keywords.contains(word)) {
            return new Token(TokenTypes.Keyword, word);
        } else if (Data.Types.contains(word)) {
            return new Token(TokenTypes.Type, word);
        } else if (word.length() == 3 && StringChecker.isChar(word)) {
            return new Token(TokenTypes.Char_Constant, word);
        } else if (StringChecker.isIdentifier(word)) {
            return new Token(TokenTypes.Identifier, word);
        } else {
            return new Token(TokenTypes.Unknown, word);
        }
    }

    private Token extractToken(String word) {
        if (StringChecker.isNumber(word)) {
            return new Token(TokenTypes.Integer_Constant, word);
        } else if (StringChecker.isFloat(word)) {
            return new Token(TokenTypes.Float_Constant, word);
        } else if (StringChecker.isHexNumber(word)) {
            return new Token(TokenTypes.Hex_Constant, word);
        } else {
            return extractType(word);
        }
    }

    public void processLine(String line) {
        line = line.trim();
        int length = line.length();

        for (int i = 0; i < length; i++) {
            char curr = line.charAt(i);

            Token token;

            switch (curr) {
                case ':' -> {
                    if (i != length - 1 && line.charAt(i + 1) == '=') {
                        token = new Token(TokenTypes.Operator, ":=");

                        i++;
                    } else {
                        token = new Token(TokenTypes.Unknown, ":");
                    }

                    tokens.add(token);
                }
                case '>' -> {
                    if (i != length - 1 && line.charAt(i + 1) == '=') {
                        token = new Token(TokenTypes.Operator, ">=");

                        i++;
                    } else {
                        token = new Token(TokenTypes.Operator, ">");
                    }

                    tokens.add(token);
                }
                case '&' -> {
                    Token lv;

                    if (i != length - 1 && line.charAt(i + 1) == '&') {
                        lv = new Token(TokenTypes.Operator, "&&");

                        i++;
                    } else {
                        lv = new Token(TokenTypes.Operator, "&");
                    }

                    tokens.add(lv);
                }
                case '|' -> {
                    if (i != length - 1 && line.charAt(i + 1) == '|') {
                        token = new Token(TokenTypes.Operator, "||");

                        i++;
                    } else {
                        token = new Token(TokenTypes.Operator, "|");
                    }

                    tokens.add(token);
                }
                case '<' -> {
                    if (i != length - 1 && line.charAt(i + 1) == '=') {
                        token = new Token(TokenTypes.Operator, "<=");

                        i++;
                    } else {
                        token = new Token(TokenTypes.Operator, "<");
                    }

                    tokens.add(token);
                }
                case '=' -> {
                    if (i != length - 1 && line.charAt(i + 1) == '=') {
                        token = new Token(TokenTypes.Operator, "==");

                        i++;
                    } else {
                        token = new Token(TokenTypes.Operator, "=");
                    }

                    tokens.add(token);
                }
                case '!' -> {
                    if (i != length - 1 && line.charAt(i + 1) == '=') {
                        token = new Token(TokenTypes.Operator, "!=");

                        i++;
                    } else {
                        token = new Token(TokenTypes.Operator, "!");
                    }

                    tokens.add(token);
                }
                case '*' -> {
                    if (i != length - 1 && line.charAt(i + 1) == '=') {
                        token = new Token(TokenTypes.Operator, "*=");

                        i++;
                    } else {
                        token = new Token(TokenTypes.Operator, "*");
                    }

                    tokens.add(token);
                }
                case '%' -> {
                    if (i != length - 1 && line.charAt(i + 1) == '=') {
                        token = new Token(TokenTypes.Operator, "%=");

                        i++;
                    } else {
                        token = new Token(TokenTypes.Operator, "%");
                    }

                    tokens.add(token);
                }
                case '/' -> {
                    if (i != length - 1 && line.charAt(i + 1) == '=') {
                        token = new Token(TokenTypes.Operator, "/=");

                        tokens.add(token);

                        i++;
                    } else if (i != length - 1 && (line.charAt(i + 1) == '*' || line.charAt(i + 1) == '/')) {
                        commentState(line.substring(i));

                        return;
                    } else {
                        token = new Token(TokenTypes.Operator, "/");

                        tokens.add(token);
                    }
                }
                case '+' -> {
                    if (i != length - 1 && line.charAt(i + 1) == '=') {
                        token = new Token(TokenTypes.Operator, "+=");

                        i++;
                    } else if (i != length - 1 && line.charAt(i + 1) == '+') {
                        token = new Token(TokenTypes.Operator, "++");

                        i++;
                    } else {
                        token = new Token(TokenTypes.Operator, "+");
                    }

                    tokens.add(token);
                }
                case '-' -> {
                    if (i != length - 1 && line.charAt(i + 1) == '=') {
                        token = new Token(TokenTypes.Operator, "-=");

                        i++;
                    } else if (i != length - 1 && line.charAt(i + 1) == '-') {
                        token = new Token(TokenTypes.Operator, "--");

                        i++;
                    } else {
                        token = new Token(TokenTypes.Operator, "-");
                    }

                    tokens.add(token);
                }
                case '(', ')', '{', '}', '[', ']', ',', '.', ';' -> punctuationState(String.valueOf(curr));
                case '\"' -> {
                    stringState(line.substring(i + 1));

                    return;
                }
                default -> {
                    if (Character.isDigit(curr)) {
                        numberState(line.substring(i));

                        return;
                    } else if (Character.isLetter(curr) || curr == '_' || curr == '\'') {
                        processWord(line.substring(i));

                        return;
                    } else {
                        if (!Character.isWhitespace(curr)) {
                            Token lv = new Token(TokenTypes.Unknown, Character.toString(curr));

                            tokens.add(lv);
                        }
                    }
                }
            }
        }
    }
}
