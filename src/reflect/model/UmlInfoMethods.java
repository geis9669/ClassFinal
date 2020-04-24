package reflect.model;

import java.lang.reflect.*;
import java.util.*;

public class UmlInfoMethods {

    /**
     *
     * @param cl a class
     * @return all the constructors as a string from the class cl
     */
    public static String getConstructors(Class cl)
    {
        Object[] constructors = sortByParameters(cl.getDeclaredConstructors());
        String message = "";

        for(int place = 0; place < constructors.length; place++)
        {
            Constructor thisClass = (Constructor) constructors[place];

            String name = thisClass.getName();
            message += "    "+ Modifier.toString(thisClass.getModifiers());
            message += " "+name+"(";

            // print parameter types
            Class[] paramTypes = thisClass.getParameterTypes();
            for( int index = 0; index < paramTypes.length; index++)
            {
                if(index>0)message += ", ";
                message += paramTypes[index].getName();
            }
            message += ");\n";
        }
        return message;
    }

    /**
     * makes a String of all the methods of a class
     * @param cl a class
     * @return the String of methods
     */
    public static String getMethods(Class cl)
    {
        Method[] methods = (Method[]) sortByName(cl.getDeclaredMethods());
        String message = "";

        for(Method method : methods)
        {
            Class returnType = method.getReturnType();
            String name = method.getName();

            // add modifiers to message, return type and method name
            message +="    " + Modifier.toString(method.getModifiers());
            message += " " + returnType.getName() + " "+name+ "(";

            //add parameter types to message
            Class[] paramTypes = method.getParameterTypes();
            for(int i = 0; i<paramTypes.length; i++)
            {
                if(i>0) message+=", ";
                message+=paramTypes[i].getName();
            }
            message+=");\n";
        }
        return message;
    }

    /**
     *
     * @param cl a class
     * @return the fields of a class
     */
    public static String getFields(Class cl)
    {
        Field[] fields = cl.getDeclaredFields();
        String message = "";

        for(Field f: fields)
        {
            Class type = f.getType();
            String name = f.getName();
            message += "    " + Modifier.toString(f.getModifiers());
            message += " " + type.getName()+ " " + name+ ";\n";
        }
        return message;
    }
