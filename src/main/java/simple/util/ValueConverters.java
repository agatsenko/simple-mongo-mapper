/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2019-12-17
 */
package simple.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValueConverters {
    private static final ValueConverter directConverter = src -> src;

    @SuppressWarnings("unchecked")
    public static <T> ValueConverter<T, T> directConverter() {
        return (ValueConverter<T, T>) directConverter;
    }

    public static final ValueConverter<Boolean, String> boolAsStrConverter = bool -> {
        if (bool == null) {
            return null;
        }
        return bool ? "1" : "0";
    };
}
