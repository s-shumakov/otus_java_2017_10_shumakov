package ru.otus.hw08.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import java.lang.reflect.Field;

public class JsonBuilder {
    private JSONObject jsonObj;

    public JsonBuilder() {
        this.jsonObj = new JSONObject();
    }

    public void object2Json(Object obj) {
        try {
            navigateTree(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObj.toString());
    }

    private void navigateTree(Object obj) throws IllegalAccessException {
        for (Field field : obj.getClass().getFields()) {
            String type = getType(field);
            switch (type) {
                case "Primitive":
                case "String":
                    System.out.println(type + " " + field.getName() + ": " + field.get(obj));
                    jsonObj.put(field.getName(), field.get(obj));
                    break;
                case "Array":
                    JSONArray list = new JSONArray();
                    jsonObj.put(field.getName(), list);
                    for (Object element : (Object[]) field.get(obj)) {
                        System.out.println(type + " " + field.getName() + ": " + field.get(obj));
                        navigateTree(element);
                    }
                    break;
                case "Object":
                    JSONObject innerObj = new JSONObject();
                    jsonObj.put(field.getName(), innerObj);
                    System.out.println(type + " " + field.getName() + ": " + field.get(obj));
                    navigateTree(field.get(obj));
                    break;
            }
        }
    }

    private String getType(Field field) {
        Class clazz = field.getType();
        if (clazz.isPrimitive()) {
            return "Primitive";
        } else if (clazz.getSimpleName().equals("String")) {
            return "String";
        } else if (clazz.isArray()) {
            return "Array";
        } else if (clazz.isEnum()) {
            return "Enum";
        } else {
            return "Object";
        }
    }

    private void add2Json(JSONObject jsonObj) {

    }
}
