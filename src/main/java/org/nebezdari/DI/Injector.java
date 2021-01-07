package org.nebezdari.DI;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration(packages = {
        "org.nebezdari.org.nebezdari.sorters.quicksort",
        "org.nebezdari.validators.contracts",
        "org.nebezdari.validators.person"
})
public class Injector {
    
    private static final char PKG_SEPARATOR = '.';
    private static final char DIR_SEPARATOR = '/';
    private static final String CLASS_FILE_SUFFIX = ".class";

    public static <T> T Inject(T object) throws InjectorException {
        Configuration configuration = Injector.class.getAnnotation(Configuration.class);
        List<Class<?>> classes = getClassesInModules(configuration.packages());

        Field[] objectFields = object.getClass().getDeclaredFields();
        for (Field field: objectFields) {
            AutoInjectable annotation = field.getAnnotation(AutoInjectable.class);

            if (annotation != null) {
                field.setAccessible(true);

                try {
                    if (Collection.class.isAssignableFrom(field.getType())) {
                        List<Class<?>> implementationClasses = getImplementationClasses(annotation.clazz(), classes);

                        for (Class<?> implementationClass : implementationClasses) {
                            Object list = field.get(object);
                            Method add = List.class.getDeclaredMethod("add", Object.class);
                            add.invoke(list, implementationClass.getConstructor().newInstance());
                        }
                    }
                    else {
                        Class<?> implementationClass = getImplementationClass(field.getType(), classes);
                        field.set(object, implementationClass.getConstructor().newInstance());
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    throw new InjectorException(e);
                }
            }
        }

        return object;
    }

    private static List<Class<?>> getImplementationClasses(Class<?> fieldClass, List<Class<?>> classList) throws InjectorException {
        List<Class<?>> classes = new ArrayList<>();
        for (Class<?> clazz: classList) {

            if (fieldClass.equals(clazz)) {
                classes.add(clazz);
            }
            else {
                for (Class<?> classInterface: clazz.getInterfaces()) {
                    if (fieldClass.equals(classInterface)) {
                        classes.add(clazz);
                    }
                }
            }
        }

        return classes;
    }

    private static Class<?> getImplementationClass(Class<?> fieldClass, List<Class<?>> classList) throws InjectorException {
        Class<?> implementationClass = null;
        int counter = 0;
        for (Class<?> clazz: classList) {

            if (fieldClass.equals(clazz)){
                counter++;
                implementationClass = clazz;
            }
            else {
                for (Class<?> classInterface: clazz.getInterfaces()) {
                    if (fieldClass.equals(classInterface)) {
                        counter++;
                        implementationClass = clazz;
                    }
                }
            }
        }

        if (counter == 0) {
            throw new InjectorException("DI Failed: Suitable class not found");
        }

        if (counter > 1) {
            throw new InjectorException("DI Failed: Suitable class more than one");
        }

        return implementationClass;
    }

    private static List<Class<?>> getClassesInModules(String[] modules) {
        List<Class<?>> classList = new ArrayList<>();
        for (String module: modules) {
            classList.addAll(getClassesInPackage(module));
        }
        return classList;
    }

    private static List<Class<?>> getClassesInPackage(String packageName) {
        List<Class<?>> classList = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace(PKG_SEPARATOR, DIR_SEPARATOR);
        URL root = classLoader.getResource(path);

        if (root == null) {
            return classList;
        }

        File[] classFiles = new File(root.getFile()).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(CLASS_FILE_SUFFIX);
            }
        });

        if (classFiles == null) {
            return classList;
        }

        for (File file : classFiles) {
            String className = file.getName().replaceAll(".class$", "");

            try {
                Class<?> clazz = Class.forName(packageName + PKG_SEPARATOR + className);
                classList.add(clazz);
            }
            catch (ClassNotFoundException ignored) { }
        }

        return classList;
    }
}
