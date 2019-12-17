package simple.util;

import java.util.function.Function;
import java.util.function.Supplier;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Check {
    public static void arg(boolean condition, Supplier<String> errorMsg) {
        check(condition, errorMsg, IllegalArgumentException::new);
    }

    public static void arg(boolean condition, String errorMsgFormat, Object... errorMsgFormatArgs) {
        arg(condition, () -> String.format(errorMsgFormat, errorMsgFormatArgs));
    }

    public static void argNotNull(Object arg, CharSequence argName) {
        arg(arg != null, () -> argName + " is null");
    }

    public static void state(boolean condition, Supplier<String> errorMsg) {
        check(condition, errorMsg, IllegalStateException::new);
    }

    public static void state(boolean condition, String errorMsgFormat, Object... errorMsgFormatArgs) {
        state(condition, () -> String.format(errorMsgFormat, errorMsgFormatArgs));
    }

    private static <E extends RuntimeException> void check(
            boolean condition,
            Supplier<String> errorMsg,
            Function<String, E> error) {
        if (!condition) {
            throw error.apply(errorMsg.get());
        }
    }
}
