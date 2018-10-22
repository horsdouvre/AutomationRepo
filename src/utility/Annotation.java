package utility;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class Annotation implements IAnnotationTransformer{
	public int count =1;

	@SuppressWarnings("rawtypes")
	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		int size = ExcelUtils.getRowCount("Master");

		for(int i=2; i<size; i++){
			if(ExcelUtils.getCellData("Master", "NeedToExecute", i).equalsIgnoreCase("Yes")){
				String c = ExcelUtils.getCellData("Master", "TestCaseName", i);
				String m = testMethod.getName();
				
				count = ExcelUtils.getTotal(c, m);
				
				if (m.equals(testMethod.getName())) {
					annotation.setInvocationCount(count);
				}
			}
		}
	}
}