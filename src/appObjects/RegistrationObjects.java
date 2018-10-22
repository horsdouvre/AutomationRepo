package appObjects;

public class RegistrationObjects {

	
	public static final String captchaUnChecked="//*[@class='recaptcha-checkbox goog-inline-block recaptcha-checkbox-unchecked rc-anchor-checkbox']";
	public static final String captchaCheckedSign="//*[@class='recaptcha-checkbox goog-inline-block recaptcha-checkbox-unchecked rc-anchor-checkbox recaptcha-checkbox-checked']";
	public static final String nextButton = "//*[@class='generic-button']";
	
	
	public static final String register_Link = ".//*[@id='LoginForm']/div[2]/div/div[2]/a/span[1]";
	public static final String register_FirstName = ".//*[@id='RegisterForm']/section[2]/div[1]/div[1]/input";
	public static final String register_LastName = ".//*[@id='RegisterForm']/section[2]/div[2]/div[1]/input";
	
	public static final String register_citizenId=".//*[@id='RegisterForm']/section[2]/div[3]/div[1]/input";
	public static final String register_dateOfBirth=".//*[@id='RegisterForm']/section[2]/div[4]/div[1]/input";
	public static final String register_postalCode=".//*[@id='RegisterForm']/section[2]/div[5]/div[1]/input";
	public static final String register_emailAddress=".//*[@id='RegisterForm']/section[2]/div[6]/div[1]/div[1]/input";
	public static final String register_confirmEmailAddress=".//*[@id='RegisterForm']/section[2]/div[6]/div[2]/div/input";
	public static final String register_checkBox="MobileNumber";
	public static final String register_mobilePhone=".//*[@id='RegisterForm']/section[2]/div[7]/div[2]/input"; 
	
	public static final String register_VerifyFNError = ".//*[@id='error-tooltip']/div[2]";
	public static final String register_VerifyLNError = ".//*[@id='error-tooltip']/div[2]";
	public static final String register_VerifyCorrectLandPage = "";
	public static final String register_VerifyLandPage = ".//*[@id='RegisterCreateUserProfile']/div[1]/section/div[1]/h1";
}



