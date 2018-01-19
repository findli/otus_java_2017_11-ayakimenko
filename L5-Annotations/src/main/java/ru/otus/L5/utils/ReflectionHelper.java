package ru.otus.L5.utils;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


@SuppressWarnings("SameParameterValue")
public class ReflectionHelper {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionHelper.class);

    private ReflectionHelper() {
    }

    public static <T> T instantiate(Class<T> type, Object... args) {
        try {
            if (args.length == 0) {
                return type.newInstance();
            } else {
                return type.getConstructor(toClasses(args)).newInstance(args);
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            logger.error("###instantiate Something goes wrong ", e);
        }
        return null;
    }

    static Object getFieldValue(Object object, String name) {
        Field field = null;
        boolean isAccessible = true;
        try {
            field = object.getClass().getDeclaredField(name);
            isAccessible = field.isAccessible();
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.error("###getFieldValue Something goes wrong ", e);
        } finally {
            if (field != null && !isAccessible) {
                field.setAccessible(false);
            }
        }
        return null;
    }

    static void setFieldValue(Object object, String name, Object value) {
        Field field = null;
        boolean isAccessible = true;
        try {
            field = object.getClass().getDeclaredField(name); //getField() for public fields
            isAccessible = field.isAccessible();
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.error("###setField Something goes wrong ", e);
        } finally {
            if (field != null && !isAccessible) {
                field.setAccessible(false);
            }
        }
    }

    static Object callMethod(Object object, String name, Object... args) {
        Method method = null;
        boolean isAccessible = true;
        try {
            method = object.getClass().getDeclaredMethod(name, toClasses(args));
            isAccessible = method.isAccessible();
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            logger.error("###callMethod Something goes wrong ", e);
        } finally {
            if (method != null && !isAccessible) {
                method.setAccessible(false);
            }
        }
        return null;
    }

    public static <T> List<Method> getMethodsByAnnotation(Class<T> type, Class<? extends Annotation> annotation) {
        Method[] methods;
        List<Method> methodsWithAnnotation = new ArrayList<>();

        methods = type.getDeclaredMethods();

        for (Method method : methods) {
            if (method.getAnnotation(annotation) != null) {
                methodsWithAnnotation.add(method);
            }
        }
        return methodsWithAnnotation;
    }

    static Set<Class<?>> getClassesByPackage(String tPackage) {
        Reflections reflections = new Reflections(tPackage, new SubTypesScanner(false));
        return reflections.getSubTypesOf(Object.class);
    }

    static <T> boolean hasMethodAnnotation(Class<T> type, Class<? extends Annotation> annotation) {
        for (Method method : type.getDeclaredMethods()) {
            if (method.getAnnotation(annotation) != null) {
                return true;
            }
        }
        return false;
    }

    private static Class<?>[] toClasses(Object[] args) {
        if (Objects.isNull(args)) {
            return new Class[0];
        }

        return Arrays.stream(args)
                .map(Object::getClass).toArray(Class<?>[]::new);
    }

    public static Set<Class<?>> getTestClassesByPackageReference(String packageFullRef) {
        Reflections reflections = new Reflections(packageFullRef, new SubTypesScanner(false));

        return reflections.getSubTypesOf(Object.class);
    }
}