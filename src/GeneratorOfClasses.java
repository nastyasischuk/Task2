import java.io.FileInputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GeneratorOfClasses {
    private List<Class<?>> allClasses;
    private Class<? extends Annotation> annotation;
    private Properties properties;

    public GeneratorOfClasses(List<Class<?>> allClasses, Class<? extends Annotation> annotation, String pathToProperties) {
        this.allClasses = allClasses;
        this.annotation = annotation;
        properties = readProperties(pathToProperties);
    }

    public List<Object> instantiateClasses() throws Exception {
        List<Class<?>> toInstantiate = getClassesToCreateInstances();
        List<Object> createdClasses = new ArrayList<>();

        for (Class<?> classToInstantiate : toInstantiate) {
            Constructor constructor = classToInstantiate.getConstructor();
            Method[] methods = classToInstantiate.getDeclaredMethods();
            Object newObjectToInstantiate = constructor.newInstance();
            for (int i = 0; i < methods.length; i++) {
                String methodName = methods[i].getName();
                if (methodName.startsWith("set")) {
                    invokeSettingMethods(newObjectToInstantiate, methods[i]);
                }
            }
            createdClasses.add(newObjectToInstantiate);
        }
        return createdClasses;
    }

    public List<Class<?>> getClassesToCreateInstances() {
        List<Class<?>> classesToInstantiate = new ArrayList<>();
        for (Class<?> element : allClasses) {
            if (element.isAnnotationPresent(annotation)) {
                classesToInstantiate.add(element);
            }
        }
        return classesToInstantiate;
    }

    public Properties readProperties(String pathToProperties) {
        Properties properties = new Properties();
        try (FileInputStream reader = new FileInputStream(pathToProperties)) {
            properties.load(reader);
        } catch (Exception e) {

        }
        return properties;
    }

    public void invokeSettingMethods(Object newObjectToInstantiate, Method method) {
        try {
            String propertyValue = properties.getProperty(method.getName().substring(3).toLowerCase());
            for (int j = 0; j < method.getParameterTypes().length; j++) {
                Class<?> type = method.getParameterTypes()[j];
                if (type == int.class || type == Integer.class) {
                    method.invoke(newObjectToInstantiate, Integer.parseInt(propertyValue));
                } else if (type == short.class || type == Short.class) {
                    method.invoke(newObjectToInstantiate, Short.parseShort(propertyValue));
                } else if (type == byte.class || type == Byte.class) {
                    method.invoke(newObjectToInstantiate, Byte.parseByte(propertyValue));
                } else if (type == double.class || type == Double.class) {
                    method.invoke(newObjectToInstantiate, Double.parseDouble(propertyValue));
                } else if (type == float.class || type == Float.class) {
                    method.invoke(newObjectToInstantiate, propertyValue);
                } else if (type == boolean.class || type == Boolean.class) {
                    method.invoke(newObjectToInstantiate, Boolean.parseBoolean(propertyValue));
                } else if (type == char.class || type == Character.class) {
                    method.invoke(newObjectToInstantiate, propertyValue.charAt(0));
                } else {
                    method.invoke(newObjectToInstantiate, propertyValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
