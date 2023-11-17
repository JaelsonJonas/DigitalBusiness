package br.com.iriscareapi.utils;

import br.com.iriscareapi.entities.Phone;
import br.com.iriscareapi.exception.DataUtilsException;

import java.lang.reflect.Field;
import java.util.HashMap;

public class DataUtils {

    public static <T> T validateUpdatedValue(T defaultValue, T newValue) {
        try {
            return (newValue != null && !newValue.toString().isEmpty() && !newValue.toString().isBlank()) ? newValue : defaultValue;
        } catch (Exception e) {
            throw new DataUtilsException("validateUpdatedValue", e);
        }
    }

    public static <E, T> void dataUpdate(E entity, HashMap<T, T> fields) {
        try {
            for (T field : fields.keySet()) {
                setField(entity, field.toString(), DataUtils.validateUpdatedValue(field, fields.get(field)));
            }
        } catch (Exception e) {
            throw new DataUtilsException("dataUpdate", e);
        }

    }

    public static <T> void setField(Object obj, String fieldName, T value) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new DataUtilsException("SetField", e);
        }
    }
}
