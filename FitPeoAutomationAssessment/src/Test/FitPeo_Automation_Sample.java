package Test;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;


public class FitPeo_Automation_Sample {
	public static void main(String[] args) throws Exception {
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.manage().window().maximize();
		
		driver.get("https://www.fitpeo.com/");
		
		driver.findElement(By.xpath("//div[6]/a")).click();
		
		Actions act = new Actions(driver);
		act.pause(Duration.ofSeconds(1)).perform();
		
		
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,400);");
		
		Robot r = new Robot();
	
		WebElement slide = driver.findElement(By.cssSelector(".MuiSlider-thumb"));
		act.dragAndDropBy(slide, 94, 0).pause(Duration.ofSeconds(3)).build().perform();
		
		for(int i =0;i<4;i++)
		r.keyPress(KeyEvent.VK_LEFT);
		act.pause(Duration.ofSeconds(1)).perform();
		r.keyPress(KeyEvent.VK_LEFT);
		
		r.keyRelease(KeyEvent.VK_LEFT);
		act.pause(Duration.ofSeconds(3)).perform();
		WebElement innum = driver.findElement(By.xpath("//div[1]/input[1]"));
		
		innum.click();
		
		act.pause(Duration.ofSeconds(3)).perform();
		
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_A);
		r.keyPress(KeyEvent.VK_BACK_SPACE);
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.keyRelease(KeyEvent.VK_A);
		r.keyRelease(KeyEvent.VK_BACK_SPACE);
		
		r.keyPress(KeyEvent.VK_5);
		r.keyRelease(KeyEvent.VK_5);
		r.keyPress(KeyEvent.VK_6);
		r.keyRelease(KeyEvent.VK_6);
		r.keyPress(KeyEvent.VK_0);
		r.keyRelease(KeyEvent.VK_0);
				
		List <WebElement> cbx = driver.findElements(By.xpath("//p[contains(text(),'CPT-99')]")); 
		int c=1;
		for(WebElement cx : cbx)
		{
			String cpt=cx.getText();
			
			if(cpt.contains("CPT-99091")||cpt.contains("CPT-99453")||cpt.contains("CPT-99454")||cpt.contains("CPT-99474"))
			{
				driver.findElement(By.xpath("//p[contains(text(),'"+cpt+"')]/..//input")).click();
			}
			
		} 
		act.pause(Duration.ofSeconds(20)).perform();
		
		WebElement reimbursement = driver.findElement(By.xpath("//p[contains(text(),'Patients Per Month:')]/p")); // Replace with actual ID or locator
         String expectedReimbursement = "$110700";
         if (expectedReimbursement.equals(reimbursement.getText())) {
             System.out.println("Reimbursement value is correct.");
         } else {
             System.out.println("Reimbursement value is incorrect.");
         }	
	driver.quit();
	}

}

