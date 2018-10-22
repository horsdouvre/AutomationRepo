package com.albertsons.portal.automation.common;


import com.albertsons.portal.automation.constants.GlobalConstants;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;




/*#################################################################################
  # Class Name: DataExtractor                                                     #
  # Author:                                                                       #
  # Description:                                                                  #
  # Initialize Excel core engine                                                  #
  # Retrive data from Test data sheets                                            #
  #                                                                               #
//#################################################################################
*/
public class DataExtractor {

    //initializeSetup
    public String scriptName = null;
    public String browserName = null;
    public String banner = null;
    public String platformType = null;
    public String testEnvironment = null;
    public String mobileDevice = null;

    //Test Data Variables
    public String execute = null;
    public String almTestCaseName = null;
    public String Desktopexecute = null;
    public String Mobileexecute = null;

    public String firstname = null;
    public String lastname = null;
    public String clubcardorphonenumber = null;
    public String emailaddress = null;
    public String password = null;
    public String securityquestion = null;
    public String securityanswer = null;
    public String zipcode = null;
    public String storeAddress = null;
    public String inquirytype = null;
    public String loyaltycardorphonenumber = null;
    public String contactmethod = null;
    public String phonenumber = null;
    public String city = null;
    public String state = null;
    public String address = null;
    public String topic = null;
    public String comments = null;
    public String productname = null;
    public String productupc = null;
    public String productsize = null;
    public String productcode = null;
    public String productsellbyuseby = null;
    public String dateofpurchase = null;
    public String deliverydate = null;
    public String whendidthishappen = null;
    public String storeinfo = null;
    public String chooseyourstorecity = null;
    public String btemailaddress = null;
    public String btpassword = null;
    public String url = null;
    public String url1 = null;
    public String url2 = null;
    public String url3 = null;
    public String title = null;
    public String streetAddress = null;
    public String suiteorApt = null;
    public String country = null;



    public String oldFirstName = null;
    public String oldLastName = null;
    public String oldHomePhone = null;

    public String newFirstName = null;
    public String newLastName = null;
    public String newStreetAddress = null;
    public String newCity = null;
    public String newState = null;
    public String newZip = null;
    public String newHomePhone = null;
    public String newEmail = null;
    public String receiveCoupons = null;


    public int stepNo = 1;
    public String stepDescription = null;
    public String stepExpected = null;
    public String stepPassActual = null;
    public String stepFailActual = null;

    //DeepLink
    public String deepLinkuid = null;
    public String deepLinkURL = null;
    public String eventId = null;
    public String cmpid = null;
    public String HHID = null;
    public String Theme = null;
    public String emailToken = null;
    public String emailTokenURL = null;

    public String reg_firstname = null;
    public String reg_lastname = null;
    public String reg_clubcardorphonenumber = null;
    public String reg_emailaddress = null;
    public String reg_password = null;
    public String reg_securityquestion = null;
    public String reg_securityanswer = null;


    //Data Extractor Variables
    public String bannerurl = null;
    public String bannerHomePage = null;
    public String safewayURL = null;
    public String albertsonsURL = null;
    public String module = null;

    //ALM Variables
    public String almURL = null;
    public String almUserName = null;
    public String almPassword = null;
    public String almDomain = null;
    public String almProject = null;

    public String almTestSetPath = null;
    public String almTestSetName = null;
    public String almTestSetID = null;

    //Mobile Environment Config
    public String executionNetwork = null;
    public String cloudEnv = null;
    public String privateCloud = null;
    public String publicCloud = null;
    public String cloudUserId = null;
    public String cloudPassword = null;
    public String gridHub = null;

    //Mobile Device Config
    public String deviceID = null;
    public String deviceName = null;
    public String platformVersion = null;
    public String platformName = null;
    public String newCommandTimeout = null;

    // EXCEL
    public static String strTestMachineOS = null;
    public static String strdataExtractorFilePath = null;

    public static String cellTextVal = null;
    public static String cellIntegerVal = null;
    public static int colIndex = 0;

    public ArrayList<String> objectArrayList = new ArrayList<String>();
    public static ArrayList<Row> testscripttotalRowsList = null;

    public ExcelCoreEngine excelCoreEngine = null;
    public int obtainedRow=0;

    // File Paths
    public String TestDataFilePath = null;
    public String AlmConfigFilePath = null;
    public String MobileEnvConfigFilePath = null;
    public String MobileDeviceConfigFilePath = null;
    public String ExtentScreenshotsPath = null;




    public String resultPath=null;

    public void assignTestDetails(String testStepName,String testStepPassDetails,String testStepFailDetails) throws Exception {
        stepDescription = testStepName;
        stepExpected = testStepPassDetails;
        stepPassActual = testStepPassDetails;
        stepFailActual = testStepFailDetails;

    }

    /**
     *
     * @param scriptName
     * @param browserName
     * @param banner
     * @param platformType
     * @param environment
     * @throws Exception
     */


    //##################################################################################################
    //####################                                                       //####################
    //####################                                                      //####################
    //####################                                                      //####################
    //##################################################################################################
    public void initializeSetup(String scriptName,String browserName,String banner,String platformType,String environment,String mobileDevice,String almIntegration) throws Exception{
        this.scriptName = scriptName;
        this.browserName = browserName.toLowerCase();
        this.banner = banner.toLowerCase();
        this.platformType = platformType;
        GlobalConstants.platformType = platformType;
        this.testEnvironment = environment.toLowerCase();
        GlobalConstants.testEnvironment = environment.toLowerCase();
        this.mobileDevice = mobileDevice;
        GlobalConstants.almIntegration=almIntegration.toString();
        setBannerGroup(banner);
        //get the system os into Global Constant
        getHostSysOS();
        //Set Proxy for Safeway VDI Machines to run Rest-Assured Test Scripts
        if(GlobalConstants.hostOSName.equalsIgnoreCase("windows")){
            if((getComputerName().contains("CSC"))){
                GlobalConstants.network="EXTERNAL";
            } else {
                GlobalConstants.network="INTERNAL";
            }
        }
        //Load File Paths based on os
        TestDataFilePath = GlobalConstants.TestDataFilePath + module + "\\" + this.banner.toUpperCase() + ".json";
        AlmConfigFilePath = GlobalConstants.ALMConfigFilePathWindows;
        //System.out.println("TestDataFilePath::"+TestDataFilePath);
        MobileEnvConfigFilePath = GlobalConstants.MobileConfigWindows + "MobileEnvConfig.json";
        MobileDeviceConfigFilePath = GlobalConstants.MobileConfigWindows + "MobileDeviceConfig.json";
        ExtentScreenshotsPath = GlobalConstants.ExtentScreenshotPathWindows + this.scriptName+"_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +".png";
       if (GlobalConstants.hostOSName.equalsIgnoreCase("linux")){
            TestDataFilePath = TestDataFilePath.replaceAll("\\\\","/");
            AlmConfigFilePath = AlmConfigFilePath.replaceAll("\\\\","/");
            MobileEnvConfigFilePath = MobileEnvConfigFilePath.replaceAll("\\\\","/");
            MobileDeviceConfigFilePath = MobileDeviceConfigFilePath.replaceAll("\\\\","/");
            ExtentScreenshotsPath = ExtentScreenshotsPath.replaceAll("\\\\","/");
        }else if(GlobalConstants.hostOSName.equalsIgnoreCase("mac")){
            TestDataFilePath = TestDataFilePath.replaceAll("\\\\","//");
            AlmConfigFilePath = AlmConfigFilePath.replaceAll("\\\\","//");
            MobileEnvConfigFilePath = MobileEnvConfigFilePath.replaceAll("\\\\","//");
            MobileDeviceConfigFilePath = MobileDeviceConfigFilePath.replaceAll("\\\\","//");
            ExtentScreenshotsPath = ExtentScreenshotsPath.replaceAll("\\\\","//");
        }
        if(GlobalConstants.dataExtractorMode.toUpperCase().equals(GlobalConstants.json)) {
            this.initilizejsonfiles();
        } else {
            this.initilizeexcelfiles(module);
        }
    }
    public void setBannerGroup(String banner)throws Exception{

        switch (banner.toUpperCase()){
            case "SAFEWAY":
            case "VONS":
            case "TOMTHUMB":
            case "CARRS":
            case "PAVILIONS":
            case "RANDALLS":
                GlobalConstants.BannerGroup = "SAFEWAY";
                break;
            case "ALBERTSONS":
                GlobalConstants.BannerGroup = "ALBERTSONS";
                break;
            case "SHAWS":
            case "STARMARKET":
            case "JEWELOSCO":
            case "ACMEMARKETS":
                GlobalConstants.BannerGroup = "NAI";
            break;
        }
    }

