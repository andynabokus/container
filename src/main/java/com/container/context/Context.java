package com.container.context;

import com.container.loaders.ContextClassLoader;

import java.util.Set;

public class Context {

    private ContextClassLoader classLoader;
    private Set<Class<?>> classSet;

    public Context(ContextClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public Set<Class<?>> getClasses() {
        classSet = classLoader.loadClasses();
        return  classSet;
    }


}
