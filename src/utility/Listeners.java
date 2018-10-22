package utility;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

/**
 * This class have test listener adapter with test failure, skipped, success.
 * @author Prasad Tirunagari
 * Version 1.0
 * Date Modified 02/03/2016
 */
public class Listeners extends TestListenerAdapter {

	/* This method is executed on Test Failure	*/
	@Override
	public void onTestFailure(ITestResult result) {
		String rs = getMethodContext(result);
		System.err.println(rs + " | FAILED");
		Reporter.log(rs + " | FAILED");
	}

	/* This method is executed on Test Skipped	*/
	@Override
	public void onTestSkipped(ITestResult result) {
		String rs = getMethodContext(result);
		System.out.println(rs + " | SKIPPED");
		Reporter.log(rs + " | SKIPPED");
	}

	/* This method is executed on Test Success	*/
	@Override
	public void onTestSuccess(ITestResult result) {
		String rs = getMethodContext(result);
		System.out.println(rs + " | PASSED");
		Reporter.log(rs + " | PASSED");
	}

	/* This method is executed to get Testcase Name	*/
	private String getMethodContext(ITestResult result) {
		//String browser = result.getTestContext().getCurrentXmlTest()
		//.getParameter("browser");
		String testClasss = result.getTestClass().getName();
		String name = result.getName();
		String rs = testClasss + " | " + name;
		return rs;
	}
}
