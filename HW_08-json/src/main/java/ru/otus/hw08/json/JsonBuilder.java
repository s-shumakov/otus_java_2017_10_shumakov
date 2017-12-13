package ru.otus.hw08.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;
import java.util.Collection;

public class JsonBuilder {
    private JSONObject jsonObject;

    public JsonBuilder() {
        this.jsonObject = new JSONObject();
    }

    public String object2Json(Object obj) {
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
                case "Integer":
                case "Long":
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
                case "ArrayOfInt":
                    jsonArray = new JSONArray();
                    jsonObject.put(field.getName(), jsonArray);
                    for (int element : (int[]) field.get(obj)) {
                        System.out.println(type + " " + field.getName() + ": " + element);
                        jsonArray.add(element);
                    }
                    break;
                case "ArrayOfString":
                    jsonArray = new JSONArray();
                    jsonObject.put(field.getName(), jsonArray);
                    for (String element : (String[]) field.get(obj)) {
                        System.out.println(type + " " + field.getName() + ": " + element);
                        jsonArray.add(element);
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
        System.out.println("clazz: " +clazz.getSimpleName());
        if (clazz.isPrimitive()) {
            return "Primitive";
        } else if (clazz.getSimpleName().equals("String")) {
            return "String";
        } else if (clazz.getSimpleName().equals("Integer")) {
            return "Integer";
        } else if (clazz.getSimpleName().equals("Long")) {
            return "Long";
        } else if (clazz.getSimpleName().equals("Collection")) {
            return "Collection";
        } else if (clazz.isArray()) {
            if (clazz.getSimpleName().equals("int[]")) {
                return "ArrayOfInt";
            } else if (clazz.getSimpleName().equals("String[]")) {
                return "ArrayOfString";
            } else {
                return "ArrayOfObjects";
            }
        } else if (clazz.getSimpleName().equals("Collection")) {
            return "Collection";
        } else {
            return "Object";
        }
    }

}
