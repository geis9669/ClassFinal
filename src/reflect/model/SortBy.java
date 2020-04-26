package reflect.model;

import java.lang.reflect.*;

public interface SortBy<T> {
	public boolean sortBy(T item1,T  item2);
}
