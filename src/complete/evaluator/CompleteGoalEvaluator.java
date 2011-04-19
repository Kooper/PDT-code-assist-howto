package complete.evaluator;

import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.typeinference.PHPClassType;

public class CompleteGoalEvaluator extends GoalEvaluator {

	private final String argumentValue;

	public CompleteGoalEvaluator(IGoal goal, String argumentValue) {
		super(goal);
		this.argumentValue = argumentValue;
	}

	@Override
	public Object produceResult() {
		// Assuming our factory method produces objects of
		// 'Prefix_argumentValue'
		String effectiveClassname = "Prefix_" + argumentValue;
		return new PHPClassType(effectiveClassname);
	}

	@Override
	public IGoal[] init() {
		return null;
	}

	@Override
	public IGoal[] subGoalDone(IGoal subgoal, Object result, GoalState state) {
		return null;
	}

}
