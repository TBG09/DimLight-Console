package rsbdp.commands;

import rsbdp.System.PublicVariables;

import java.lang.reflect.Field;

public class getvar {
    public static void main(String variableName) {
        try {
            // Get the PublicVariables class
            Class<?> clazz = PublicVariables.class;

            Field field = clazz.getField(variableName);

            String value = (String) field.get(null);

            // Print the value if it exists
            System.out.println(value);
        } catch (NoSuchFieldException e) {
            System.out.println("Variable " + variableName + " doesn't exist.");
        } catch (IllegalAccessException e) {
            System.out.println("Access to the variable " + variableName + " is denied.");
        }
    }
}

