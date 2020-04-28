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
        Object[] constructors = (Object[]) sortList(cl.getDeclaredConstructors(),sort);
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
        Method[] methods = (Method[]) sortList(cl.getDeclaredMethods(), sort,nextSort);
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
        Field[] fields = (Field[]) sortList(cl.getDeclaredFields(),sort);
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

    /**
     * this sorts a list by the operations in the first sort and innersorts
     * @param <T> the type of array to be sorted
     * @param list the list to be sorted
     * @param firstSort the first sort to be applied to the list
     * @param innerSorts sort by first sort then by inner going one deeper for each time they are the same
     * @return the sorted list as an ObjectArray
     */
    private static <T> T[] sortList(T[] list, SortBy<T> firstSort, SortBy<T>... innerSorts)
    {
    	SortBy<T>[] nextList = (SortBy<T>[]) Array.newInstance(innerSorts.getClass().getComponentType(), innerSorts.length+1);
    	nextList[0] = firstSort;
    	for(int index = 0; index<innerSorts.length ; index++)
    	{
    		nextList[index+1] = innerSorts[index];
    	}
    	return sortList(list, nextList);
    }
    
    private static <T> T[] sortList(T[] list, SortBy<T>[] sorts)
    {
        if(list.length <= 1)
        {
            return list;
        }
        T pivot = list[0];
        List<T> first = new ArrayList<>();
        List<T> last = new ArrayList<>();
        for (int index = 1; index < list.length; index++) {
        	T current = list[index];
            int same = 0;
            int time = 0;
        	while(same == 0) {// need to make it so it will sort on many different levels      		
            	if(time < sorts.length) {
            		same = sorts[time].sortBy(current, pivot);
            		time++;
            	}else {
            		same = -1;
            	}
            	if(same<0) {
                	first.add(current);
            	} 
            	if(same> 0) {
                	last.add(current);
            	}
        	}
        }
        Class listClass = list.getClass().getComponentType();
        T[] middle = (T[]) Array.newInstance(listClass, 1); 
        middle[0] = pivot;
        T[] firstArray = (T[]) Array.newInstance(listClass, first.size());
        T[] lastArray = (T[]) Array.newInstance(listClass, last.size());
        first.toArray(firstArray);
        lastArray = last.toArray(lastArray);

        return (T[]) combineArrays((T[]) combineArrays(sortList(firstArray,sorts), middle), sortList(lastArray,sorts));
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
