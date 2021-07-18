package fun.divinetales.Core.Utils;

import java.lang.reflect.Field;

public class ReflectionUtil {

    public static Object getDeclaredField(Class<?> clazz, Object instance, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

