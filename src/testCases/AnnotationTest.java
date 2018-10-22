package testCases;

import org.testng.TestNG;
import org.testng.annotations.Test;

import utility.Annotation;
import utility.Constant;
import utility.ExcelUtils;

public class AnnotationTest {

	@Test
	public void testAnnotation() throws Exception{
		ExcelUtils.openExcel(Constant.Path_TestData, Constant.Master_Sheet);
		TestNG tng = new TestNG();
		Annotation anno = new Annotation();
		tng.setTestClasses(new Class[] {TestReport.class});
		tng.setAnnotationTransformer(anno);
		tng.run();		
	}
}
