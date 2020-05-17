package reflect.model;

/**
 * this is used to specifiy a sort
 * @author Greg
 *
 * @param <T> the type that you want to sort
 */
public interface SortBy<T> {
	/**
	 * how you want the two items sorted
	 * @param item1 the first item
	 * @param item2 the second item
	 * @return zero is equal, the others are determined how you want and what you use this with
	 */
	public int sortBy(T item1,T  item2);
}
