package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class loginCSOD {
	
	public static Path currentDir = Paths.get("");
	
	static String newLine = System.getProperty("line.separator");
	
	static public File logfile;
	static String localDirPath = currentDir.toAbsolutePath().toString() + "\\Files\\";
	
	static String logfileName = "log.txt";
	
	public static String getInputDate() {
		JFrame  f=new JFrame();   
		    String dt=JOptionPane.showInputDialog(f,"Please enter a date upto which the pending training requests\nneed to be deleted in (dd/mm/yyyy) format: ");     
		    
		    return dt;
	}
	
	
	public static String getPassword() {
		
		
		JPasswordField pwd = new JPasswordField(10);
	    int action = JOptionPane.showConfirmDialog(null, pwd,"Enter Password",JOptionPane.OK_CANCEL_OPTION);
	 /*   if(action < 0)JOptionPane.showMessageDialog(null,"Cancel, X or escape key selected");
	    else JOptionPane.showMessageDialog(null,"Your password is "+new String(pwd.getPassword()));*/
	   
		/*JFrame  f=new JFrame();   
		    String pass=JOptionPane.showInputDialog(f,"Please enter the password: ");   */  
		    
		    return new String(pwd.getPassword());
	}
	

	public static void login(WebDriver driver, String userid, String password) {

		WebDriverWait wait = new WebDriverWait(driver, 50);
	/*	//driver.get("https://soprasteria2-pilot.csod.com/"); //Pilot
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("userNameBox"))).sendKeys(userid);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("passWordBox"))).sendKeys(password);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit"))).click(); */
		
		driver.get("http://soprasteria.csod.com/");  //PROD

		
		//*[@id="defaultForm"]/div[4]/a

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"defaultForm\"]/div[4]/a"))).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userNameInput"))).sendKeys(userid);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("passwordInput"))).sendKeys(password);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submitButton"))).click();
		
		

		driver.manage().window().maximize();

	}

	public static void userProxy(WebDriver driver, String userid) {
		Actions action = new Actions(driver);

		WebDriverWait wait = new WebDriverWait(driver, 50);
		WebElement TrainingMgmt = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-Training Management")));
		action.moveToElement(TrainingMgmt).click().perform();
		WebElement Users = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/form/div[5]/div/ul/li[3]/ul/li[4]/a")));

		action.moveToElement(Users).click().perform();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userIdText"))).sendKeys(userid);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSearchUser"))).click(); 

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rptUsers_ctl00_ddlUserOptions"))).click(); 

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rptUsers_ctl00_ddlUserOptions_lnkProxyAsUser"))).click(); 

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_tbReason"))).sendKeys("Test"); 

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_ibLogin"))).click(); 

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"ctl00_ContentPlaceHolder1_dlgProxyAsUser_ibSubmit\"]"))).click();

	}

	public static void navToApproveTraining(WebDriver driver) {

		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(0));
		driver.close();
		driver.switchTo().window(tabs2.get(1));
		Actions action = new Actions(driver);

		WebDriverWait wait = new WebDriverWait(driver, 50);

		WebElement myAccount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-My Account")));
		action.moveToElement(myAccount).click().perform();
		WebElement welCome = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/form/div[5]/div/ul/li[1]/ul/li[1]/a")));

		action.moveToElement(welCome).click().perform();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_widgetLayout_rptWidgets_ctl00_widgetContainer_ctl00_rptNewUi_ctl03_Link"))).click();


	}

	public static org.w3c.dom.Document getDocument(String fileName) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		org.w3c.dom.Document doc = builder.parse(fileName);
		return doc;
	}
	public static String resultsFound(WebDriver driver,String dt) {
		
		WebDriverWait wait = new WebDriverWait(driver, 50);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_dateControl_textboxDate"))).sendKeys(dt);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_btnSearch"))).click();
		
		String rslt = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_Pager_lblResults"))).getText();
		//(10 results)
		
		rslt = rslt.substring(1,3);
		
		return rslt.trim();
	}
	
	public static ArrayList<String> excelRead() {
		
		ArrayList<String> inputArray = new ArrayList<String>();
		
		
		
		return inputArray;
		
	}
	
	public static String getStartDate(WebDriver driver, int rownum) {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		try {
		String trainingName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_EmpRequests_ctl0"+ rownum +"_lnkTitle"))).getText();
	//	ctl00_ContentPlaceHolder1_EmpRequests_ctl02_lnkTitle
		
		String trainingStartsAt = driver.findElement(By.id("ctl00_ContentPlaceHolder1_EmpRequests_ctl0"+rownum +"_PostTitleText")).getText();
		
	//	String trainingStartsAt = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_EmpRequests_ctl0"+rownum +"_PostTitleText"))).getText();
		return trainingName + trainingStartsAt;
		}
		catch(TimeoutException ex) {
			String trainingName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_EmpRequests_ctl0"+ rownum +"_lnkTitle"))).getText();
			//	ctl00_ContentPlaceHolder1_EmpRequests_ctl02_lnkTitle
				
			//	String trainingStartsAt = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_EmpRequests_ctl0"+rownum +"_PostTitleText"))).getText();
			
			 String trainingStartsAt = " ";
			 return trainingName + trainingStartsAt;
		}
		
		
	}
	
	public static String getRequestedDate(WebDriver driver, int rownum) {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		
		String requestedDate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/form/div[11]/div[2]/table[2]/tbody/tr/td[2]/table[3]/tbody/tr["+rownum+"]/td[5]"))).getText();
	//	/html/body/form/div[11]/div[2]/table[2]/tbody/tr/td[2]/table[3]/tbody/tr[2]/td[5]
		
		
		return requestedDate ;
		
	}
	
	public static String getUsername(WebDriver driver, int rownum) {
		
		WebDriverWait wait = new WebDriverWait(driver, 50);
		
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/form/div[11]/div[2]/table[2]/tbody/tr/td[2]/table[3]/tbody/tr["+rownum +"]/td[1]/a[1]"))).getText();
		
	//	/html/body/form/div[11]/div[2]/table[2]/tbody/tr/td[2]/table[3]/tbody/tr[2]/td[1]/a[1]
	}
	
	public static void writeLog(PrintStream ps, String info) throws IOException {
		
		ps.write(newLine.getBytes());
		ps.write(info.getBytes());
		ps.write(newLine.getBytes());
		
		
	}
	
	
	public static PrintStream getLogFile() throws FileNotFoundException {
		
		logfile = new File(localDirPath + logfileName);
		
		return  new PrintStream(logfile);
		
	}
	public static void denyTraining(WebDriver driver, int rownum) {

		WebDriverWait wait = new WebDriverWait(driver, 50);

	//	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_dateControl_textboxDate"))).sendKeys(dt);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_btnSearch"))).click();
	//	/html/body/form/div[11]/div[2]/table[2]/tbody/tr/td[2]/table[3]/tbody/tr[2]/td[7]/a[3]
//
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/form/div[11]/div[2]/table[2]/tbody/tr/td[2]/table[3]/tbody/tr[" +rownum + "]/td[7]/a[3]"))).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("comments"))).sendKeys("Session Expired (Pending Request)");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("SubmitButton"))).click();
		
		
	}

	public static void closeBrowser(WebDriver driver) {

		driver.close();

	}


}