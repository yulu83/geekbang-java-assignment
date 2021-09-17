package com.yulu;


import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) throws Exception {
        HelloClassLoader classLoader = new HelloClassLoader();
        Class<?> klass = classLoader.findClass("Hello");
        Method helloMethod = klass.getDeclaredMethod("hello");
        helloMethod.setAccessible(true);
        Object obj = klass.newInstance();
        helloMethod.invoke(obj);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Path path = Paths.get("Hello.xlass");
        try {
            byte[] data = Files.readAllBytes(path);
            byte[] decoded = decode(data);

            return defineClass(name, decoded, 0, data.length);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] decode(byte[] data) {
        for (int i = 0; i < data.length; ++i) {
            data[i] = (byte) (255 - (int) data[i]);
        }
        return data;
    }
}
