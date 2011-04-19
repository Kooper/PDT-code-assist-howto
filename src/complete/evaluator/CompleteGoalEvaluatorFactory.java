package complete.evaluator;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.compiler.ast.nodes.StaticMethodInvocation;

public class CompleteGoalEvaluatorFactory implements IGoalEvaluatorFactory {

	// The only method of the interface
	@Override
	public GoalEvaluator createEvaluator(IGoal goal) {

		Class<?> goalClass = goal.getClass();

		// Override only 'expression' type goals
		if (ExpressionTypeGoal.class != goalClass) {
			return null;
		}

		ASTNode expression = ((ExpressionTypeGoal) goal).getExpression();

		// Interested only in static methods calls
		if (!(expression instanceof StaticMethodInvocation)) {
			return null;
		}

		StaticMethodInvocation inv = (StaticMethodInvocation) expression;
		ASTNode reciever = inv.getReceiver();

		GoalEvaluator result = null;

		// Check that expression being analyzed consists of our factory
		// structure. Assume it is Factory_Classname::factory('name')
		if (reciever instanceof SimpleReference
				&& "Factory_Classname".equals(((SimpleReference) reciever)
						.getName())
				&& "factory".equals(inv.getCallName().getName())) {
			// result would be a goal evaluator object for our factory
			result = produceGoalEvaluator(inv, goal);
		}

		return result;
	}

	/**
	 * Returns goal evaluator created from the value of the first call argument.
	 */
	private GoalEvaluator produceGoalEvaluator(StaticMethodInvocation inv,
			IGoal goal) {

		// Take call arguments
		List<?> arguments = inv.getArgs().getChilds();

		if (arguments.size() == 1) {
			Object first = arguments.get(0);

			// We are interested only in first string argument of the method
			if (first instanceof Scalar
					&& ((Scalar) first).getScalarType() == Scalar.TYPE_STRING) {
				String argumentValue = ((Scalar) first).getValue();
				if (argumentValue.length() < 3) {
					// String consisting of two characters, including quotes
					return null;
				}

				// Strip quotes from the beginning and end of the string
				argumentValue = argumentValue.substring(1,
						argumentValue.length() - 1);

				return new CompleteGoalEvaluator(goal, argumentValue);
			}
		}

		return null;
	}
}
