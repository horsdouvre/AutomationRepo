package com.albertsons.portal.automation.common;

import atu.alm.wrapper.ALMServiceWrapper;
import atu.alm.wrapper.ITestCase;

import atu.alm.wrapper.ITestCaseRun;
import atu.alm.wrapper.enums.StatusAs;
import atu.alm.wrapper.exceptions.ALMServiceException;
import com.relevantcodes.extentreports.LogStatus;
import com.albertsons.portal.automation.constants.GlobalConstants;
import  com.albertsons.portal.automation.reportGeneration.ExtentManager;
import  com.albertsons.portal.automation.reportGeneration.ExtentTestManager;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;


/*#################################################################################
  # Class Name: ALMIntegration                                                    #
  # Author:                                                                       #
  # Description:                                                                  #
  # Initialize ALM Connection                                                     #
  # Update test case results to ALM                                               #
  #                                                                               #
  #################################################################################
*/
public class ALMIntegration {

    private ALMServiceWrapper almWrapper = null;
    private String almURL;
    private String almUserName;
    private String almPassword;
    private String almDomain;
    private String almProject;
    private String almTestSetPath;
    private String almTestSetName;
    private String almTestSetID;
    private String testCaseName;
    public String scriptName;
    public int stepNo;

    private ITestCaseRun objRun = null;
    private ITestCase objTestCase = null;
    private String testStepExecutionResults [][] = new String [1][5];


    public ALMIntegration(DataExtractor dataExtractor) {
        almURL = dataExtractor.almURL;
        almUserName = dataExtractor.almUserName;
        almPassword = dataExtractor.almPassword;
        almDomain = dataExtractor.almDomain;
        almProject = dataExtractor.almProject;
        almTestSetPath = dataExtractor.almTestSetPath;
        almTestSetName = dataExtractor.almTestSetName;
        almTestSetID = dataExtractor.almTestSetID;
        testCaseName = dataExtractor.almTestCaseName;

        scriptName = dataExtractor.scriptName;

        almWrapper = new ALMServiceWrapper(almURL);
    }

    public void initializeConnection() throws ALMServiceException {
        if(GlobalConstants.almIntegration.equals("ON")) {
            boolean isConnected = almWrapper.connect(almUserName, almPassword, almDomain, almProject);
            if (isConnected) {
                System.out.println("Connected to ALM!");
            } else {
                System.out.println("Unable to connect to ALM.");
            }
        }
    }


    public void updateTestCaseResult(DataExtractor dataExtractor,StatusAs finalstatus) throws ALMServiceException {
        StatusAs status;
        int j;
        if (GlobalConstants.generalTestCaseStatus.equals("Passed")) {
            status = StatusAs.PASSED;
        } else if (GlobalConstants.generalTestCaseStatus.equals("N/A")) {
            status = StatusAs.N_A;
        } else {
            status = StatusAs.FAILED;
        }
        if (finalstatus == StatusAs.FAILED){
            status = finalstatus;
        }


        if(GlobalConstants.almIntegration.equals("ON")) {

            System.out.println("############################################");
            System.out.println(almTestSetPath);
            System.out.println(almTestSetName);
            System.out.println(almTestSetID);
            System.out.println(testCaseName);
            System.out.println(status);
            System.out.println("############################################");

            objTestCase = almWrapper.updateResult(almTestSetPath, almTestSetName, Integer.valueOf(almTestSetID), testCaseName, status);
            objRun = almWrapper.createNewRun(objTestCase, LocalDateTime.now().toString(), status);
            for (int i = 0; i < testStepExecutionResults.length; i++) {
                if(testStepExecutionResults[i][1]!=null) {
                    if (testStepExecutionResults[i][1].equals("Passed")) {
                        status = StatusAs.PASSED;
                    } else if (testStepExecutionResults[i][1].equals("Failed")) {
                        status = StatusAs.FAILED;
                    } else if (testStepExecutionResults[i][1].equals("N/A")) {
                        status = StatusAs.N_A;
                    }

                    almWrapper.addStep(objRun, "Step " + testStepExecutionResults[i][0], status, testStepExecutionResults[i][2], testStepExecutionResults[i][3], testStepExecutionResults[i][4]);
                    j = i + 1;

                    if (finalstatus == StatusAs.FAILED && j == testStepExecutionResults.length) {
                        almWrapper.addStep(objRun, "Step " + dataExtractor.stepNo, StatusAs.NOT_COMPLETED, "This test case is aborted", "Failed", "Terminated");
                    }
                }
            }
            terminateConnection();
        } else {
            System.out.println("############################################");
            System.out.println(scriptName+"::"+status);
            System.out.println("############################################");
        }
    }

    public void addExecutionStep(DataExtractor dataExtractor, String testStatus) throws Exception {
        String actual;

       // String testDetails=dataExtractor.TestCaseReader(dataExtractor.stepNo);
       // String[] arr1=testDetails.split("\\$");
        if (dataExtractor.stepNo > 1) {
            testStepExecutionResults = (String[][]) resizeArray(testStepExecutionResults, testStepExecutionResults.length + 1);
            if (testStepExecutionResults[testStepExecutionResults.length - 1] == null){
                testStepExecutionResults[testStepExecutionResults.length - 1] = new String[5];
            }
        }
        if (testStatus.equals("Failed")){
            GlobalConstants.generalTestCaseStatus = "Failed";
            //actual = "Actual is FAILED::["+arr1[2]+"]";
            actual = dataExtractor.stepFailActual;
            ExtentTestManager.generateExtentReport(GlobalConstants.ReportStatus.FAIL.toString(),dataExtractor);
        } else {
            //actual = arr1[2];
            actual = dataExtractor.stepPassActual;
            //Assert.assertTrue(true, dataExtractor.stepFailActual);
            ExtentTestManager.generateExtentReport(GlobalConstants.ReportStatus.PASS.toString(), dataExtractor);
        }
        testStepExecutionResults[testStepExecutionResults.length - 1][0] = Integer.toString(dataExtractor.stepNo);
        testStepExecutionResults[testStepExecutionResults.length - 1][1] = testStatus;
        testStepExecutionResults[testStepExecutionResults.length - 1][2] = dataExtractor.stepDescription;
        testStepExecutionResults[testStepExecutionResults.length - 1][3] = dataExtractor.stepExpected;
        testStepExecutionResults[testStepExecutionResults.length - 1][4] = actual;

            System.out.println(Integer.toString(dataExtractor.stepNo)+"::"+testStatus+":::"+actual);

    }

    public void terminateTestExecution() throws ALMServiceException {
       // updateTestCaseResult();
        terminateConnection();
//        Assert.fail("Test execution terminated!");
//        throw new SkipException("Test execution terminated!");
    }

    public static Object resizeArray(Object oldArray, int newSize) {
        int oldSize = java.lang.reflect.Array.getLength(oldArray);
        Class elementType = oldArray.getClass().getComponentType();
        Object newArray = java.lang.reflect.Array.newInstance(elementType,newSize);
        int preserveLength = Math.min(oldSize,newSize);
        if (preserveLength > 0) {
            System.arraycopy(oldArray,0,newArray,0,preserveLength);
        }
        return newArray;
    }

    public void terminateConnection() {
        almWrapper.close();
    }

    protected String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

}
