package utility;

import java.util.List;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestNG;
import org.testng.internal.Utils;

/**
 * This class have the start and finish test results.
 * @author Prasad Tirunagari
 * Version 1.0
 * Date Modified 02/03/2016
 */

public class Listener implements ITestListener, IInvokedMethodListener {
	String method=null;
	static ITestResult resultT;
	/* This method is executed for Testcase finish	*/
	public void onFinish(ITestContext arg0) {
		//System.out.println("Result -->"+resultT.getStatus());
//		System.out.println(ITestResult.FAILURE+"  "+ITestResult.SKIP+"  "+ITestResult.SUCCESS+"  "+ITestResult.SUCCESS_PERCENTAGE_FAILURE);
//		Reporter.log("****************************************************************************************",true);
//		Reporter.log("****************************************************************************************",true);
//		Reporter.log("Completed executing test " + arg0.getName(), true);
//		Reporter.log("@@@@@@@@@@@@@@@@@@@@@                "+arg0.getName()+ "       @@@@@@@@@@@@@@@@@@@@@@@@@",true);
//		Reporter.log("****************************************************************************************",true);
//		Reporter.log("****************************************************************************************",true);
	}
	/* This method is executed for Testcase Start	*/
	public void onStart(ITestContext arg0) {
//		Reporter.log("****************************************************************************************",true);
//		Reporter.log("****************************************************************************************",true);
//		Reporter.log("About to begin executing test " + arg0.getName(), true);
//		Reporter.log("$$$$$$$$$$$$$$$$$$$$$                "+arg0.getName()+ "       $$$$$$$$$$$$$$$$$$$$$$$$$",true);
//		Reporter.log("****************************************************************************************",true);
//		Reporter.log("****************************************************************************************",true);	
	}
	/* This method is executed for Testcase Name	*/
	private String getMethodContext(ITestResult result) {
		//String browser = result.getTestContext().getCurrentXmlTest()
		//.getParameter("browser");
		String[] testClasss = result.getTestClass().getName().split("\\.");
		String name = result.getName();
		String rs = testClasss[1] + " | " + name;
		return rs;
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
	}

	/* This method is executed for Testcase Failure	*/
	public void onTestFailure(ITestResult arg0) {
		String rs = getMethodContext(arg0);
//		System.out.println(rs + " | FAILED");
		Reporter.log(rs + " | FAILED");
		printTestResults(arg0);
	}
	
	/* This method is executed to print Test Results	*/
	private void printTestResults(ITestResult result) {
		String[] metname = method.split("\\.");
		Reporter.log("TestName = " + metname[1], true);
		String[] jiraID = metname[1].split("_");
		Reporter.log("Jira ID = " + jiraID[1] + "-" + jiraID[2]);
		String[] clsname = result.getTestClass().getName().split("\\.");
		Reporter.log("Test Method resides in Class: " + clsname[1], true);
		if (result.getParameters().length != 0) {
			String params = null;
			for (Object parameter : result.getParameters()) {
				params += parameter.toString() + ",";
			}
			Reporter.log("Test Method had the following parameters : " + params, true);
		}
		String status = null;
		switch (result.getStatus()) {
		case ITestResult.SUCCESS:
			status = "Pass";
			break;
		case ITestResult.FAILURE:
			status = "Failed";
			break;
		case ITestResult.SKIP:
			status = "Skipped";
		}
		Reporter.log("Test Status: " + status, true);
		Reporter.log("---------------------------------------------------------------------------------------------------",true);
	}
	
	/* This method is executed for Testcase Skipped	*/
	public void onTestSkipped(ITestResult arg0) {
		String rs = getMethodContext(arg0);
//		System.out.println(rs + " | SKIPPED");
		Reporter.log(rs + " | SKIPPED");
		printTestResults(arg0);
	}
	/* This method is executed for Testcase Start	*/
	public void onTestStart(ITestResult arg0) {
//		System.out.println("Calling onTestStart");
	}
	/* This method is executed for Testcase Success	*/
	public void onTestSuccess(ITestResult arg0) {
		String rs = getMethodContext(arg0);
//		System.out.println(rs + " | PASSED");
		Reporter.log(rs + " | PASSED");
		printTestResults(arg0);
	}

	public void afterInvocation(IInvokedMethod method, ITestResult result) {
		resultT = result;
		String[] metname = returnMethodName(method.getTestMethod()).split("\\.");
		String textMsg = "Completed executing " + metname[1];
		String s = returnMethodName(method.getTestMethod());
		if(s.contains("beforeClass") || s.contains("afterClass") || s.contains("beforeTestCase") || s.contains("afterTestCase")){	
		}else{
			Reporter.log(textMsg, true);
		}
		String[] clsname = result.getTestClass().getName().split("\\.");
		if(s.contains("afterClass")){
			Reporter.log("=========================================   Test Suite "+clsname[1] +" - Testing Completed   ========================================= \n\n",true);
		}
//		result.setAttribute(name, value);
		
		
		Reporter.setCurrentTestResult(result);

		if (method.isTestMethod()) {

			List<Throwable> verificationFailures = TestBase.getVerificationFailures();

			//if there are verification failures...
			if (verificationFailures.size() > 0) 
			{

				//set the test to failed
				result.setStatus(ITestResult.FAILURE);

				//if there is an assertion failure add it to verificationFailures
				if (result.getThrowable() != null) {
					verificationFailures.add(result.getThrowable());
				}

				int size = verificationFailures.size();
				//if there's only one failure just set that
				if (size == 1) {
					result.setThrowable(verificationFailures.get(0));
				} else {
					//create a failure message with all failures and stack traces (except last failure)
					StringBuffer failureMessage = new StringBuffer("Multiple failures (").append(size).append("):\n\n");
					for (int i = 0; i < size-1; i++) {
						failureMessage.append("Failure ").append(i+1).append(" of ").append(size).append(":\n");
						Throwable t = verificationFailures.get(i);
						String fullStackTrace = Utils.stackTrace(t, false)[0];
						failureMessage.append(fullStackTrace).append("\n\n");
					}

					//final failure
					Throwable last = verificationFailures.get(size-1);
					failureMessage.append("Failure ").append(size).append(" of ").append(size).append(":\n");
					failureMessage.append(last.toString());

					//set merged throwable
					Throwable merged = new Throwable(failureMessage.toString());
					merged.setStackTrace(last.getStackTrace());

					result.setThrowable(merged);
				}
			}
		}
	}

	public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {
		TestNG tn= new TestNG();
		tn.setDefaultTestName(returnMethodName(arg0.getTestMethod()));
		String[] metname = returnMethodName(arg0.getTestMethod()).split("\\.");
		String textMsg = "About to begin executing " + metname[1];
		method = returnMethodName(arg0.getTestMethod());
		if(method.contains("beforeClass") || method.contains("afterClass") || method.contains("beforeTestCase") || method.contains("afterTestCase")){	
		}else{
			Reporter.log(textMsg, true);
		}
		String[] clsname = arg1.getTestClass().getName().split("\\.");
		if(method.contains("beforeClass")){
			Reporter.log("=========================================   Test Suite "+ clsname[1] +" - Testing Initiated   =========================================",true);
		}
	}

	private String returnMethodName(ITestNGMethod method) {
		return method.getRealClass().getSimpleName() + "." + method.getMethodName();
	}

}


