package utility;

import org.testng.IInvokedMethod;

import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
/**
 * This class used for Test Listener Adapter.
 * @author Prasad Tirunagari
 * Version 1.0
 * Date Modified 02/03/2016
 */
public class TestListenerAdapter implements IInvokedMethodListener {

	public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {}
	
	public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {}
	
}
