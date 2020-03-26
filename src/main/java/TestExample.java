import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class TestExample {

    public static IOSDriver<IOSElement> driver;

    private final static File app = new File("src/test/resources/QALection.zip");
    private final static String URL_STRING = "http://127.0.0.1:4723/wd/hub";
    private final static String phone = "+7 999 888 77 66";
    private final static String password = "password";

    @BeforeClass
    private static void setCapabilitys() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 7");
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13.3");
        caps.setCapability(MobileCapabilityType.NO_RESET, true);
        caps.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        caps.setCapability("useNewWDA", false);

        URL url = new URL(URL_STRING);
        driver = new IOSDriver<IOSElement>(url, caps);
    }

    @AfterClass
    public void tearDown() {
            driver.quit();
    }

    @Test(priority = 1)
    public void testOne() {
        By mainScreenTitle = By.id("MainScreenTitle");
        By phoneField = By.id("phoneTextField");
        By passwordField = By.id("passwordTextField");
        By button = By.id("signInButton");

        waitElementPresent(mainScreenTitle, 5);
        IOSElement phoneFieldElement = driver.findElement(phoneField);
        phoneFieldElement.click();
        phoneFieldElement.sendKeys(phone);
        IOSElement passwordFieldElement = driver.findElement(passwordField);
        passwordFieldElement.click();
        passwordFieldElement.sendKeys(password);
        driver.findElement(button).click();
        Assert.assertTrue(driver.findElementsByName("Пять").size() > 0);
    }

    @Test(priority = 2)
    public void testTwo() {
        By mainScreenTitle = By.id("MainScreenTitle");
        By backButton = By.id("backButton");
        By phoneField = By.id("phoneTextField");
        By passwordField = By.id("passwordTextField");

        IOSElement fiveField = driver.findElementsByName("Пять").get(0);
        fiveField.click();
        waitElementPresent(backButton, 5);
        driver.findElement(backButton).click();
        waitElementPresent(mainScreenTitle, 5);
        Assert.assertTrue(driver.findElement(phoneField).getText().equals("Номер телефона"));
        Assert.assertTrue(driver.findElement(passwordField).getText().equals("Пароль"));
    }

    public IOSElement waitElementPresent(By by, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        return (IOSElement) wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

}
