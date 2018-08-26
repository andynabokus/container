package com.container.loaders;

import java.util.Set;

public interface ContextClassLoader {

     Set<Class<?>> loadClasses();
     Class<?> getClassByName(String name);

}
