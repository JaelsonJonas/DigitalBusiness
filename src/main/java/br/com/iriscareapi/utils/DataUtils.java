package br.com.iriscareapi.utils;

import br.com.iriscareapi.exception.DataUtilsException;

import java.lang.reflect.Field;

public class DataUtils {

    public static <T> T validateUpdatedValue(T defaultValue, T newValue) {
        try {
            return (newValue != null && !newValue.toString().isEmpty() && !newValue.toString().isBlank()) ? newValue : defaultValue;
        } catch (Exception e) {
            throw new DataUtilsException("validateUpdatedValue", e);
        }
    }

    public static <T, U> void dataUpdate(T dataToUpdate, U dataFromDTO) {
        try {
            for(Field at : dataToUpdate.getClass().getDeclaredFields()) {
                for(Field atDTO : dataFromDTO.getClass().getDeclaredFields()){
                    setField(dataToUpdate, at.toString(), DataUtils.validateUpdatedValue(at, atDTO));
                }
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
