public class Token {
    private final TokenTypes type;
    private final String value;

    public Token(TokenTypes type, String value) {
        this.type = type;
        this.value = value;
    }

    public TokenTypes getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("<%s> - <%s>", value, type);
    }
}
