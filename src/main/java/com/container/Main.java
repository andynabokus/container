package com.container;

import com.container.context.Context;
import com.container.loaders.FileSystemClassLoader;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class Main {
    private ClassLoader classloader;

    String classPath = System.getProperty("java.class.path");

    public static void main(String[] args) {
        //FileSystemClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*        FileSystemClassLoader cl = FileSystemClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader)cl).getURLs();

        for(URL url: urls){
            System.out.println(url.getFile());
        }*/
/*
        String classPath = System.getProperty("java.class.path");
        System.out.println(classPath);

        Main m = new Main();
        m.classloader = m.getClass().getClassLoader();

        System.out.println( m.classloader.getClass());

        try {
            Method method = m.classloader.getClass().getMethod("getClassPath", (Class<?>) null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }*/

Context cont = new Context(new FileSystemClassLoader());
cont.getClasses().forEach(e -> System.out.println(e.toString()));




    }
}
