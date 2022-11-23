package chap03;

@FunctionalInterface
public interface Supplier<T> {
    T get();
}
