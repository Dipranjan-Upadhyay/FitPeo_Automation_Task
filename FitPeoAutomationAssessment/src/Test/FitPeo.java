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

public class FitPeo {

    private static WebDriver driver;
    private static Actions actions;
    private static JavascriptExecutor jsExecutor;
    private static Robot robot;

    public static void main(String[] args) {
        try {
            setup();
            navigateToFitPeoHomePage();
            navigateToRevenueCalculatorPage();
            scrollToSlider();
            adjustSlider(820);
            updateTextField(560);
            validateSliderValue(560);
            selectCPTCodes();
            validateTotalRecurringReimbursement("$110700");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            teardown();
        }
    }

    private static void setup() throws Exception {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().window().maximize();
        actions = new Actions(driver);
        jsExecutor = (JavascriptExecutor) driver;
        robot = new Robot();
    }

    private static void navigateToFitPeoHomePage() {
        driver.get("https://www.fitpeo.com/");
    }

    private static void navigateToRevenueCalculatorPage() {
        driver.findElement(By.xpath("//div[6]/a")).click();
    }

    private static void scrollToSlider() {
        jsExecutor.executeScript("window.scrollBy(0, 500);");
        actions.pause(Duration.ofSeconds(1)).perform();
    }

    private static void adjustSlider(int value) {
        WebElement slider = driver.findElement(By.cssSelector(".MuiSlider-thumb"));
        actions.dragAndDropBy(slider, 94, 0).pause(Duration.ofSeconds(3)).perform();
    }
    private static void validateSliderValue(int expectedValue) {
    	for(int i =0;i<4;i++)
    		robot.keyPress(KeyEvent.VK_LEFT);
    		actions.pause(Duration.ofSeconds(1)).perform();
    		robot.keyPress(KeyEvent.VK_LEFT);
    		
    		robot.keyRelease(KeyEvent.VK_LEFT);
    		actions.pause(Duration.ofSeconds(3)).perform();
    		WebElement innum = driver.findElement(By.xpath("//div[1]/input[1]"));
    		
    		innum.click();
    		
    		actions.pause(Duration.ofSeconds(3)).perform();
    }

    private static void updateTextField(int value) {
        WebElement textField = driver.findElement(By.xpath("//div[1]/input[1]"));
        textField.click();
        actions.pause(Duration.ofSeconds(3)).perform();

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyPress(KeyEvent.VK_BACK_SPACE);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_BACK_SPACE);

        for (char digit : String.valueOf(value).toCharArray()) {
            robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(digit));
            robot.keyRelease(KeyEvent.getExtendedKeyCodeForChar(digit));
        }
    }

    private static void selectCPTCodes() {
        List<WebElement> checkboxes = driver.findElements(By.xpath("//p[contains(text(),'CPT-99')]"));
        for (WebElement checkbox : checkboxes) {
            String cpt = checkbox.getText();
            if (cpt.contains("CPT-99091") || cpt.contains("CPT-99453") || cpt.contains("CPT-99454") || cpt.contains("CPT-99474")) {
                driver.findElement(By.xpath("//p[contains(text(),'" + cpt + "')]/..//input")).click();
            }
        }
    }

    private static void validateTotalRecurringReimbursement(String expectedValue) {
        WebElement reimbursement = driver.findElement(By.xpath("//p[contains(text(),'Patients Per Month:')]/p"));
        System.out.println(reimbursement.getText());
        String actualValue = reimbursement.getText();
        if (expectedValue.equals(actualValue)) {
            System.out.println("Reimbursement value is correct.");
        } else {
            System.out.println("Reimbursement value is incorrect.");
        }
    }

    private static void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