    public void initilizejsonfiles() throws Exception {
        if(GlobalConstants.almIntegration.equals("ON")) {
            if(GlobalConstants.dataExtractorMode.toUpperCase().equals(GlobalConstants.json)) {
              //  String moduleName = bipassModulename(module);
                this.JsonALMPareseDatafile();
            }
        }
        if (this.platformType.toLowerCase().equals("mobile")){
            JsonMobilePareseDatafile();
        }
        this.JsonTestDataReader();

    }
    public void initilizeexcelfiles(String module) throws Exception {
        GlobalConstants.FilePath =GlobalConstants.TestDataFilePath;
        this.initilizeExcel(GetdataExtractorfileName(module),banner,scriptName);
        if(GlobalConstants.almIntegration.equals("ON")) {
            GlobalConstants.FilePath =GlobalConstants.ALMConfigFilePathWindows;
            //this.JsonALMPareseDatafile();
        }
    }

       /**

     */
    public void JsonTestDataReader() throws Exception {
        String filePath = null;
        try {
            JsonUtils jsonUtils=new JsonUtils();
            filePath = this.TestDataFilePath;
            // Common
            almTestCaseName =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"almTestCaseName");
            Desktopexecute =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"Desktop_Execute");
            Mobileexecute =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"Mobile_Execute");
            url =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"URL");
            firstname =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"FirstName");
            lastname =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"LastName");

            //DeepLink
            deepLinkURL =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"DeepLinkURL");
            eventId =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"EventId");
            emailToken =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"EmailToken");
            cmpid =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"Cmpid");
            HHID =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"HHID");
            Theme =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"Theme");
            url1 =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"URLOne");
            url2 =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"URLTwo");
            url3 =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"URLThree");

            // #####   RSS   #####
            clubcardorphonenumber =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"ClubCardorPhonenumber");
            emailaddress =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"EmailAddress");
            password =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"Password");
            securityquestion =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"SecurityQuestion");
            securityanswer =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"SecurityAnswer");
            zipcode =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"Zipcode");
            storeAddress =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"StoreAddress");

            // #####   MyAccount   #####
            phonenumber =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"PhoneNumber");
            title =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"Title");
            streetAddress =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"StreetAddress");
            suiteorApt =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"SuiteApt");
            city =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"City");
            state =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"State");
            country =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"Country");


            // #####   ECP   #####
            inquirytype =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"InquiryType");
            loyaltycardorphonenumber =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"LoyaltyCardOrPhoneNumber");
            contactmethod =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"ContactMethod");
            storeAddress =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"StoreAddress");

            address =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"Address");
            topic =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"Topic");
            comments =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"Comments");
            productname =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"ProductName");
            productupc =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"ProductUPC");
            productcode =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"ProductCode");
            productsellbyuseby =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"ProductSellByUseBy");
            dateofpurchase =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"DateOfPurchase");
            whendidthishappen =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"WhenDidThisHappen");
            storeinfo =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"StoreInformation");
            deliverydate = jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"DeliveryDate");
            // ## BOXTOP


            btemailaddress =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"BTEmailAddress");
            btpassword =  jsonUtils.simplpeJsonObjectParer(filePath,scriptName,"BTPassword");

            if (banner.toLowerCase().equals("carrs")) {
                banner = "carrsqc";
            }

            // Load default test data
            loaddefaultRegistrationTestData();
            //Load default Banner specified URLs

            getBannerHomePage();
            getCity();
            getEmailserver();
            //Load default DeepLink data
