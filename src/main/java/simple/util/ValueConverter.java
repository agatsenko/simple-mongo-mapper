package simple.util;

@FunctionalInterface
public interface ValueConverter<S, D> {
    D convert(S src);
}
