package reflect.model;

import java.lang.reflect.*;
import java.util.*;

public class UmlInfoMethods {

    public static String getClassInfo(String className)
    {
        String classInfo = "";

        try
        {
            Class cl = Class.forName(className);
            Class supercl = cl.getSuperclass();
            classInfo = ("class " + className);
            if(supercl != null && supercl != Object.class)
            {
                classInfo += (" extends " + supercl.getName());
                //classInfo += "\n" +getClassInfo(supercl.getName());
            }
            classInfo += "\n{\n";
            classInfo += getConstructors(cl);
            classInfo += "\n";
            classInfo += getMethods(cl);
            classInfo += "\n";
            classInfo += getFields(cl);
            classInfo += "}"+"\n";
        }
        catch(ClassNotFoundException exception)
        {
            return "could not find the entered class, make sure its all spelled correctly\n";
//            exception.printStackTrace();
        }

        return classInfo;
    }

    /**
     *
     * @param className
     * @param options the first is for constructors, next methods, data members
     * @return
     */
    public static String getClassInfo(String className, boolean[] options)
    {
        String classInfo = "";
        if(options == null || options.length != 3)
        {
            options = new boolean[3];
            for(int i = 0 ; i<options.length; i ++)
            {
                options[i] = true;
            }
        }

        try
        {
            Class cl = Class.forName(className);
            Class supercl = cl.getSuperclass();
            classInfo = ("class " + className);
            if(supercl != null && supercl != Object.class)
            {
                classInfo += (" extends " + supercl.getName());
                //classInfo += "\n" +getClassInfo(supercl.getName());
            }
            classInfo += "\n{\n";
            if(options[0]) {
                classInfo += getConstructors(cl);
            }
            classInfo += "\n";
            if(options[1]) {
                classInfo += getMethods(cl);
            }
            classInfo += "\n";
            if(options[2]){
                classInfo += getFields(cl);
            }
            classInfo += "}"+"\n";
        }
        catch(ClassNotFoundException exception)
        {
            return "could not find the entered class, make sure its all spelled correctly\n";
//            exception.printStackTrace();
        }

        return classInfo;
    }


    /**
     *
     * @param cl a class
     * @return all the constructors as a string from the class cl
     */
    public static String getConstructors(Class cl)
    {
    	SortBy<Executable> sort = (a,b) -> a.getParameterCount()-b.getParameterCount();
        Object[] constructors = (Object[]) ArrayUtilities.sortList(cl.getDeclaredConstructors(),sort);
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
    	SortBy<Executable> sort = (a,b) -> a.getName().compareTo(b.getName()) ;
    	SortBy<Executable> nextSort = (a,b) -> a.getParameterCount()-b.getParameterCount();
        Method[] methods = (Method[]) ArrayUtilities.sortList(cl.getDeclaredMethods(), sort,nextSort);
        String message = "";
        
        for(int place = 0; place < methods.length; place++)
        {
        	Method method = (Method) methods[place];
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
    	SortBy<Field> sort = (a,b) -> a.getName().compareTo(b.getName());
        Field[] fields = (Field[]) ArrayUtilities.sortList(cl.getDeclaredFields(),sort);
        String message = "";

        for(int place = 0; place < fields.length; place++)
        {
        	Field f = (Field) fields[place];
            Class type = f.getType();
            String name = f.getName();
            message += "    " + Modifier.toString(f.getModifiers());
            message += " " + type.getName()+ " " + name+ ";\n";
        }
        return message;
    }

}
