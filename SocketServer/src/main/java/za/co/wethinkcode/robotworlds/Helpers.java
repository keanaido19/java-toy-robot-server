package za.co.wethinkcode.robotworlds;

import java.util.regex.Pattern;

public class Helpers {
    public static boolean isObjectPositiveInteger(Object o) {
        try {
            if (o instanceof Integer) {
                return (int) o >= 0;
            }
            if (o instanceof Double) {
                String num = o.toString();
                Pattern pattern = Pattern.compile("^\\d+\\.0$");
                return pattern.matcher(num).find() && (int) ((double) o) >= 0;
            }
        } catch (NumberFormatException ignored) {
        }
        return false;
    }

    public static boolean isObjectAlphabetString(Object o) {
        if (o instanceof String) {
            Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
            return pattern.matcher(o.toString()).find();
        }
        return false;
    }
}