//            if (module.equals(GlobalConstants.PortalModule.DeepLink.toString())) {
//                setDeepLinkdataExtractor(GlobalConstants.emailAccount);
//            }

            if (this.platformType.equals("Desktop")) {
                execute = Desktopexecute;
            } else {
                execute = Mobileexecute;
            }



        } catch (FileNotFoundException ex) {

            ex.printStackTrace();

        } catch (IOException ex) {

            ex.printStackTrace();

        } catch (ParseException ex) {

            ex.printStackTrace();

        } catch (NullPointerException ex) {

            ex.printStackTrace();

        }

    }
    public void ExcelALMPareseDatafile(String module) throws Exception {
        ArrayList<Row> testscripttotalRowsList = excelCoreEngine.extractTotalData();

        Iterator rows = testscripttotalRowsList.iterator();
        int breakVal=0;

        while (rows.hasNext()) {
            int rowIncrementer=0;
            breakVal++;
            XSSFRow currentRow = (XSSFRow) rows.next();
            Iterator cells = currentRow.cellIterator();
            while (cells.hasNext()) {
                // rowIncrementer=0;
                XSSFCell cell = (XSSFCell) cells.next();

                if(rowIncrementer<4) {
                    rowIncrementer++;
                    int type = cell.getCellType();
                    if (type == XSSFCell.CELL_TYPE_STRING) {
                        cellTextVal = cell.getRichStringCellValue().toString();

                    } else if (type == XSSFCell.CELL_TYPE_NUMERIC) {
                        cellIntegerVal = String.valueOf((int) cell.getNumericCellValue());

                    }
                    if(cellTextVal.equals(scriptName)) {
                        obtainedRow = breakVal;
                        break;
                    }

                }

            }
            if(obtainedRow>0)
                break;


            almURL = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("almURL")).toString();
            almUserName = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("almUserName")).toString();
            almPassword = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("almPassword")).toString();
            almDomain = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("almDomain")).toString();
            almProject = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("almProject")).toString();

            almTestSetPath = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("almTestSetPath")).toString();
            almTestSetName = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("almTestSetName")).toString();
            almTestSetID = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("almTestSetID")).toString();
        }

    }
    public void JsonALMPareseDatafile() throws Exception {
        try {
            // read the json file
            String filePath = this.AlmConfigFilePath;
           // System.out.println("AlmConfigFilePath::" + filePath);
            FileReader reader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            // handle a structure into the json object

            JSONObject structure = (JSONObject) jsonObject.get(banner.toUpperCase());

            almURL = (String) structure.get("almURL");
            almUserName = (String) structure.get("almUserName");
            almPassword = (String) structure.get("almPassword");
            almDomain = (String) structure.get("almDomain");
            almProject = (String) structure.get("almProject");

            almTestSetPath = (String) structure.get("almTestSetPath");
            almTestSetName = (String) structure.get("almTestSetName");
            almTestSetID = (String) structure.get("almTestSetID");


        } catch (FileNotFoundException ex) {

            ex.printStackTrace();

        } catch (IOException ex) {

            ex.printStackTrace();

        } catch (ParseException ex) {

            ex.printStackTrace();

        } catch (NullPointerException ex) {

            ex.printStackTrace();

        }

    }


    public void JsonMobilePareseDatafile() throws Exception {
        try {
            // read the json file
            String filePath = this.MobileDeviceConfigFilePath;
            FileReader reader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONObject structure = (JSONObject) jsonObject.get(this.mobileDevice);


            deviceID = (String) structure.get("deviceID");
            deviceName = (String) structure.get("deviceName");
            platformVersion = (String) structure.get("platformVersion");
            platformName = (String) structure.get("platformName");
            newCommandTimeout = "9999";

            // read the json file
            filePath = this.MobileEnvConfigFilePath;
            reader = new FileReader(filePath);
            jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(reader);
            structure = (JSONObject) jsonObject.get("MobileExecutionConfig");

            executionNetwork = (String) structure.get("ExecutionNetwork");
            cloudEnv = (String) structure.get("CloudEnv");
            privateCloud = (String) structure.get("PrivateCloud");
            publicCloud = (String) structure.get("PublicCloud");
            cloudUserId = (String) structure.get("CloudUserId");
            cloudPassword = (String) structure.get("CloudPassword");

            if(cloudEnv.toLowerCase().equals("public")){
                gridHub = publicCloud;
            } else {
                gridHub = privateCloud;
            }

        } catch (FileNotFoundException ex) {

            ex.printStackTrace();

        } catch (IOException ex) {

            ex.printStackTrace();

        } catch (ParseException ex) {

            ex.printStackTrace();

        } catch (NullPointerException ex) {

            ex.printStackTrace();

        }

    }

    public void initilizeExcel(String FileName,String TabName,String FieldName) throws Exception {
        GetTestMachineOS();
       // String dataExtractorFileName = GetdataExtractorfileName(module);
//        switch (strTestMachineOS.toUpperCase()){
//            case "MAC":
//                strdataExtractorFilePath = GlobalConstants.dataExtractorFilePathWindows + FileName;
//                GlobalConstants.dataExtractorFileName = FileName;
//                break;
//            case "WINDOWS":
//                strdataExtractorFilePath = GlobalConstants.dataExtractorFilePathWindows + FileName;
//                GlobalConstants.dataExtractorFileName = FileName;
//                break;
//        }
        strdataExtractorFilePath = GlobalConstants.FilePath + FileName;
        GlobalConstants.dataExtractorFileName = FileName;
        excelCoreEngine = new ExcelCoreEngine(strdataExtractorFilePath,TabName.toUpperCase(),FieldName);
        //System.out.println("strdataExtractorFilePath:"+strdataExtractorFilePath);

        if (!banner.toLowerCase().equals("carrs")) {
            banner = banner.toLowerCase();
        } else {
            banner = "carrsqc";
        }
        try {
            this.ExcelParsedataExtractorfile();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void ExcelParsedataExtractorfile() throws Exception{
        ArrayList<Row> testscripttotalRowsList = excelCoreEngine.extractTotalData();

        Iterator rows = testscripttotalRowsList.iterator();
        int breakVal=0;

        while (rows.hasNext()) {
            int rowIncrementer=0;
            breakVal++;

            XSSFRow currentRow = (XSSFRow) rows.next();
            Iterator cells = currentRow.cellIterator();
            while (cells.hasNext()) {
                // rowIncrementer=0;
                XSSFCell cell = (XSSFCell) cells.next();

                if(rowIncrementer<4) {
                    rowIncrementer++;
                    int type = cell.getCellType();
                    if (type == XSSFCell.CELL_TYPE_STRING) {
                        cellTextVal = cell.getRichStringCellValue().toString();

                    } else if (type == XSSFCell.CELL_TYPE_NUMERIC) {
                        cellIntegerVal = String.valueOf((int) cell.getNumericCellValue());

                    }
                    if(cellTextVal.equals(scriptName)) {
                        obtainedRow = breakVal;
                        break;
                    }

                }

            }
            if(obtainedRow>0)
                break;
        }

        //System.out.println(GlobalConstants.dataExtractorFileName.toLowerCase());

        switch (GlobalConstants.dataExtractorFileName.toLowerCase()) {
            case "contactus_uivalidation_testdata.xlsx":
                almTestCaseName = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ALMTestCaseName")).toString();
                //  execute = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ExecuteScript")).toString();
                Desktopexecute = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Desktop")).toString();
                Mobileexecute = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Mobile")).toString();
                bannerurl= testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("URL")).toString();
                // QA1Env = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("QA1Environment")).toString();
                //QA2Env = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("QA2Environment")).toString();
                banner = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Banner")).toString();
                inquirytype = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("InquiryType")).toString();
                loyaltycardorphonenumber = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("LoyaltyCardOrPhoneNumber")).toString();
                firstname = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("FirstName")).toString();
                lastname = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("LastName")).toString();
                contactmethod = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ContactMethod")).toString();
                emailaddress = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("EmailAddress")).toString();
                phonenumber = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("PhoneNumber")).toString();
                zipcode = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ZIPCode")).toString();
                city = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("City")).toString();
                state = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("State")).toString();
                address = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Address")).toString();
                topic = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Topic")).toString();
                comments = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Comments")).toString();
                productname = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ProductName")).toString();
                productupc = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ProductUPC")).toString();
                productsize = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ProductSize")).toString();
                productcode = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ProductCode")).toString();
                productsellbyuseby = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ProductSellByUseBy")).toString();
                dateofpurchase = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("DateOfPurchase")).toString();
                whendidthishappen = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("WhenDidThisHappen")).toString();
                storeinfo = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("StoreInformation")).toString();
                //mobileurl = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("MobileURL")).toString();
                break;

            case "contactus_endtoend_testdata.xlsx":

                almTestCaseName = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ALMTestCaseName")).toString();
                //execute = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ExecuteScript")).toString();
                Desktopexecute = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Desktop")).toString();
                Mobileexecute = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Mobile")).toString();
                bannerurl= testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("BannerURL")).toString();
                //QA1Env = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("QA1Environment")).toString();
                //QA2Env = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("QA2Environment")).toString();
                banner = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Banner")).toString();
                inquirytype = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("InquiryType")).toString();
                loyaltycardorphonenumber = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("LoyaltyCardOrPhoneNumber")).toString();
                firstname = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("FirstName")).toString();
                lastname = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("LastName")).toString();
                contactmethod = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ContactMethod")).toString();
                emailaddress = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("EmailAddress")).toString();
                phonenumber = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("PhoneNumber")).toString();
                zipcode = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ZIPCode")).toString();
                city = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("City")).toString();
                state = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("State")).toString();
                address = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Address")).toString();
                topic = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Topic")).toString();
                comments = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Comments")).toString();
                productname = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ProductName")).toString();
                productupc = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ProductUPC")).toString();
                productsize = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ProductSize")).toString();
                productcode = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ProductCode")).toString();
                productsellbyuseby = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ProductSellByUseBy")).toString();
                dateofpurchase = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("DateOfPurchase")).toString();
                whendidthishappen = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("WhenDidThisHappen")).toString();
                storeinfo = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("StoreInformation")).toString();
               // mobileurl = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("MobileURL")).toString();
                break;

            case "boxtop_testdata.xlsx":
                almTestCaseName = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ALMTestCaseName")).toString();
                Desktopexecute = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Desktop")).toString();
                Mobileexecute = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Mobile")).toString();
                bannerurl = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("URL")).toString();
                // banner = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Banner")).toString();
                firstname = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("FirstName")).toString();
                lastname = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("LastName")).toString();
                clubcardorphonenumber = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ClubCardorPhonenumber")).toString();
                emailaddress = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("EmailAddress")).toString();
                password = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Password")).toString();
                securityquestion = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("SecurityQuestion")).toString();
                securityanswer = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("SecurityAnswer")).toString();
                zipcode = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Zipcode")).toString();
                storeAddress = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("SelectStore")).toString();
                btemailaddress = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("BTEmailAddress")).toString();
                btpassword = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("BTPassword")).toString();

                break;


            case "rss_testdata.xlsx":
                almTestCaseName = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ALMTestCaseName")).toString();
                //              execute = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ExecuteScript")).toString();
                Desktopexecute = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Desktop")).toString();
                Mobileexecute = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Mobile")).toString();
                // globalConstants.bannerurl = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("URL")).toString();
                bannerurl = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("URL")).toString();
                //  banner = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Banner")).toString();
                firstname = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("FirstName")).toString();
                lastname = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("LastName")).toString();
                clubcardorphonenumber = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ClubCardorPhonenumber")).toString();
                emailaddress = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("EmailAddress")).toString();
                password = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Password")).toString();
                securityquestion = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("SecurityQuestion")).toString();
                securityanswer = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("SecurityAnswer")).toString();
                zipcode = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Zipcode")).toString();
                chooseyourstorecity = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ChooseyourStoreCity")).toString();
                storeAddress = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("SelectStore")).toString();
                break;

            case "rss_smoke_testdata.xlsx":
                almTestCaseName = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ALMTestCaseName")).toString();
                Desktopexecute = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Desktop")).toString();
                Mobileexecute = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Mobile")).toString();
                bannerurl = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("URL")).toString();
                firstname = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("FirstName")).toString();
                lastname = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("LastName")).toString();
                clubcardorphonenumber = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ClubCardorPhonenumber")).toString();
                emailaddress = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("EmailAddress")).toString();
                password = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Password")).toString();
                securityquestion = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("SecurityQuestion")).toString();
                securityanswer = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("SecurityAnswer")).toString();
                zipcode = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Zipcode")).toString();
                storeAddress = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("SelectStore")).toString();
                break;

            case "deeplink_testdata.xlsx":
                almTestCaseName = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ALMTestCaseName")).toString();
                Desktopexecute = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Desktop")).toString();
                Mobileexecute = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Mobile")).toString();
                bannerurl = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("URL")).toString();
                url1 = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("URLOne")).toString();
                url2 = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("URLTwo")).toString();
                url3 = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("URLThree")).toString();

                break;

            case "ecp_smoke_testdata.xlsx":
                almTestCaseName = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ALMTestCaseName")).toString();
                //  execute = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ExecuteScript")).toString();
                Desktopexecute = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Desktop")).toString();
                Mobileexecute = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Mobile")).toString();
                //bannerurl= testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("URL")).toString();
                // QA1Env = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("QA1Environment")).toString();
                //QA2Env = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("QA2Environment")).toString();
                //banner = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Banner")).toString();
                inquirytype = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("InquiryType")).toString();
                loyaltycardorphonenumber = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("LoyaltyCardOrPhoneNumber")).toString();
                firstname = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("FirstName")).toString();
                lastname = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("LastName")).toString();
                contactmethod = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ContactMethod")).toString();
                emailaddress = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("EmailAddress")).toString();
                phonenumber = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("PhoneNumber")).toString();
                zipcode = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ZIPCode")).toString();
                city = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("City")).toString();
                state = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("State")).toString();
                address = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Address")).toString();
                topic = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Topic")).toString();
                comments = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("Comments")).toString();
                productname = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ProductName")).toString();
                productupc = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ProductUPC")).toString();
                productsize = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ProductSize")).toString();
                productcode = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ProductCode")).toString();
                productsellbyuseby = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("ProductSellByUseBy")).toString();
                dateofpurchase = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("DateOfPurchase")).toString();
                whendidthishappen = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("WhenDidThisHappen")).toString();
                storeAddress = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("StoreInformation")).toString();
                // mobileurl = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("MobileURL")).toString();

                break;

            case "almconfig.xlsx":

                almURL = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("almURL")).toString();
                almUserName = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("almUserName")).toString();
                almPassword = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("almPassword")).toString();
                almDomain = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("almDomain")).toString();
                almProject = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("almProject")).toString();

                almTestSetPath = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("almTestSetPath")).toString();
                almTestSetName = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("almTestSetName")).toString();
                almTestSetID = testscripttotalRowsList.get(obtainedRow-1).getCell(getcolIndex("almTestSetID")).toString();
                break;


        }



        if (platformType.equals("Desktop")) {
            execute = Desktopexecute;
        } else {
            execute = Mobileexecute;
        }

       // testEnvironment = GlobalConstants.testEnvironment;
        switch (testEnvironment.toUpperCase()) {
            case "QA1":
                bannerurl = "http://ngcp-qa1."+banner+".com/";
                safewayURL =  "https://ngcp-qa1.safeway.com/";
                GlobalConstants.BannerUrl = "http://ngcp-qa1."+banner+".com/";

                break;

            case "QA2":
                bannerurl = "http://ngcp-qa2."+banner+".com/";
                safewayURL =  "https://ngcp-qa2.safeway.com/";
                GlobalConstants.BannerUrl = "http://ngcp-qa1."+banner+".com/";
                break;
            case "QI":
                bannerurl = "http://ngcp-qi."+banner+".com/";
                safewayURL =  "https://ngcp-qi.safeway.com/";
                GlobalConstants.BannerUrl = "http://ngcp-qi."+banner+".com/";
                break;
            case "PRF":
                bannerurl = "http://ngcp-prf."+banner+".com/";
                safewayURL =  "https://ngcp-prf.safeway.com/";
                GlobalConstants.BannerUrl = "http://ngcp-prf."+banner+".com/";
                break;
        }


    }
    public static void GetTestMachineOS(){

        if (System.getProperty("os.name").indexOf("Mac")>=0){

            strTestMachineOS = "Mac";

        }else if (System.getProperty("os.name").indexOf("Windows")>=0) {

            strTestMachineOS = "Windows";

        }

    }

    public static String GetdataExtractorfileName(String module) {
        String filename = null;
        if(module.toLowerCase().contains("ContactUS_UI".toLowerCase())){
            filename = "ContactUs_UIValidation_TestData.xlsx";
        } else if(module.toLowerCase().contains("ContactUS_E2E".toLowerCase())){
            filename = "ContactUs_EndtoEnd_TestData.xlsx";
        } else if(module.toLowerCase().contains("BoxTop".toLowerCase())){
            filename = "BoxTop_TestData.xlsx";
        } else if(module.toLowerCase().contains("Registration_UI".toLowerCase())){
            filename = "RSS_TestData.xlsx";
        } else if(module.toLowerCase().contains("Registration_E2E".toLowerCase())){
            filename = "RSS_TestData.xlsx";
        } else if(module.toLowerCase().contains("Registration".toLowerCase())){
            filename = "RSS_TestData.xlsx";
        } else if(module.toLowerCase().contains("MyAccount".toLowerCase())){
            filename = "RSS_TestData.xlsx";
        } else if(module.toLowerCase().contains("SignIn".toLowerCase())){
            filename = "RSS_TestData.xlsx";
        } else if(module.toLowerCase().contains("Misc".toLowerCase())){
            filename = "RSS_TestData.xlsx";
        } else if(module.toLowerCase().contains("HeaderFooter_ECP".toLowerCase())){
            filename = "HeaderFooter_dataExtractor.xlsx";
        } else if(module.toLowerCase().contains("HeaderFooter_RSS".toLowerCase())){
            filename = "RSS_TestData.xlsx";
        } else if(module.toLowerCase().contains("DeepLink".toLowerCase())){
            filename = "DeepLink_TestData.xlsx";
        } else if(module.toLowerCase().contains("Smoke_RSS".toLowerCase())){
            filename = "RSS_TestData.xlsx";
        } else if(module.toLowerCase().contains("Smoke_ECP".toLowerCase())){
            filename = "RSS_TestData.xlsx";
        } else if(module.toLowerCase().contains("ALMConfig".toLowerCase())){
            filename = "RSS_TestData.xlsx";
        }

        return filename;
    }

    public int getcolIndex(String colName)throws Exception{

        ArrayList<Row> testscriptColumnList = excelCoreEngine.extractTestScriptColumns();

        Iterator coulmns = testscriptColumnList.iterator();
        int breakVal=0;
        while (coulmns.hasNext()) {


            XSSFRow currentRow = (XSSFRow) coulmns.next();
            Iterator cells = currentRow.cellIterator();

            while (cells.hasNext()) {
                // rowIncrementer=0;
                breakVal++;
                XSSFCell cell = (XSSFCell) cells.next();




                cellTextVal = cell.getRichStringCellValue().toString();
                // objectArrayList.add(cellTextVal);

                if(cellTextVal.equals(colName)) {
                    colIndex = breakVal;
                    break;
                }

            }
            if(colIndex>0)
                break;
        }
        colIndex = colIndex - 1;
        return colIndex;
    }
    public void getdefaultZipcodeandStoreAddress() throws Exception {
        switch (banner.toUpperCase()) {
            case "SAFEWAY":
                if (zipcode == null) {
                    zipcode="95001";
                }
                if (storeAddress == null) {
                    storeAddress = "Safeway - 2720 41st St. Soquel,CA 95073";
                }

                break;
            case "ALBERTSONS":
                if (zipcode == null) {
                    zipcode="70810";
                }
                if (storeAddress == null) {
                    storeAddress = "Albertsons - 9960 Bluebonnet Blvd Baton Rouge,LA 70810";
                }

                break;
            case "CARRSQC":
                if (zipcode == null) {
                    zipcode="99517";
                }
                if (storeAddress == null) {
                    storeAddress = "Carrsqc - 1650 W. Northern Lights Blvd Anchorage,AK 99517";
                }

                break;
            case "RANDALLS":
                if (zipcode == null) {
                    zipcode="77551";
                }
                if (storeAddress == null) {
                    storeAddress = "Randalls - 2931 Central City Blvd Galveston,TX 77551";
                }
                break;
            case "PAVILIONS":
                if (zipcode == null) {
                    zipcode="90713";
                }
                if (storeAddress == null) {
                    storeAddress = "Pavilions - 5949 E. Spring St Long Beach,CA 90808";
                }

                break;
            case "TOMTHUMB":
                if (zipcode == null) {
                    zipcode="75067";
                }
                if (storeAddress == null) {
                    storeAddress = "Tomthumb - 1075 West F.m. 3040 Lewisville,TX 75067";
                }

                break;
            case "VONS":
                if (zipcode == null) {
                    zipcode="92627";
                }
                if (storeAddress == null) {
                    storeAddress = "Vons - 185 E. 17th St. Costa Mesa,CA 92627";
                }
                break;
            case "SHAWS":
                if (zipcode == null) {
                    zipcode="02302";
                }
                if (storeAddress == null) {
                    storeAddress = "Shaws - 715 Crescent St Brockton,MA 02302";
                    storeinfo = "Shaws - 715 Crescent St Brockton,MA 02302";
                }
                break;
            case "STARMARKET":
                if (zipcode == null) {
                    zipcode="02601";
                }
                if (storeAddress == null) {
                    storeAddress = "Starmarket - 625 W Main St Hyannis,MA 02601";
                }
                break;
            case "JEWELOSCO":
                if (zipcode == null) {
                    zipcode="60655";
                }
                if (storeAddress == null) {
                    storeAddress = "Jewelosco - 3128 W 103rd St Chicago,IL 60655";
                }
                break;
            case "ACMEMARKETS":
                if (zipcode == null) {
                    zipcode="07310";
                }
                if (storeAddress == null) {
                    storeAddress = "Acmemarkets - 125 18th St Jersey City,NJ 07310";
                }
                break;
        }
    }
    public void registerUserdata() throws Exception {

        long tLen = (long) Math.pow(10, 8) * 9;

        long number = (long) (Math.random() * tLen) + (long) Math.pow(10, 8 - 1) * 1;

        String randomValue = number + "";
        

        firstname = "AutoJohn";
        lastname = "AutoSmith";
        if (clubcardorphonenumber == null){
            if(testEnvironment.toLowerCase().equals("qa2")){
                clubcardorphonenumber = "41021940505";
            } else if (testEnvironment.toLowerCase().equals("qa1")){
                clubcardorphonenumber = "48635026156";
            } else {
                clubcardorphonenumber="2"+randomValue;
            }
        }
        emailaddress = "testauto_" +new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "@example.com";
        password = "@lt12345";
        securityquestion = "What is the name of your first pet?";
        securityanswer = "Doggie";
        getdefaultZipcodeandStoreAddress();
    }

    public void registerNewUserdata() throws Exception {
        reg_firstname = "AutoJohn";
        reg_lastname = "AutoSmith";
        if (clubcardorphonenumber == null){
            if(testEnvironment.toLowerCase().equals("qa2")){
                clubcardorphonenumber = "41021940505";
            } else if (testEnvironment.toLowerCase().equals("qa1")){
                clubcardorphonenumber = "48635026156";
            }
        }
        reg_emailaddress = "testauto_" +new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "@example.com";
        reg_password = "@lt12345";
        reg_securityquestion = "What is the name of your first pet?";
        reg_securityanswer = "Doggie";
        getdefaultZipcodeandStoreAddress();
    }
    public final static String createRandomNumber(long len) {
        if (len > 18)
            throw new IllegalStateException("To many digits");
        long tLen = (long) Math.pow(10, len - 1) * 9;

        long number = (long) (Math.random() * tLen) + (long) Math.pow(10, len - 1) * 1;

        String randomValue = number + "";
        if (randomValue.length() != len) {
            throw new IllegalStateException("The random number '" + randomValue + "' is not '" + len + "' digits");
        }
        return randomValue;
    }

    public void loaddefaultRegistrationTestData() throws Exception {
        long tLen = (long) Math.pow(10, 8) * 9;
        long number = (long) (Math.random() * tLen) + (long) Math.pow(10, 8 - 1) * 1;
        String randomValue = number + "";
        if (firstname == null) {
            firstname = "AutoJohn";
        }
        if (lastname == null) {
            lastname = "AutoSmith";
        }
        if (clubcardorphonenumber == null){
            if(testEnvironment.toLowerCase().equals("qa2")){
                clubcardorphonenumber = "41021940505";
            } else if (testEnvironment.toLowerCase().equals("qa1")){
                clubcardorphonenumber = "48635026156";
            } else {
                clubcardorphonenumber="2"+randomValue;
            }
        } else if (clubcardorphonenumber.equals("-")){
            clubcardorphonenumber="2"+randomValue;
        }
        if (emailaddress == null || emailaddress.equals("-")){
            emailaddress = "test_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +  "@example.com";
        }
        if (password == null){
            password = "@lt12345";
        }
        if (securityquestion == null) {
            securityquestion = "What is the name of your first pet?";
        }
        if (securityanswer == null) {
            securityanswer = "Doggie";
        }
        getdefaultZipcodeandStoreAddress();
    }

    public void getBannerHomePage() throws Exception {
        switch (this.testEnvironment.toUpperCase()) {
            case "QA1":
                safewayURL = "https://ngcp-qa1.safeway.com/";
                albertsonsURL = "https://ngcp-qa1.albertsons.com/";
                GlobalConstants.BannerUrl = "https://ngcp-qa1."+banner+".com/";
                switch (banner.toUpperCase()) {
                    case "ALBERTSONS":
                        bannerHomePage = "https://albertsons.wpuat.mywebgrocer.com/#1";
                        bannerurl = "https://ngcp-qa1.albertsons.com/";
                        break;
                    case "ACMEMARKETS":
                        bannerHomePage = "http://acmemarkets.wpuat.mywebgrocer.com/";
                        bannerurl = "http://ngcp-qa1.acmemarkets.com/ShopStores/";
                        break;
                    case "JEWELOSCO":
                        bannerHomePage = "http://jewelosco.wpuat.mywebgrocer.com/";
                        bannerurl = "http://ngcp-qa1.jewelosco.com/ShopStores";
                        break;
                    case "SHAWS":
                        bannerHomePage = "http://shaws.wpuat.mywebgrocer.com/#1";
                        bannerurl = "https://ngcp-qa1.shaws.com/";
                        break;
                    case "STARMARKET":
                        bannerHomePage = "http://starmarket.wpuat.mywebgrocer.com/#1";
                        bannerurl = "https://ngcp-qa1.starmarket.com/";
                        break;
                    case "SAFEWAY":
                        bannerHomePage = "http://ngcp-qa1.safeway.com/";
                        bannerurl = "https://ngcp-qa1.safeway.com/";
                        break;
                    case "VONS":
                        bannerHomePage = "http://ngcp-qa1.vons.com/";
                        bannerurl = "https://ngcp-qa1.vons.com/";
                        break;
                    case "CARRSQC":
                        bannerHomePage = "http://ngcp-qa1.carrsqc.com/";
                        bannerurl = "https://ngcp-qa1.carrsqc.com/";
                        break;
                    case "RANDALLS":
                        bannerHomePage = "http://ngcp-qa1.randalls.com/";
                        bannerurl = "https://ngcp-qa1.randalls.com/";
                        break;
                    case "PAVILIONS":
                        bannerHomePage = "http://ngcp-qa1.pavilions.com/";
                        bannerurl = "https://ngcp-qa1.pavilions.com/";
                        break;
                    case "TOMTHUMB":
                        bannerHomePage = "http://ngcp-qa1.tomthumb.com/";
                        bannerurl = "https://ngcp-qa1.tomthumb.com/";
                        break;
                }

                break;
            case "QA2":
                safewayURL = "https://ngcp-qa2.safeway.com/";
                albertsonsURL = "https://ngcp-qa2.albertsons.com/";
                GlobalConstants.BannerUrl = "https://ngcp-qa2."+banner+".com/";
                switch (banner.toUpperCase()) {
                    case "ALBERTSONS":
                        bannerHomePage = "http://albertsons.wpuat.mywebgrocer.com/";
                        bannerurl = "https://ngcp-qa2.albertsons.com/";
                        break;
                    case "ACMEMARKETS":
                        bannerHomePage = "https://ngcp-qa2.acmemarkets.com/";
                        bannerurl = "https://ngcp-qa2.acmemarkets.com/";
                        break;
                    case "JEWELOSCO":
                        bannerHomePage = "https://ngcp-qa2.jewelosco.com/";
                        bannerurl = "https://ngcp-qa2.jewelosco.com/";
                        break;
                    case "SHAWS":
                        bannerHomePage = "https://ngcp-qa2.shaws.com/";
                        bannerurl = "https://ngcp-qa2.shaws.com/";
                        break;
                    case "STARMARKET":
                        bannerHomePage = "https://ngcp-qa2.starmarket.com/";
                        bannerurl = "https://ngcp-qa2.starmarket.com/";
                        break;
                    case "SAFEWAY":
                        bannerHomePage = "http://ngcp-qa2.safeway.com/";
                        bannerurl = "https://ngcp-qa2.safeway.com/";
                        break;
                    case "VONS":
                        bannerHomePage = "http://ngcp-qa2.vons.com/";
                        bannerurl = "https://ngcp-qa2.vons.com/";
                        break;
                    case "CARRSQC":
                        bannerHomePage = "http://ngcp-qa2.carrsqc.com/";
                        bannerurl = "https://ngcp-qa2.carrsqc.com/";
                        break;
                    case "RANDALLS":
                        bannerHomePage = "http://ngcp-qa2.randalls.com/";
                        bannerurl = "https://ngcp-qa2.randalls.com/";
                        break;
                    case "PAVILIONS":
                        bannerHomePage = "http://ngcp-qa2.pavilions.com/";
                        bannerurl = "https://ngcp-qa2.pavilions.com/";
                        break;
                    case "TOMTHUMB":
                        bannerHomePage = "http://ngcp-qa2.tomthumb.com/";
                        bannerurl = "https://ngcp-qa2.tomthumb.com/";
                        break;
                }

                break;
            case "QI":
                safewayURL = "https://ngcp-qi.safeway.com/";
                albertsonsURL = "https://ngcp-qi.albertsons.com/";
                GlobalConstants.BannerUrl = "https://ngcp-qi."+banner+".com/";
                switch (banner.toUpperCase()) {
                    case "ALBERTSONS":
                        bannerHomePage = "http://ngcp-qi.albertsons.com/ShopStores/tools/store-locator.page";
                        bannerurl = "https://ngcp-qi.albertsons.com/";
                        break;
                    case "ACMEMARKETS":
                        bannerHomePage = "http://ngcp-qi.acmemarkets.com/ShopStores/tools/store-locator.page";
                        bannerurl = "https://ngcp-qi.acmemarkets.com/";
                        break;
                    case "JEWELOSCO":
                        bannerHomePage = "http://ngcp-qi.jewelosco.com/ShopStores/tools/store-locator.page";
                        bannerurl = "https://ngcp-qi.jewelosco.com/";
                        break;
                    case "SHAWS":
                        bannerHomePage = "http://ngcp-qi.shaws.com/ShopStores/tools/store-locator.page";
                        bannerurl = "https://ngcp-qi.shaws.com/";
                        break;
                    case "STARMARKET":
                        bannerHomePage = "http://ngcp-qi.starmarket.com/ShopStores/tools/store-locator.page";
                        bannerurl = "https://ngcp-qi.starmarket.com/";
                        break;
                    case "SAFEWAY":
                        bannerHomePage = "http://ngcp-qi.safeway.com/";
                        bannerurl = "https://ngcp-qi.safeway.com/";
                        break;
                    case "VONS":
                        bannerHomePage = "http://ngcp-qi.vons.com/";
                        bannerurl = "https://ngcp-qi.vons.com/";
                        break;
                    case "CARRSQC":
                        bannerHomePage = "http://ngcp-qi.carrsqc.com/";
                        bannerurl = "https://ngcp-qi.carrsqc.com/";
                        break;
                    case "RANDALLS":
                        bannerHomePage = "http://ngcp-qi.randalls.com/";
                        bannerurl = "https://ngcp-qi.randalls.com/";
                        break;
                    case "PAVILIONS":
                        bannerHomePage = "http://ngcp-qi.pavilions.com/";
                        bannerurl = "https://ngcp-qi.pavilions.com/";
                    case "TOMTHUMB":
                        bannerHomePage = "http://ngcp-qi.tomthumb.com/";
                        bannerurl = "https://ngcp-qi.tomthumb.com/";
                        break;
                }
                break;
            case "PROD":
                safewayURL = "http://www.safeway.com/";
                albertsonsURL = "http://www.albertsons.com/";
                switch (banner.toUpperCase()) {
                    case "ALBERTSONS":
                        bannerHomePage = "http://www1.albertsons.com/";
                        GlobalConstants.BannerUrl = "http://www1.albertsons.com/";
                        bannerurl = "https://www.albertsons.com/";
                        break;
                    case "ACMEMARKETS":
                        bannerHomePage = "http://www1.acmemarkets.com/";
                        GlobalConstants.BannerUrl = "http://www1.acmemarkets.com/";
                        bannerurl = "https://www.acmemarkets.com/";
                        break;
                    case "JEWELOSCO":
                        bannerHomePage = "http://jewelosco.wpuat.mywebgrocer.com/";
                        GlobalConstants.BannerUrl = "http://www1.jewelosco.com/";
                        bannerurl = "https://www.jewelosco.com/";
                        break;
                    case "SHAWS":
                        bannerHomePage = "http://ngcp-qi.shaws.com/ShopStores/tools/store-locator.page";
                        GlobalConstants.BannerUrl = "http://www1.shaws.com/";
                        bannerurl = "https://www.shaws.com/";
                        break;
                    case "STARMARKET":
                        bannerHomePage = "http://ngcp-qi.starmarket.com/ShopStores/tools/store-locator.page";
                        GlobalConstants.BannerUrl = "http://www1.starmarket.com/";
                        bannerurl = "https://www.starmarket.com/";
                        break;
                    case "SAFEWAY":
                        bannerHomePage = "http://www.safeway.com/";
                        GlobalConstants.BannerUrl = "http://www.safeway.com/";
                        bannerurl = "https://www.safeway.com/";
                        break;
                    case "VONS":
                        bannerHomePage = "http://www.vons.com/";
                        GlobalConstants.BannerUrl = "http://www.vons.com/";
                        bannerurl = "https://www.vons.com/";
                        break;
                    case "CARRSQC":
                        bannerHomePage = "http://www.carrsqc.com/";
                        GlobalConstants.BannerUrl = "http://www.carrsqc.com/";
                        bannerurl = "https://www.carrsqc.com/";
                        break;
                    case "RANDALLS":
                        bannerHomePage = "http://www.randalls.com/";
                        GlobalConstants.BannerUrl = "http://www.randalls.com/";
                        bannerurl = "https://www.randalls.com/";
                        break;
                    case "PAVILIONS":
                        bannerHomePage = "http://www.pavilions.com/";
                        GlobalConstants.BannerUrl = "http://www.pavilions.com/";
                        bannerurl = "https://www.pavilions.com/";
                        break;
                    case "TOMTHUMB":
                        bannerHomePage = "http://www.tomthumb.com/";
                        GlobalConstants.BannerUrl = "http://www.tomthumb.com/";
                        bannerurl = "https://www.tomthumb.com/";
                        break;
                }

                break;
            case "PRF":
                safewayURL = "https://ngcp-prf.safeway.com/";
                albertsonsURL = "https://ngcp-prf.albertsons.com/";
                GlobalConstants.BannerUrl = "https://ngcp-prf."+banner+".com/";

                switch (banner.toUpperCase()) {
                    case "ALBERTSONS":
                        bannerHomePage = "https://ngcp-prf.albertsons.com/";
                        bannerurl = "https://ngcp-prf.albertsons.com/";
                        break;
                    case "ACMEMARKETS":
                        bannerHomePage = "http://acmemarkets.wpuat.mywebgrocer.com/";
                        bannerurl = "https://ngcp-prf.acmemarkets.com/";
                        break;
                    case "JEWELOSCO":
                        bannerHomePage = "http://jewelosco.wpuat.mywebgrocer.com/";
                        bannerurl = "https://ngcp-prf.jewelosco.com/";
                        break;
                    case "SHAWS":
                        bannerHomePage = "http://shaws.wpuat.mywebgrocer.com/#1";
                        bannerurl = "https://ngcp-prf.shaws.com/";
                        break;
                    case "STARMARKET":
                        bannerHomePage = "http://starmarket.wpuat.mywebgrocer.com/#1";
                        bannerurl = "https://ngcp-prf.starmarket.com/";
                        break;
                    case "SAFEWAY":
                        bannerHomePage = "http://ngcp-prf.safeway.com/";
                        bannerurl = "https://ngcp-prf.safeway.com/";
                        break;
                    case "VONS":
                        bannerHomePage = "http://ngcp-prf.vons.com/";
                        bannerurl = "https://ngcp-prf.vons.com/";
                        break;
                    case "CARRSQC":
                        bannerHomePage = "http://ngcp-prf.carrsqc.com/";
                        bannerurl = "https://ngcp-prf.carrsqc.com/";
                        break;
                    case "RANDALLS":
                        bannerHomePage = "http://ngcp-prf.randalls.com/";
                        bannerurl = "https://ngcp-prf.randalls.com/";
                        break;
                    case "PAVILIONS":
                        bannerHomePage = "http://ngcp-prf.pavilions.com/";
                        bannerurl = "https://ngcp-prf.pavilions.com/";
                        break;
                    case "TOMTHUMB":
                        bannerHomePage = "http://ngcp-prf.tomthumb.com/";
                        bannerurl = "https://ngcp-prf.tomthumb.com/";
                        break;
                }

                break;
        }
    }


    public void loadContactData() throws Exception {
        title = "Mr.";
        firstname = "TestAutoFName";
        lastname = "TestAutoLName";
        streetAddress = "122827 Street AutoTest";
        suiteorApt = "503";
    }
    public void loadinvalidContactData() throws Exception {
        title = "Mr.";
        firstname = "^%$^#%^%$^#$#";
        lastname = "^#&*^&#$%&^$&";
        streetAddress = "ghdfjghdfgh&*&*^&&^%";
        suiteorApt = "erterty";
        phonenumber = "sdfsdg";
    }

    public void setDeepLinkdataExtractor(String emailAccount) throws Exception {

        switch (emailAccount.toUpperCase()) {
            case "HOTMAIL":
                emailaddress = "alb_trig1@hotmail.com";
                password = "@lt12345";
                emailToken = "x5BvSh9gUzK7/Nts1RB56A";
                emailTokenURL = "x5BvSh9gUzK7%2FNts1RB56A";
                HHID = "990003009919";
                cmpid = "abcd";
                Theme = "red";

                break;
            case "GMAIL":
                emailaddress = "loyaltytesting1005@gmail.com";
                password = "@lt12345";
                emailToken = "4+aBHkBBcRitRzrOXckK+g";
                emailTokenURL = "4%2BaBHkBBcRitRzrOXckK%2Bg";
                HHID = "990014298212";
                cmpid = "abcd";
                Theme = "red";
                break;
            case "YAHOOMAIL":
                emailaddress = "forautomation_4616@yahoo.com";
                password = "@lt12345";
                emailToken = "76avGFAYkZogLyn7KK62Cw";
                emailTokenURL = "76avGFAYkZogLyn7KK62Cw";
                HHID = "990003009478";
                cmpid = "abcd";
                Theme = "red";
                break;
        }
    }

