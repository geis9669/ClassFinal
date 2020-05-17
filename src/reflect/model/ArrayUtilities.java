package reflect.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds code to do things with arrays like sorting
 * @author Greg
 *
 */
public class ArrayUtilities {
	
	
	/**
     * this sorts a list by the operations in the first sort and innersorts
     * @param <T> the type of array to be sorted
     * @param list the list to be sorted
     * @param firstSort the first sort to be applied to the list
     * @param innerSorts sort by first sort then by inner going one deeper for each time they are the same
     * @return the sorted list as an ObjectArray cast it to what it was entered as
     */
    public static <T> T[] sortList(T[] list, SortBy<T> firstSort, SortBy<T>... innerSorts)
    {
    	SortBy<T>[] nextList = (SortBy<T>[]) Array.newInstance(innerSorts.getClass().getComponentType(), innerSorts.length+1);
    	nextList[0] = firstSort;
    	for(int index = 0; index<innerSorts.length ; index++)
    	{
    		nextList[index+1] = innerSorts[index];
    	}
    	return sortList(list, nextList);
    }
    
    /**
     * this sorts a list by the operations in the sorts array starting with the first item until one is not equal
     * @param <T> the type of array to be sorted
     * @param array the array to be sorted
     * @param sorts sorts by first item in sorts then by inner going one deeper for each time they are the same
     * @return the sorted list as an ObjectArray cast it to what it was entered as
     */
    public static <T> T[] sortList(T[] array, SortBy<T>[] sorts)
    {
        if(array.length <= 1)
        {
            return array;
        }
        T pivot = array[0];
        List<T> first = new ArrayList<>();
        List<T> last = new ArrayList<>();
        for (int index = 1; index < array.length; index++) {
        	T current = array[index];
            int same = 0;
            int time = 0;
        	while(same == 0) {	
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
        Class arrayClass = array.getClass().getComponentType();
        T[] middle = (T[]) Array.newInstance(arrayClass, 1); 
        middle[0] = pivot;
        T[] firstArray = (T[]) Array.newInstance(arrayClass, first.size());
        T[] lastArray = (T[]) Array.newInstance(arrayClass, last.size());
        first.toArray(firstArray);
        lastArray = last.toArray(lastArray);

        return (T[]) combineArraysg((T[]) combineArraysg(sortList(firstArray,sorts), middle), sortList(lastArray,sorts));
    }
    
    /**
     * combines two arrays after making sure they are the same type. 
     * adds the second array to the first array
     * @param firstArray the list that is first in the new list
     * @param lastList the list that starts after the first list
     * @return the new list as an object which needs to be cast or a Object array with 0 elements
     */
    public static Object combineArrays(Object[] firstArray, Object[] lastList)
    {
        Class cl1 = firstArray.getClass();
        Class cl2 = lastList.getClass();
        Class componentType1 = cl1.getComponentType();

        //cl1.isInstance(cl2)
        if(cl1.getComponentType().getName().contentEquals(cl2.getComponentType().getName()))
        {
            int newLength = firstArray.length + lastList.length;
            Object newArray = Array.newInstance(componentType1, newLength);
            System.arraycopy(firstArray, 0, newArray,0,firstArray.length);
            System.arraycopy(lastList, 0, newArray, firstArray.length, lastList.length);

            return newArray;
        }
        else
        {
            Object emptyArray = Array.newInstance(componentType1, 0);
            return emptyArray;
        }
    }
    
    /**
     * combines two arrays with the second after the first. 
     * @param firstArray the array that is first in the new array
     * @param lastArray the array that starts after the first array
     * @return the new array as an object which needs to be cast or a Object array with 0 elements
     */
    public static <T> T[] combineArraysg(T[] firstArray, T[] secondArray)
    {
    	T[] combinedArray = (T[]) Array.newInstance(firstArray.getClass().getComponentType(), firstArray.length+secondArray.length);
    	System.arraycopy(firstArray, 0, combinedArray, 0, firstArray.length);
    	System.arraycopy(secondArray, 0, combinedArray, firstArray.length, secondArray.length);
    	return combinedArray;
    }
}
