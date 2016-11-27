package utils;

public interface SerializerInterface {
    void push(Object o);
    Object pop();
    void write() throws Exception;
    void read() throws Exception;
}