public void getCity(){
    switch (banner.toLowerCase()) {
        case "safeway":
            if(city==null) {
                city = "DUBLIN";
            }
            break;
        case "vons":
            if(city==null) {
                city = "FRESNO";
            }
            break;
        case "pavilions":
            if(city==null) {
                city = "LOS ANGELES";
            }
            break;
        case "randalls":
            if(city==null) {
                city = "AUSTIN";
            }
            break;
        case "tomthumb":
            if(city==null) {
                city = "LEWISVILLE";
            }
            break;
        case "carrsqc":
            if(city==null) {
                city = "ANCHORAGE";
            }
            break;
        case "albertsons":
            if(city==null) {
                city = "KUNA";
            }
            break;
        case "shaws":
            if(city==null) {
                city = "KUNA";
            }
            break;
        case "starmarket":
            if(city==null) {
                city = "HYANNIS";
            }
            break;
        case "jewelosco":
            if(city==null) {
                city = "Chicago";
            }
            break;
        case "acmemarkets  ":
            if(city==null) {
                city = "Jersey City";
            }
            break;
      }
    }


    public void getHostSysOS() throws Exception{

        if(System.getProperty("os.name").indexOf("Mac")>=0){
            GlobalConstants.hostOSName = "MAC";
        }else if (System.getProperty("os.name").indexOf("Windows")>=0){
            GlobalConstants.hostOSName = "WINDOWS";
        }else if (System.getProperty("os.name").indexOf("nux")>=0) {
            GlobalConstants.hostOSName= "LINUX";
        }else if (System.getProperty("os.name").indexOf("nix")>=0) {
            GlobalConstants.hostOSName = "UNIX";
        }


    }

    public String getComputerName() throws Exception{

        String hostname = "Unknown";
        try
        {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        }
        catch (UnknownHostException ex)
        {
            ex.fillInStackTrace();
            System.out.println("Hostname can not be resolved");
        }
        return (hostname);
    }
    public void getEmailserver() throws Exception{

        if(emailaddress.contains("gmail")){
            GlobalConstants.EMAIL="GMAIL";
        } else if (emailaddress.contains("yahoo")){
            GlobalConstants.EMAIL="YAHOO";
        } else if (emailaddress.contains("hotmail")){
            GlobalConstants.EMAIL="HOTMAIL";
        }

    }

    public void loadContactusTestdata() throws Exception{

        if (inquirytype == null) {
            inquirytype = "AutoJohn";
        }
        if (loyaltycardorphonenumber == null) {
            loyaltycardorphonenumber = "";
        }
        if (firstname == null) {
            firstname = "Maria";
        }
        if (lastname == null) {
            lastname = "Santos";
        }
        if (contactmethod == null) {
            contactmethod = "Email";
        }
        if (emailaddress == null) {
            emailaddress = "forautomation_4618@yahoo.com";
        }
        if (zipcode == null) {
            zipcode = "99501";
        }
        if (city == null) {
            city = "99501";
        }
        if (state == null) {
            state = "99501";
        }

        if (address == null) {
            address = "5322 Otter Ln Middleberge FL 32068";
        }
        if (topic == null) {
            topic = "Please select a topic";
        }
        if (comments == null) {
            comments = "FOR TESTING PURPOSES ONLY";
        }
        if (whendidthishappen == null) {
            whendidthishappen = "07/29/2016";
        }

        if(topic.equals("Product Comments & Questions")){
            if(productname == null){
                productname = "Jumbo Yellow Onions PLU 4093";
            }
            if(productupc == null){
                productupc = "020735112045";
            }
            if(productsize == null){
                productsize = "1.02 oz";
            }
            if(productcode == null){
                productsize = "123QA4567WE890";
            }
        }

    }


}

