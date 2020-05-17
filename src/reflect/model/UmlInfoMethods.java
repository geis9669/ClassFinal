package reflect.model;

import java.lang.reflect.*;

/**
 * a place to leave methods getting information and formating the information
 * for the class to return
 * @author Greg
 *
 */
public class UmlInfoMethods 
{
	
    /**
     * This gets info about a class and puts it in a string
     * format
     * @param classClass the class to get the info from
     * @param options the first is for constructors, next methods, data members, can be null
     * @return the information
     */
    public static String getClassInfo(Class classClass, boolean[] options)
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

            Class supercl = classClass.getSuperclass();
            classInfo = ""+ classClass;
            if(supercl != null )
            {
                classInfo += (" extends " + supercl.getName());
            }
            
            classInfo += "\n";
            Class[] interfaces= classClass.getInterfaces();
            if(interfaces.length > 0)
            {
            	classInfo += "implements" ;
            	for(int index = 0; index<interfaces.length; index++)
            	{
            		if(index%2==0)
            		{
            			classInfo+="\n";
            		}
            		Class implemted = interfaces[index];
            		classInfo+=implemted.getName()+"  ";
            	}
            }
            
            classInfo += "\n{\n";
            if(options[0]) 
            {
            	SortBy<Executable> sort = (a,b) -> a.getParameterCount()-b.getParameterCount();
            	Constructor[] constructors = (Constructor[]) ArrayUtilities.sortList(classClass.getDeclaredConstructors(),sort);
                classInfo += formatConstructors(constructors);
            }
            classInfo += "\n";
            if(options[1]) 
            {
            	SortBy<Executable> sortm = (a,b) -> a.getName().compareTo(b.getName()) ;
            	SortBy<Executable> nextSortm2 = (a,b) -> a.getParameterCount()-b.getParameterCount();
                Method[] methods = (Method[]) ArrayUtilities.sortList(classClass.getDeclaredMethods(), sortm,nextSortm2);
                classInfo += formatMethods(methods);
            }
            classInfo += "\n";
            if(options[2])
            {
            	SortBy<Field> sort = (a,b) -> a.getName().compareTo(b.getName());
                Field[] fields = (Field[]) ArrayUtilities.sortList(classClass.getDeclaredFields(),sort);
                classInfo += formatFields(fields);
            }
            classInfo += "}"+"\n";
            
        return classInfo;
    }

    /**
     * takes a Array of Constructors and makes a string of them
     * It places one item on each line
     * @param Constructors the array to have the information gotten out of
     * @return a string of the gotten information
     */
	private static String formatConstructors(Constructor[] constructors) {
		String message = "";

        for(int place = 0; place < constructors.length; place++)
        {
            Constructor thisClass = constructors[place];

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
     * takes a Array of Methods and makes a string of them
     * It places one item on each line
     * @param methods the array to have the information gotten out of
     * @return a string of the gotten information
     */
	private static String formatMethods(Method[] methods) {
		String message = "";
        for(int place = 0; place < methods.length; place++)
        {
        	Method method = methods[place];
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
     * takes a Array of fields and makes a string of them
     * It places one item on each line
     * @param fields the array to have the information gotten out of
     * @return a string of the gotten information
     */
    public static String formatFields(Field[] fields)
    {
    	String message = "";
    	for(int place = 0; place < fields.length; place++)
        {
        	Field f = fields[place];
            Class type = f.getType();
            String name = f.getName();
            message += "    " + Modifier.toString(f.getModifiers());
            message += " " + type.getName()+ " " + name+ ";\n";
        }
    	return message;
    }

}
