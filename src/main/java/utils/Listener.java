package utils;

public interface Listener<T> {
    void publish(T object);
}
