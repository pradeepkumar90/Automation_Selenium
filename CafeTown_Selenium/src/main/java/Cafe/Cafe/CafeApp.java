package Cafe.Cafe;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import junit.framework.Assert;

public class CafeApp {

	public static void main( String[] args )
	{
	  
	//Mozilla firefox driver
	  System.setProperty("webdriver.gecko.driver", "C:\\Drivers\\geckodriver.exe");
	  WebDriver driver=new FirefoxDriver();
	  
	  //Launch URL, validate the title and Login.
	  driver.get("http://cafetownsend-angular-rails.herokuapp.com/");
	  System.out.println(driver.getTitle()); 
	  String actTitle= driver.getTitle();
	  String expTitle= "CafeTownsend-AngularJS-Rails";
	  Assert.assertEquals(actTitle,expTitle);
	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);		  
	  driver.findElement(By.xpath("//input[@ng-model='user.name']")).sendKeys("Luke");
	  driver.findElement(By.xpath("//input[@ng-model='user.password']")).sendKeys("Skywalker");
	  driver.findElement(By.xpath("//button[@type='submit']")).click();
	  System.out.println("Login Successfull");
	  
	  // Read multiple data from excel sheet and create the employee data
	  try 
	  { 
		
		// Use excel sheet from Github
		File file = new File("C:/cafeApp_TestData.xlsx"); 
		FileInputStream iFile = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(iFile);      
		XSSFSheet sheet = wb.getSheet("Add_Sheet"); 

		int rowCount = sheet.getLastRowNum();
		for (int row=1; row<=rowCount; row++)
		{ 
		   String FirstName = sheet.getRow(row).getCell(0).getStringCellValue();
		   String LastName = sheet.getRow(row).getCell(1).getStringCellValue();
		   String StartDate = sheet.getRow(row).getCell(2).getStringCellValue();
		   String Email = sheet.getRow(row).getCell(3).getStringCellValue();
		   //System.out.println(FirstName + " , " + LastName + " , " +StartDate + " , " +Email);
		   
		   driver.findElement(By.xpath("//*[@id='bAdd']")).click();
		   driver.findElement(By.xpath("//input[@ng-model='selectedEmployee.firstName']")).sendKeys(FirstName);
		   driver.findElement(By.xpath("//input[@ng-model='selectedEmployee.lastName']")).sendKeys(LastName);
		   driver.findElement(By.xpath("//input[@ng-model='selectedEmployee.startDate']")).sendKeys(StartDate);
		   driver.findElement(By.xpath("//input[@ng-model='selectedEmployee.email']")).sendKeys(Email); 	  
		   driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);	  
		   driver.findElement(By.xpath("//button[@ng-show='isCreateForm']")).click();
		   driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		   System.out.println("Added " + row + "data");
		}
		iFile.close();

	  }

	  catch (IOException e) { e.printStackTrace();
	} 
	  
	  
	  // Read data from excel sheet to edit the selected data.
	  try 
		{ 
		  
		  	// Use excel sheet from Github
			File file = new File("C:/cafeApp_TestData.xlsx"); 
			FileInputStream iFile = new FileInputStream(file);
			XSSFWorkbook wb = new XSSFWorkbook(iFile);      
			XSSFSheet sheet = wb.getSheet("Edit_Sheet"); 

			int rowCount = sheet.getLastRowNum();
			for (int row=1; row<=rowCount; row++)
			{ 
			   String FirstName = sheet.getRow(row).getCell(0).getStringCellValue();
			   String LastName = sheet.getRow(row).getCell(1).getStringCellValue();	
			   String StartDate = sheet.getRow(row).getCell(2).getStringCellValue();
			   String Email = sheet.getRow(row).getCell(3).getStringCellValue();
			   
			   String myXpath = "//ul[@id='employee-list']/li[contains(text(), '" + FirstName + " " + LastName + "')]";
			   driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
			   driver.findElement(By.xpath(myXpath)).click();
			   driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
			   driver.findElement(By.xpath("//*[@id='bEdit']")).click();
			   driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			   driver.findElement(By.xpath("//input[@ng-model='selectedEmployee.startDate']")).sendKeys(Keys.CONTROL + "a");
			   driver.findElement(By.xpath("//input[@ng-model='selectedEmployee.startDate']")).sendKeys(Keys.DELETE);
			   driver.findElement(By.xpath("//input[@ng-model='selectedEmployee.startDate']")).sendKeys(StartDate);
			   
			   driver.findElement(By.xpath("//input[@ng-model='selectedEmployee.email']")).sendKeys(Keys.CONTROL + "a");
			   driver.findElement(By.xpath("//input[@ng-model='selectedEmployee.email']")).sendKeys(Keys.DELETE);
			   driver.findElement(By.xpath("//input[@ng-model='selectedEmployee.email']")).sendKeys(Email);
			   driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			   driver.findElement(By.xpath("//button[@ng-hide='isCreateForm']")).click();
			   System.out.println("Edited " + row + "date");

			}
						 
			iFile.close();

		}
		catch (IOException e) 
			{ 
				e.printStackTrace();
			} 	
			
	// Read data from excel sheet to delete the selected data. 
	try 
	{ 
		// Use excel sheet from Github
		File file = new File("C:/cafeApp_TestData.xlsx"); 
		FileInputStream iFile = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(iFile);      
		XSSFSheet sheet = wb.getSheet("Delete_Sheet"); 

		int rowCount = sheet.getLastRowNum();		
		for (int row=1; row<=rowCount; row++)
		{ 
		   String FirstName = sheet.getRow(row).getCell(0).getStringCellValue();
		   String LastName = sheet.getRow(row).getCell(1).getStringCellValue();	
		   driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		   String myXpath = "//ul[@id='employee-list']/li[contains(text(), '" + FirstName + " " + LastName + "')]";
		   driver.findElement(By.xpath(myXpath)).click();
		   driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		   driver.findElement(By.xpath("//*[@id='bDelete']")).click();
		   driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		   driver.switchTo().alert().accept();
		   driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		   System.out.println("Deleted " + row + "data");

		}
					 
		iFile.close();

	}
	catch (IOException e) 
		{ 
			e.printStackTrace();
		} 
	
	driver.findElement(By.xpath("//*[@ng-click='logout()']")).click();
	System.out.println("Logout Successfull");
	driver.quit();

}
}
