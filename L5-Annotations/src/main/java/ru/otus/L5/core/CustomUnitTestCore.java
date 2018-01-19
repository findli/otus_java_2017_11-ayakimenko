package ru.otus.L5.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.L5.annotations.AfterCustom;
import ru.otus.L5.annotations.BeforeCustom;
import ru.otus.L5.annotations.TestCustom;
import ru.otus.L5.utils.ReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by abyakimenko on 19.01.2018.
 */
public class CustomUnitTestCore {

    private static final Logger logger = LoggerFactory.getLogger(CustomUnitTestCore.class);

    private CustomUnitTestCore() {
    }

    public static void runPackageClasses(String packageName) {
        Set<Class<?>> testClasses = ReflectionHelper.getTestClassesByPackageReference(packageName);
        if(testClasses.isEmpty()){
            logger.info("No test classes were found for package: {}", packageName);
            return;
        }
        
        testClasses.forEach(CustomUnitTestCore::runClassMethods);
    }

    @SuppressWarnings("unchecked")
    public static void runClassMethods(Class targetClass) {

        List<Method> afterMethods = ReflectionHelper.getMethodsByAnnotation(targetClass, AfterCustom.class);
        List<Method> beforeMethods = ReflectionHelper.getMethodsByAnnotation(targetClass, BeforeCustom.class);
        List<Method> testsMethods = ReflectionHelper.getMethodsByAnnotation(targetClass, TestCustom.class);

        logger.info("Start executing class' [{}] methods", targetClass.getName());

        testsMethods.forEach(method -> {

            try {
                logger.info("### Method name: {}", method.getName());
                Object testClassInstance = ReflectionHelper.instantiate(targetClass);

                runMethods(testClassInstance, beforeMethods);
                runMethod(testClassInstance, method);
                runMethods(testClassInstance, afterMethods);
            } catch (InvocationTargetException ex) {
                logger.error("Test's failed", ex);
            } catch (IllegalAccessException ex) {
                logger.error("Test's broken", ex);
            }
        });
    }

    private static void runMethods(Object instance, List<Method> methods)
            throws InvocationTargetException, IllegalAccessException {
        for (Method m : methods) {
            runMethod(instance, m);
        }
    }

    private static void runMethod(Object instance, Method method, Object... params)
            throws InvocationTargetException, IllegalAccessException {

        if (Objects.isNull(instance) || Objects.isNull(method)) {
            return;
        }
        method.invoke(instance, params);
    }
}
