import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Compulsory {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, InvocationTargetException, MalformedURLException {

        String classPath = "C:\\Users\\Miruna Savin\\IdeaProjects\\PA2023_B5\\Laborator12\\Compulsory.java";
        String className = "Compulsory";

        File classFile = new File(classPath);
        URL classURL = classFile.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[] { classURL});
        Class<?> loadedClass = classLoader.loadClass(className);

        Package classPackage = loadedClass.getPackage();
        System.out.println("Package: " + classPackage.getName());

        Method[] methods = loadedClass.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("Method: " + method.getName());
        }

        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class) && Modifier.isStatic(method.getModifiers())) {
                method.invoke(null);
            }
        }

    }
}
