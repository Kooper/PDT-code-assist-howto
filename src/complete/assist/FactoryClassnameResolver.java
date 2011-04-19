package complete.assist;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.util.text.TextSequence;

public class FactoryClassnameResolver {

	private String effectiveClassName;
	private int offset = 0;

	public FactoryClassnameResolver(TextSequence inputString) {

		Pattern factoryPattern = Pattern
				.compile("\\s*Factory_Classname\\s*::\\s*factory\\s*[(]\\s*(['\"])(\\w+)\\1\\s*[)]\\s*");

		Matcher classNameSearcher = factoryPattern.matcher(inputString);
		if (classNameSearcher.find()) {
			effectiveClassName = getClassname(classNameSearcher.group(2));
			offset = classNameSearcher.end();
		}
	}

	/**
	 * Returns true if input string contains factory call.
	 * 
	 * @return true or false
	 */
	public boolean containsFactoryCall() {
		return !effectiveClassName.isEmpty();
	}

	/**
	 * Returns PHP type deducted from factory call
	 * 
	 * @return PHP class type
	 */
	public PHPClassType getClassType() {
		return new PHPClassType(effectiveClassName);
	}

	/**
	 * Returns offset in context string where Factory::get static call ends.
	 * 
	 * @return end position of broker call
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * Transforms supplied parameter into PHP class name according to defined
	 * conventions.
	 * 
	 * 'abc' -> My_Abc
	 * 
	 * @param className
	 * @return
	 */
	public static String getClassname(String className) {

		if (className.length() < 1) {
			return "";
		}

		String classPrefix = "Prefix_";
		String capitalizedClassName = className.substring(0, 1).toUpperCase()
				+ className.substring(1);

		return classPrefix + capitalizedClassName;
	}
}
