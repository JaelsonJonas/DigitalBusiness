package br.com.iriscareapi.utils;

import br.com.iriscareapi.exception.DataUtilsException;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class DataUtils {

    public static <T> T validateUpdatedValue(T defaultValue, T newValue) {
        try {
            return (newValue != null && !newValue.toString().isEmpty() && !newValue.toString().isBlank()) ? newValue : defaultValue;
        } catch (Exception e) {
            throw new DataUtilsException("validateUpdatedValue", e);
        }
    }

    public static <T> void dataUpdate(Object entity, Object entityDto) throws Exception {
        List<String> fieldNames = Arrays.stream(entity.getClass().getDeclaredFields()).map(Field::getName).toList();
        List<String> dtoFieldNames = Arrays.stream(entityDto.getClass().getDeclaredFields()).map(Field::getName).toList();

        for (String dtoName : dtoFieldNames) {
            for (String fdName: fieldNames) {
                if(dtoName.equals(fdName)) {
                    Object inDb = getFieldValue(entity,fdName);
                    Object dto = getFieldValue(entityDto, dtoName);

                    if(inDb instanceof LocalDate) {
                        System.out.println("Localdate--------------------");
                        dto = DateUtils.parseString(dto.toString());
                    }


                    System.out.println( "___________________________________________" + inDb + "___________________________________________" );
                    try {
                        setField(entity, fdName, DataUtils.validateUpdatedValue(inDb, dto));
                    } catch (Exception e) {
                        throw new DataUtilsException("dataUpdate", e);
                    }
                }
            }
        }

    }

    public static Object getFieldValue(Object entity, String fieldName) {
        Object fieldValue = null;

        try {
            Class<?> entityClass = entity.getClass();
            Field field = entityClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            fieldValue = field.get(entity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new DataUtilsException("getFieldValue", e);
        }

        return fieldValue;
    }

    public static <T> void setField(Object obj, String fieldName, T value) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new DataUtilsException("setField", e);
        }
    }
}
