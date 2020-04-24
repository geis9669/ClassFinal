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
        if(options.length != 3)
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

    /**
     * Sorts a list that is of type Executable
     * @param list the list you want sorted
     * @return the sorted array, or an array of size 0 if the list is not of type Executable,
     *  or the list if its size is less then or equal to 1
     */
    private static Executable[] sortByParameters(Executable[] list)
    {
        if(list.length <= 1)
        {
            return list;
        }
        Executable pivot = list[0];
        List<Executable> first = new ArrayList<>();
        List<Executable> last = new ArrayList<>();

        for (int index = 1; index < list.length; index++) {
            Executable current = list[index];
            if (current.getParameterCount() <= pivot.getParameterCount()) {
                first.add(current);
            } else if (current.getParameterCount() > pivot.getParameterCount()) {
                last.add(current);
            }
        }
        Executable[] middle = {pivot};

        Executable[] firstArray = new Executable[first.size()];
        Executable[] lastArray = new Executable[last.size()];
        first.toArray(firstArray);
        lastArray = last.toArray(lastArray);

        return (Executable[]) combineArrays((Executable[]) combineArrays( sortByParameters(firstArray), middle), sortByParameters(lastArray));
    }

    private static Executable[] sortByName(Executable[] list) {
        if (list.length <= 1) 
            return list;
        int half = list.length / 2;
        Executable[] first = new Executable[half];//list.length/2];
        Executable[] second = new Executable[half + list.length % 2];
        //splits the array in half
        for (int index = 0; index < list.length; index++) 
            if (index < half) 
                first[index] = list[index];
            else 
                second[index - half] = list[index];
        first = sortByName(first);
        second = sortByName(second);

        // compare the lists and make them one in the correct order.
        int indexList = 0;
        int indexFirst = 0;
        int indexSecond = 0;
        while(indexList < list.length)
        {
            if(indexFirst >= first.length)
            {
                list[indexList] = second[indexSecond];
                indexSecond++;
            }
            else if(indexSecond >= second.length)
            {
                list[indexList] = first[indexFirst];
                indexFirst++;
            }
            else
            {
                if (first[indexFirst].getName().compareTo(second[indexSecond].getName()) < 0) 
                {
                    list[indexList] = first[indexFirst];
                    indexFirst++;
                } 
                else if (first[indexFirst].getName().compareTo(second[indexSecond].getName()) > 0) 
                {
                    list[indexList] = second[indexSecond];
                    indexSecond++;
                }
                else
                {
                    list[indexList] = first[indexFirst];
                    indexFirst++;
                    indexList++;
                    list[indexList] = second[indexSecond];
                    indexSecond++;
                }
            }
            indexList++;
        }
        return list;
    }

    /**
     * combines two arrays after making sure they are the same type. 
     * adds the second array to the first array
     * @param firstList the list that is first in the new list
     * @param lastList the list that starts after the first list
     * @return the new list as an object which needs to be cast or a Object array with 0 elements
     */
    public static Object combineArrays(Object[] firstList, Object[] lastList)
    {
        Class cl1 = firstList.getClass();
        Class cl2 = lastList.getClass();
        Class componentType1 = cl1.getComponentType();

        //cl1.isInstance(cl2)
        if(cl1.getComponentType().getName().contentEquals(cl2.getComponentType().getName()))
        {
            int newLength = firstList.length + lastList.length;
            Object newArray = Array.newInstance(componentType1, newLength);
            System.arraycopy(firstList, 0, newArray,0,firstList.length);
            System.arraycopy(lastList, 0, newArray, firstList.length, lastList.length);

            return newArray;
        }
        else
        {
            Object emptyArray = Array.newInstance(componentType1, 0);
            return emptyArray;
        }
    }
}