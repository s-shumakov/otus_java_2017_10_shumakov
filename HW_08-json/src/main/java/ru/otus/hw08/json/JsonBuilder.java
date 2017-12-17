package ru.otus.hw08.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class JsonBuilder {
    private JSONObject jsonObject;
    private static final ArrayList<Class> PRIMITIVES = new ArrayList<>(Arrays.asList(
            Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Boolean.TYPE, Character.TYPE
    ));

    public JsonBuilder() {
        this.jsonObject = new JSONObject();
    }

    public String objectToJson(Object obj) {
        try {
            navigateTree(obj, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @SuppressWarnings("unchecked")
    private void navigateTree(Object obj, JSONObject innerJsonObj) throws IllegalAccessException {
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            FieldType type = getType(field);
            switch (type) {
                case PRIMITIVE:
                case STRING:
                    putString(type.toString(), field, obj, innerJsonObj);
                    break;
                case OBJECTSARRAY:
                    putArrayOfObjects(type.toString(), field, obj);
                    break;
                case ARRAY:
                    putArray(type.toString(), field, obj);
                    break;
                case COLLECTION:
                    putCollection(type.toString(), field, obj);
                    break;
                case OBJECT:
                    putObject(type.toString(), field, obj);
                    break;
            }
        }
    }

    private FieldType getType(Field field) {
        Class clazz = field.getType();
        System.out.println("clazz: " + clazz.getSimpleName());
        if (PRIMITIVES.contains(clazz)) {
            return FieldType.PRIMITIVE;
        } else if (clazz == String.class) {
            return FieldType.STRING;
        } else if (clazz == Collection.class) {
            return FieldType.COLLECTION;
        } else if (clazz.isArray()) {
            if (PRIMITIVES.contains(clazz.getComponentType()) ||
                    clazz.getComponentType() == String.class) {
                return FieldType.ARRAY;
            } else {
                return FieldType.OBJECTSARRAY;
            }
        } else {
            return FieldType.OBJECT;
        }
    }

    @SuppressWarnings("unchecked")
    private void putString(String type, Field field, Object obj, JSONObject innerJsonObj) throws IllegalAccessException {
        System.out.println(type + " " + field.getName() + ": " + field.get(obj));
        if (field.get(obj) != null) {
            if (innerJsonObj != null) {
                innerJsonObj.put(field.getName(), field.get(obj));
            } else {
                jsonObject.put(field.getName(), field.get(obj));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void putArrayOfObjects(String type, Field field, Object obj) throws IllegalAccessException {
        JSONArray jsonArray = new JSONArray();
        jsonObject.put(field.getName(), jsonArray);
        for (Object element : (Object[]) field.get(obj)) {
            JSONObject innerArrayObj = new JSONObject();
            System.out.println(type + " " + field.getName() + ": " + field.get(obj));
            jsonArray.add(innerArrayObj);
            navigateTree(element, innerArrayObj);
        }
    }

    @SuppressWarnings("unchecked")
    private void putArray(String type, Field field, Object obj) throws IllegalAccessException {
        JSONArray jsonArray = new JSONArray();
        jsonObject.put(field.getName(), jsonArray);
        int length = Array.getLength(field.get(obj));
        for (int i = 0; i < length; i ++) {
            Object arrayElement = Array.get(field.get(obj), i);
            jsonArray.add(arrayElement);
            System.out.println(type + " " + field.getName() + ": " + arrayElement);
        }
    }

    @SuppressWarnings("unchecked")
    private void putCollection(String type, Field field, Object obj) throws IllegalAccessException {
        JSONArray jsonArray = new JSONArray();
        jsonObject.put(field.getName(), jsonArray);
        for (Object element : (Collection) field.get(obj)) {
            System.out.println(type + " " + field.getName() + ": " + element);
            jsonArray.add(element);
        }
    }

    @SuppressWarnings("unchecked")
    private void putObject(String type, Field field, Object obj) throws IllegalAccessException {
        JSONObject innerObj = new JSONObject();
        jsonObject.put(field.getName(), innerObj);
        System.out.println(type + " " + field.getName() + ": " + field.get(obj));
        navigateTree(field.get(obj), innerObj);
    }

    private enum FieldType {
        PRIMITIVE,
        STRING,
        COLLECTION,
        ARRAY,
        OBJECTSARRAY,
        OBJECT
    }
}
