package gov.dot.nhtsa.odi.ewr.aws.utils;

/**
 * @author Bienvenido Ortiz
 */
public class EwrLogs {

	private EwrLogs() {

	}

	public static void DEBUG(String className, String methodName, String message) {
		System.out.println(className + "." + methodName + "() -->  " + message);
	}

	public static void ERROR(String className, String methodName, String message) {
		System.out.format("[ERROR] - %s.%s() --> %s", className, methodName, message);
	}
}
