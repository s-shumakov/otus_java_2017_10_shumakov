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
        JSONArray jsonArray;
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String type = getType(field);
            switch (type) {
                case "Primitive":
                case "String":
                    System.out.println(type + " " + field.getName() + ": " + field.get(obj));
                    if (field.get(obj) != null) {
                        if (innerJsonObj != null) {
                            innerJsonObj.put(field.getName(), field.get(obj));
                        } else {
                            jsonObject.put(field.getName(), field.get(obj));
                        }
                    }
                    break;
                case "ArrayOfObjects":
                    jsonArray = new JSONArray();
                    jsonObject.put(field.getName(), jsonArray);
                    for (Object element : (Object[]) field.get(obj)) {
                        JSONObject innerArrayObj = new JSONObject();
                        System.out.println(type + " " + field.getName() + ": " + field.get(obj));
                        jsonArray.add(innerArrayObj);
                        navigateTree(element, innerArrayObj);
                    }
                    break;
                case "Array":
                    jsonArray = new JSONArray();
                    jsonObject.put(field.getName(), jsonArray);
                    int length = Array.getLength(field.get(obj));
                    for (int i = 0; i < length; i ++) {
                        Object arrayElement = Array.get(field.get(obj), i);
                        jsonArray.add(arrayElement);
                        System.out.println(type + " " + field.getName() + ": " + arrayElement);
                    }
                    break;
                case "Collection":
                    jsonArray = new JSONArray();
                    jsonObject.put(field.getName(), jsonArray);
                    for (Object element : (Collection) field.get(obj)) {
                        System.out.println(type + " " + field.getName() + ": " + element);
                        jsonArray.add(element);
                    }
                    break;
                case "Object":
                    JSONObject innerObj = new JSONObject();
                    jsonObject.put(field.getName(), innerObj);
                    System.out.println(type + " " + field.getName() + ": " + field.get(obj));
                    navigateTree(field.get(obj), innerObj);
                    break;
            }
        }
    }

    private String getType(Field field) {
        Class clazz = field.getType();
        System.out.println("clazz: " + clazz.getSimpleName());
        if (PRIMITIVES.contains(clazz)) {
            return "Primitive";
        } else if (clazz == String.class) {
            return "String";
        } else if (clazz == Collection.class) {
            return "Collection";
        } else if (clazz.isArray()) {
            if (PRIMITIVES.contains(clazz.getComponentType()) ||
                    clazz.getComponentType() == String.class) {
                return "Array";
            } else {
                return "ArrayOfObjects";
            }
        } else {
            return "Object";
        }
    }

}
