package complete.assist;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.internal.core.codeassist.contexts.ClassObjMemberContext;

public class Context extends ClassObjMemberContext {

	@Override
	public boolean isValid(ISourceModule sourceModule, int offset,
			CompletionRequestor requestor) {

		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}

		// Search factory call among statement being edited
		FactoryClassnameResolver factorySearcher = new FactoryClassnameResolver(
				getStatementText());

		if (factorySearcher.containsFactoryCall()) {
			return true;
		}

		return false;
	}
}
