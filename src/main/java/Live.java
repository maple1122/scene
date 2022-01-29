import base.CommonMethod;
import base.LoginPortal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.Calendar;
import java.util.List;

/**
 * @author wufeng
 * @date 2021/9/27 11:57
 */
public class Live extends LoginPortal {

    static WebDriver driver;

    //直播进行下线
    public static void offLine() throws InterruptedException {
        //搜索出自动化创建的直播数据
        int num = searchAndAddLive();
        Actions action = new Actions(driver);

        action.moveToElement(driver.findElement(By.xpath("//ul[@class='line-list clearfix']/li[1]/ul/li[@class='myicon fr']"))).perform();//悬浮显示出下线操作入口
        Thread.sleep(1000);
        driver.findElement(By.className("offlineLive")).click();//点击下线
        Thread.sleep(1000);
        driver.findElement(By.className("layui-layer-btn0")).click();//确定下线
        Thread.sleep(3000);
        System.out.println("~~~ offLine() 直播下线，执行成功。共下线了 " + num + " 条~~~");
        Thread.sleep(2000);
    }

    //新建评论
    public static void addComment() throws InterruptedException {
        //搜索出自动化创建的直播数据
        searchAndAddLive();

        driver.findElement(By.linkText("评论")).click();
        Thread.sleep(500);
        driver.findElement(By.id("add")).click();
        Thread.sleep(500);
        driver.findElement(By.name("desc")).sendKeys("autoTest-这是评论内容-" + Calendar.getInstance().getTimeInMillis());
        driver.findElement(By.className("layui-layer-btn0")).click();

        System.out.println("~~~ addComment() 添加评论成功 ~~~");
        Thread.sleep(2000);
    }

    //删除评论
    public static void delComment() throws InterruptedException {
        //搜索出自动化创建的直播数据
        searchAndAddLive();

        driver.findElement(By.linkText("评论")).click();
        Thread.sleep(500);
        if (CommonMethod.isJudgingElement(driver,By.xpath("//div[@class='layui-table-body layui-table-main']/table/tbody/tr"))) {
            driver.findElement(By.xpath("//div[@class='layui-table-header']/table/thead/tr[1]/th[1]/div/div")).click();
            driver.findElement(By.cssSelector("button.layui-btn.layui-btn-primary.removeCmt")).click();
            driver.findElement(By.className("layui-layer-btn0")).click();
        }
        System.out.println("~~~ delComment() 删除评论成功 ~~~");
        Thread.sleep(2000);
    }

    //添加活动
    public static void addActivity() throws InterruptedException {
        searchAndAddLive();

        driver.findElement(By.linkText("活动")).click();
        Thread.sleep(500);

        String typeweb = "input.add-icon.vote", typemes = "投票";
        for (int type = 0; type < 3; type++) {
            if (type == 1) typeweb = "input.add-icon.entry";
            if (type == 2) typeweb = "input.add-icon.survey";
            //添加投票活动
            driver.findElement(By.cssSelector(typeweb)).click();
            Thread.sleep(1000);
            int size = driver.findElements(By.xpath("//ul[@id='activityTypeList']/li")).size();
            WebElement style;
            if (size > 0) {
                for (int i = 1; i < size + 1; i++) {
                    style = driver.findElement(By.xpath("//ul[@id='activityTypeList']/li[" + i + "]"));
                    if (!style.getAttribute("style").contains("none")) {
                        driver.findElement(By.xpath("//ul[@id='activityTypeList']/li[" + i + "]")).click();
                        Thread.sleep(200);
                        break;
                    }
                }
            } else System.out.println("没有可选的" + typemes + "活动");
            driver.findElement(By.className("layui-layer-btn0")).click();
            Thread.sleep(3000);
        }
        System.out.println("~~~ addActivity() 添加活动，执行成功 ~~~");
        Thread.sleep(2000);
    }

    //删除活动
    public static void delActivity() throws InterruptedException {

        searchAndAddLive();
        Actions action = new Actions(driver);

        driver.findElement(By.linkText("活动")).click();
        Thread.sleep(500);
        int num = driver.findElements(By.className("item")).size();
        if (num > 0) {
            for (int i = 0; i < num; i++) {
                action.moveToElement(driver.findElement(By.xpath(".//li[@class='item'][1]"))).perform();
                driver.findElement(By.xpath(".//li[@class='item'][1]/div")).click();
                Thread.sleep(4000);
                break;
            }
            System.out.println("~~~ delActivity() 删除活动，执行成功。共删除活动 " + num + " 个 ~~~");
        } else System.out.println("该直播没有活动数据");
        Thread.sleep(2000);
    }

    //搜索自动化创建的直播
    public static void searchAuto() throws InterruptedException {
        //搜索自动化创建的直播
        driver.get("http://app.test.pdmiryun.com/scene/live");
        Thread.sleep(1000);
        driver.findElement(By.name("keyword")).sendKeys("autoTest");
        driver.findElement(By.id("searchBtn")).click();
        Thread.sleep(2000);
    }

    //搜索若无已发布的自动化创建的直播，则先进行发布
    public static int searchAndAddLive() throws InterruptedException {
        searchAuto();
        List<WebElement> scences = driver.findElements(By.cssSelector("li.list-item.fl"));//自动化直播list
        if (scences.size() == 0) {
            Sign.publish();
            searchAuto();
            Thread.sleep(2000);
            scences = driver.findElements(By.cssSelector("li.list-item.fl"));
        }
        return scences.size();
    }

    //初始化登录
    static {
        try {
            driver = login();
            for (int i = 0; i < 3; i++) {
                if (!CommonMethod.isJudgingElement(driver,By.tagName("header"))) {
                    driver.get("http://app.test.pdmiryun.com/scene/live");
                    Thread.sleep(2000);
                }else break;
            }

            if (!driver.findElement(By.xpath("//div[@class='nav-right']/ul/li/a")).getText().contains("爱富县")) {
                Actions action = new Actions(driver);
                action.moveToElement(driver.findElement(By.className("nav-right"))).perform();
                driver.findElement(By.linkText("爱富县")).click();
            }
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
