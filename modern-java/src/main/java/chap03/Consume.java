package chap03;

@FunctionalInterface
public interface Consume<T> {
    void accept(T t);
}
