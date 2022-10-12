public class StringChecker {
    public static boolean isNumber(String number) {
        try {
            Integer.parseInt(number);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isFloat(String number) {
        try {
            Float.parseFloat(number);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isHexNumber(String number) {
        if (number.startsWith("0x")) {
            number = number.substring(2);

            try {
                Integer.parseInt(number, 16);

                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isChar(String strChar) {
        return strChar.charAt(0) == '\'' && strChar.charAt(2) == '\'';
    }

    public static boolean isIdentifier(String strIdent) {
        char first = strIdent.charAt(0);

        if (first == '_' || Character.isAlphabetic(first)) {
            for (int i = 1; i < strIdent.length(); i++) {
                if (!Character.isAlphabetic(strIdent.charAt(i)) && !Character.isDigit(strIdent.charAt(i)) && strIdent.charAt(i) != '_') {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }
}
