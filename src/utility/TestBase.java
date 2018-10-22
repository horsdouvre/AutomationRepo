package utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;


/**
 * 
 * @author bpatel16
 * Updates Made to the Verify Methods
 */

public class TestBase {

	private static Map<ITestResult, List<Throwable>> verificationFailuresMap = new HashMap<ITestResult, List<Throwable>>();

	private static void assertTrue(boolean condition) {
		Assert.assertTrue(condition);
	}

	private static void assertTrue(boolean condition, String message) {
		Assert.assertTrue(condition, message);
	}

	private static void assertFalse(boolean condition) {
		Assert.assertFalse(condition);
	}

	private static void assertFalse(boolean condition, String message) {
		Assert.assertFalse(condition, message);
	}

	private static void assertEquals(boolean actual, boolean expected) {
		Assert.assertEquals(actual, expected);
	}

	private static void assertEquals(Object actual, Object expected) {
		Assert.assertEquals(actual, expected);
	}

	private static void assertEquals(Object[] actual, Object[] expected) {
		Assert.assertEquals(actual, expected);
	}

	private static void assertEquals(Object actual, Object expected, String message) {
		Assert.assertEquals(actual, expected, message);
	}

	public static boolean verifyTrue(boolean condition) {
		boolean flag = true;
		try {
			assertTrue(condition);
		} catch(Throwable e) {
			flag = false;
			addVerificationFailure(e);
		}
		return flag;
	}

	public static boolean verifyTrue(boolean condition, String message) {
		boolean flag = true;
		try {
			assertTrue(condition, message);
		} catch(Throwable e) {
			flag = false;
			addVerificationFailure(e);
		}
		return flag;
	}

	public static boolean verifyFalse(boolean condition) {
		boolean flag = true;
		try {
			assertFalse(condition);
		} catch(Throwable e) {
			flag = false;
			addVerificationFailure(e);
		}
		return flag;
	}

	public static boolean verifyFalse(boolean condition, String message) {
		boolean flag = true;
		try {
			assertFalse(condition, message);
		} catch(Throwable e) {
			flag=false;
			addVerificationFailure(e);
		}
		return flag;
	}

//	public static void verifyEquals(boolean actual, boolean expected) {
//		try {
//			assertEquals(actual, expected);
//		} catch(Throwable e) {
//			addVerificationFailure(e);
//		}
//	}

	public static boolean verifyEquals(boolean actual, boolean expected) throws Exception {
		boolean flag= true;
		try {
			assertEquals(actual, expected);
		} catch(Throwable e) {
			flag = false;
			addVerificationFailure(e);
		}
		return flag;
	}

	public static boolean verifyEquals(Object actual, Object expected) {
		boolean flag= true;
		try {
			assertEquals(actual, expected);
		} catch(Throwable e) {
			flag = false;
			addVerificationFailure(e);
		}
		return flag;
	}

	public static boolean verifyEquals(String actual, String expected) throws Exception {
		boolean flag= true;
		try {
			assertEquals(actual, expected);
		} catch(Throwable e) {
			flag = false;
			addVerificationFailure(e);
		}
		return flag;
	}

	public static boolean verifyEquals(Object[] actual, Object[] expected) {
		boolean flag = true;
		try {
			assertEquals(actual, expected);
		} catch(Throwable e) {
			flag = false;
			addVerificationFailure(e);
		}
		return flag;
	}

	public static boolean verifyEquals(Object actual, Object expected, String message){
		boolean flag = true;
		try {
			assertEquals(actual, expected, message);
		} catch(Throwable e) {
			flag = false;
			addVerificationFailure(e);
		}
		return flag;
	}
	
	public static void fail(String message) {
		Assert.fail(message);
	}

	public static List<Throwable> getVerificationFailures() {
		List<Throwable> verificationFailures = verificationFailuresMap.get(Reporter.getCurrentTestResult());
		return verificationFailures == null ? new ArrayList<Throwable>() : verificationFailures;
	}

	private static void addVerificationFailure(Throwable e) {
		List<Throwable> verificationFailures = getVerificationFailures();
		verificationFailuresMap.put(Reporter.getCurrentTestResult(), verificationFailures);
		verificationFailures.add(e);
	}
	
	public static boolean verifyCompare(String actual, String expected) {
			return verifyTrue(actual.contains(expected));
	}
	
	public static boolean verifyCompare(String actual, String expected, String errorMessage) {
		return verifyTrue(actual.contains(expected),errorMessage);
}
}
