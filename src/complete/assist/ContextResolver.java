package complete.assist;

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionContextResolver;
import org.eclipse.php.internal.core.codeassist.contexts.CompletionContextResolver;

public class ContextResolver extends CompletionContextResolver implements
		ICompletionContextResolver {
	@Override
	public ICompletionContext[] createContexts() {
		return new ICompletionContext[] { new complete.assist.Context() };
	}
}
