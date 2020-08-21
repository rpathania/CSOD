package CSOD;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


import utility.loginCSOD;

public class MassDelReq {

	MassDelReq()
	{

	}
	public static void main(String[] args) throws Exception {

		boolean flag  = true;
		String log;
		String startDate;
		String trainingName;
		String requestedBy;
		String requestedDateTime;	
		int rownum = 1;

		
		String st  = loginCSOD.getInputDate();
		
		String pass = loginCSOD.getPassword();
	//	org.w3c.dom.Document document =  loginCSOD.getDocument(loginCSOD.currentDir.toAbsolutePath()+"\\configuration.xml");       
		SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
	
		Date limitDate = sdformat.parse(st);
		Date requestedDate = sdformat.parse("01/01/2019");
	//	Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
	//	System.setProperty("webdriver.gecko.driver", loginCSOD.currentDir.toAbsolutePath() + "\\geckodriver.exe");
		
		System.setProperty("webdriver.chrome.driver",loginCSOD.currentDir.toAbsolutePath()+"\\chromedriver.exe");
		//driver = new ChromeDriver();

		WebDriver driver = new ChromeDriver();
	//	WebDriver driver = new FirefoxDriver();

		PrintStream ps = loginCSOD.getLogFile();

		loginCSOD.login(driver,"nasib.dar@soprasteria.com",pass); //login

		loginCSOD.userProxy(driver,"066743"); // Proxying the user

		loginCSOD.navToApproveTraining(driver); // navigate to approve training
		loginCSOD.writeLog(ps, "Program Started at: " + new Date());

		do
		{

			requestedBy = loginCSOD.getUsername(driver, rownum); // get the Requested By name

			trainingName = loginCSOD.getStartDate(driver, rownum); // get the Training name with start date
			
			
			requestedDateTime = loginCSOD.getRequestedDate(driver, rownum);
			
			System.out.println("Req = "+requestedDateTime.substring(0,10));
			
			
			requestedDate = sdformat.parse(requestedDateTime.substring(0,10));

			//startDate = trainingName.substring(trainingName.length() -11 , trainingName.length()-1); // get the start date
			
		//	d2 = sdformat.parse(startDate);
			
		//	System.out.println("d2 = "+ d2 );
			
			System.out.println(" requestedDate= "+ requestedDate );
			
			System.out.println(" limitDate= "+ limitDate );
			//System.out.println("date2 = "+ startDate );
			
		//	loginCSOD.writeLog(ps, "Program Started at: " + new Date());
			
			
			if (limitDate.after(requestedDate)  )
			{
				System.out.println("Deteleting the row");
				loginCSOD.denyTraining(driver, rownum);
				
				loginCSOD.writeLog(ps, requestedBy +" : " +trainingName);
					
			}
			
			else {
				flag = false;
				System.out.println("Program terminating");
				
				loginCSOD.writeLog(ps, "Program Ended at: " + new Date());
			}
			
		/*	else
			{
				rownum++;
				
				if (rownum < 5) {
					continue;
				}
				else {
				System.out.println("not working");
				flag = false;
				}
			}*/
			//flag = false;

			//String loop = loginCSOD.resultsFound( driver,"29/08/2018");

			/*	for (int i =0;i< Integer.parseInt(loop);i++) {

			loginCSOD.denyTraining(driver,"29/08/2018");

		}*/

			

		}while (flag);
		//loginCSOD.denyTraining(driver,"29/08/2019"); //Denying training

		//	System.out.println(loginCSOD.resultsFound( driver,"29/08/2018"));

		//loginCSOD.closeBrowser(driver);


	}


}
