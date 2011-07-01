package idao;

import java.lang.reflect.Method;
import java.util.List;

public interface IFinderExecutor<T> {
	List<T> executeFinder(Method method, final Object[] queryArgs);
}
