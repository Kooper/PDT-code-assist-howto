package complete.assist;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.core.codeassist.ICompletionStrategyFactory;

public class StrategyFactory implements ICompletionStrategyFactory {

	@Override
	public ICompletionStrategy[] create(ICompletionContext[] contexts) {

		List<ICompletionStrategy> result = new LinkedList<ICompletionStrategy>();
		for (ICompletionContext context : contexts) {
			if (context.getClass() == complete.assist.Context.class) {
				result.add(new complete.assist.Strategy(context));
			}
		}

		return result.toArray(new ICompletionStrategy[result.size()]);
	}
}
