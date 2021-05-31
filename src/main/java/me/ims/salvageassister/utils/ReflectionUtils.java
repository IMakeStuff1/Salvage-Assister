package me.ims.salvageassister.utils;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;

public class ReflectionUtils {
    public static Field getField(Class<?> clazz, String... names) {
        Field field = null;

        for(String name: names) {
            if(field != null) break;
            try {
                field = clazz.getDeclaredField(name);
            } catch(NoSuchFieldException ignored) {

            }
        }

        return field;
    }

    public static <T> T getPrivateValue(Class<?> clazz, Object object, String... names) {
        Field field = getField(clazz, names);
        assert field != null;
        field.setAccessible(true);

        try {
            return (T)field.get(object);
        } catch(IllegalAccessException ignored) {
            return null;
        }
    }

    public static <T> boolean setPrivateValue(Class<?> clazz, Object object, T value, boolean doFinal, String... names) {
        Field field = getField(clazz, names);
        assert field != null;
        field.setAccessible(true);
        if(doFinal) FieldUtils.removeFinalModifier(field);

        try {
            field.set(object, value);
        } catch(IllegalAccessException ignored) {
            return false;
        }
        return true;
    }
}
