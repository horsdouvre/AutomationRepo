package utility;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonUtils {

	private static JSONObject jsonObject = null;
	public static String value;
	public static String arrayValue;
	public static String arrayObjValue;
	//public static ArrayList myList;
	public static String objectValue1;
	public static String path;
	//public static FileInputStream fis;
	//public static FileOutputStream fileOut;




	//To open the Json File and read data
	public static JSONObject openJson(String path) {
		try {
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(Constant.Path_Json));
		return jsonObject;
		
		} 
		catch (Exception e) {
		e.printStackTrace();
			
		}
		return jsonObject;
		
	}
	
	//To get an Array value
	public static String getJsonArrayValue(JSONObject object, String ArrayName, String objectName, String Value){
		
		try {
		JSONArray arrayName = (JSONArray) object.get(ArrayName);
        Iterator arrayIterator = arrayName.iterator();
        while (arrayIterator.hasNext()) {
			JSONObject innerObj = (JSONObject) arrayIterator.next();
		
			JSONObject objectValue = (JSONObject) innerObj.get(objectName);
			String arrayValue = (String) objectValue.get(Value);
			return arrayValue;
        }
		} 
		catch (Exception e) {
		e.printStackTrace();
			
		}
		return arrayValue;
	}
	
	//To get an Array value
	public static ArrayList getJsonArrayLoopValue(JSONObject object, String ArrayName, String objectName){
		
		JSONArray arrayName = (JSONArray) object.get(ArrayName);
		ArrayList myList = new ArrayList();
			
        Iterator arrayIterator = arrayName.iterator();
        while (arrayIterator.hasNext()) {
        	
			JSONObject innerObj = (JSONObject) arrayIterator.next();
				
			String objectValue1 = (String) innerObj.get(objectName);
				
			myList.add(objectValue1);
			
			}   
	
        return myList;
	}
	
	//To get an object value
		public static String getJsonObjectValue(JSONObject object, String objectName, String Value){
			try {
				JSONObject objectValue = (JSONObject) object.get(objectName);
				String value = (String) objectValue.get(Value);
						
			return value;
			}
			catch (Exception e) {
				e.printStackTrace();
					
			}
			return value;
		}
	
		
		//To get an Array object value
				public static String getJsonArrayObjectValue(JSONObject object, String objectName, String Value){
					try {
					JSONArray arrayName = (JSONArray) object.get(objectName);
			      Iterator arrayIterator = arrayName.iterator();
			        while (arrayIterator.hasNext()) {
						JSONObject innerObj = (JSONObject) arrayIterator.next();
						//String objectValue = (String) arrayName.get(Value);
					//	String value = (String) objectValue.get(Value);
						String arrayObjValue = (String) innerObj.get(Value);
						//System.out.println(payemntMethodCode);
						return arrayObjValue;
			        }
					}
					catch (Exception e) {
						e.printStackTrace();
							
					}
					return arrayObjValue;
				}
				
			
				
				//To get an object value
				public static String getJsonObjectObjValue(JSONObject object, String objectName,String obj, String Value){
					try {
						JSONObject objectValue = (JSONObject) object.get(objectName);
						JSONObject objectValue1 = (JSONObject) objectValue.get(obj);
						String value = (String) objectValue1.get(Value);
								
					return value;
					}
					catch (Exception e) {
						e.printStackTrace();
							
					}
					return value;
				}
			


}





