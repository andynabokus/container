package com.container.loaders;

import java.awt.*;
import java.io.File;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class FileSystemClassLoader implements ContextClassLoader {



    private String classPath;
    private Set<Class<?>> classesSet= new LinkedHashSet<Class<?>>();

    public FileSystemClassLoader() {
        initClassPathVar();
    }

    private void initClassPathVar() {
        try {
            Method method =
                    this.getClass().getClassLoader().getClass().getMethod("getClassPath", (Class<?>) null);
            if (method != null) {
                classPath = (String) method.invoke(this.getClass().getClassLoader(), (Object) null);
            }
        } catch (Exception e) {
            // ignore
        }
        if (classPath == null) {
            classPath = System.getProperty("java.class.path");
        }
    }

    @Override
    public Set<Class<?>> loadClasses() {
        StringTokenizer tokenizer =
                new StringTokenizer(classPath, File.pathSeparator);
        String token;
        File dir;
        String name;
        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
            dir = new File(token);
            if (dir.isDirectory()) {
                lookInDirectory("", dir);
            }
/*            if (dir.isFile()) {
                name = dir.getName().toLowerCase();
                if (name.endsWith(".zip") || name.endsWith(".jar")) {
                    this.lookInArchive(dir);
                }
            }*/
        }
        return this.classesSet;
    }


    private void lookInDirectory(String name, File dir) {
        File[] files = dir.listFiles();
        File file;
        String fileName;
        final int size = files.length;
        for (int i = 0; i < size; i++) {
            file = files[i];
            fileName = file.getName();
            if (file.isFile()
                    && fileName.toLowerCase().endsWith(".class")) {
                try {
                        fileName = fileName.substring(0, fileName.length() - 6);
                        // filter ignored resources
              /*          if (!(name + fileName).startsWith(this.prefix)) {
                            continue;
                        }*/

                        this.classesSet.add(Class.forName(name + fileName));
                    } /*else {
                        this.list.add(
                                this.classloader.getResource(
                                        name.replace('.', File.separatorChar)
                                                + fileName));
                    }
                }*/
                 catch (ClassNotFoundException | NoClassDefFoundError e) {
                    // ignore
                } catch (ExceptionInInitializerError e) {
                    if (e.getCause() instanceof HeadlessException) {
                        // running in headless env ... ignore
                    } else {
                        throw e;
                    }
                }
            }
            // search recursively.
            // I don't like that but we will see how it will work.
            if (file.isDirectory()) {
                lookInDirectory(name + fileName + ".", file);
            }
        }

        }

    @Override
    public Class<?> getClassByName(String name) {
        return null;
    }
}
