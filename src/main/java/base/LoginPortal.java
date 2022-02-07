package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

/**
 * @author wufeng
 * @date 2021/9/27 11:49
 */
public class LoginPortal {

    static WebDriver driver = initDriver();

    //浏览器初始化
    public static WebDriver initDriver() {
        System.setProperty("webdriver.chrome.driver", "D:\\tools\\other\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("test-type");
        options.addArguments("ignore-certificate-errors");
        options.setAcceptInsecureCerts(true);
        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        return driver;
    }

    //登录portal后台
    public static void login(String username, String password) throws InterruptedException {
        driver.get("http://app.test.pdmiryun.com/portal/login");
        //校验是否需要登录
        if (CommonMethod.isJudgingElement(driver,By.className("loginBtn"))) {
            driver.findElement(By.name("username")).sendKeys(username);
            driver.findElement(By.name("password")).sendKeys(password);

            //手动拖动滑块
            Actions action = new Actions(driver);
            WebElement moveButton = driver.findElement(By.className("slide"));
            //移到滑块元素并悬停
            action.moveToElement(moveButton).clickAndHold(moveButton);
            action.dragAndDropBy(moveButton, 305, 0).perform();
            action.release();

            Thread.sleep(2000);
            driver.findElement(By.className("loginBtn")).click();
            Thread.sleep(3000);
        }
    }

    //默认wf账号登录
    public static WebDriver login() throws InterruptedException {
        login("wf", "test1234");
        return driver;
    }


}
