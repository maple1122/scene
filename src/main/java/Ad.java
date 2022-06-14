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
 * @date 2022/1/29 11:16
 */
public class Ad extends LoginPortal {

    static WebDriver driver;

    //创建广告
    public static void addAd() throws InterruptedException {
        //预设广告标题名称
        String title = "autoTest" + Calendar.getInstance().getTimeInMillis();
        //打开创建广告页面
        driver.findElement(By.id("addAD")).click();
        Thread.sleep(2000);
        //设置标题
        driver.findElement(By.name("title")).sendKeys(title);
        //设置封面
        driver.findElement(By.cssSelector("i.layui-icon.layui-icon-add-1")).click();
        Thread.sleep(1500);
        //在线资源库获取图片
        CommonMethod.getImg(driver);

        //选择图片方式
        driver.findElement(By.xpath("//div[@class='radio-list']/div[3]/i")).click();
        Thread.sleep(500);
        //点击上传图片
        driver.findElement(By.cssSelector("span.btn-text-blue.addimg-btn2")).click();
        Thread.sleep(500);
        //在线资源库获取图片
        CommonMethod.getImg(driver);
        Thread.sleep(2000);
        //添加图片描述信息
        driver.findElement(By.xpath("//ul[@id='attrList']/li[1]/textarea")).sendKeys("图片标题" + Calendar.getInstance().getTimeInMillis());

        //添加广告简介信息
        driver.findElement(By.cssSelector("textarea.layui-textarea.desc")).sendKeys("这里是简介这里是简介这里是简介这里是简介这里是简介这里是简介这里是简介这里是简介这里是简介这里是简介这里是简介这里是简介");

        Thread.sleep(1000);
        driver.findElement(By.className("layui-layer-btn0")).click();//提交

        System.out.println("~~~ addAd() 创建广告，执行成功~~~");
        Thread.sleep(2000);
    }

    //编辑广告
    public static void editAd() throws InterruptedException {
        //获取自动化创建的广告数据
        searchAndAddAd();

        driver.findElement(By.cssSelector("li.fl.editAd")).click();//进入到编辑页
        Thread.sleep(500);
        driver.findElement(By.name("title")).sendKeys("update");//修改标题
        driver.findElement(By.className("layui-layer-btn0")).click();//保存广告数据
        Thread.sleep(3000);

        System.out.println("~~~ editAd() 编辑广告，执行成功~~~");
        Thread.sleep(2000);

    }

    //投放直播
    public static void addLive() throws InterruptedException {
        //先判断是否有在线的自动化直播，无则添加
        Live.searchAndAddLive();

        //搜索自动化创建的广告
        searchAndAddAd();

        List<WebElement> lives;
        Actions actions = new Actions(driver);

        driver.findElement(By.className("sign-logs-wrapper")).click();//进入到投放直播页
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[@class='search-input-group']/input")).sendKeys("autoTest");
        driver.findElement(By.cssSelector("a.layui-btn.search-btn.search-input-btn")).click();//查询自动化直播
        Thread.sleep(1000);

        if (CommonMethod.isJudgingElement(driver,By.xpath("//div[@class='layui-table-body layui-table-main']/table/tbody/tr"))) {
            lives = driver.findElements(By.xpath("//div[@class='layui-table-body layui-table-main']/table/tbody/tr"));

            int n = 1;
            for (int i = 0; i < lives.size(); i++) {
                n = n + 1;
                actions.moveToElement(lives.get(i)).perform();
                Thread.sleep(500);
                lives.get(i).findElement(By.xpath(".//td[6]/div/div/div/span[@class='btn-settime']")).click();
                Thread.sleep(500);

                driver.findElement(By.xpath("//div[@id='liveAdTime']/div[2]/input")).click();
                Thread.sleep(500);

                driver.findElement(By.cssSelector("i.layui-icon.laydate-icon.laydate-next-m")).click();
                driver.findElement(By.className("laydate-btns-confirm")).click();

                Thread.sleep(500);
                for (int m = n; m < 10; m++) {
                    if (CommonMethod.isJudgingElement(driver,By.xpath("//div[@id='layui-layer" + m + "']/div[@class='layui-layer-btn layui-layer-btn-r']/a[@class='layui-layer-btn0']"))) {
                        driver.findElement(By.xpath("//div[@id='layui-layer" + m + "']/div[@class='layui-layer-btn layui-layer-btn-r']/a[@class='layui-layer-btn0']")).click();
                        Thread.sleep(1000);
                        n = m;
                        break;
                    }
                }
            }
            driver.findElement(By.xpath("//div[@id='layui-layer1']/div[@class='layui-layer-btn layui-layer-btn-r']/a[@class='layui-layer-btn0']")).click();
        }
        System.out.println("~~~ addLive() 投放广告，执行成功~~~");
        Thread.sleep(2000);
    }

    //删除投放直播
    public static void delLive() throws InterruptedException {

        List<WebElement> lives;
        WebElement live;
        Actions actions = new Actions(driver);
        String adName;

        searchAndAddAd();
        if (!CommonMethod.isJudgingElement(driver,By.xpath("//ul[@class='line-list clearfix']/li[1]/div[@class='msg']/div[@class='title-line']/div[@class='sign-logs-box']/span"))) {
            addLive();
            Thread.sleep(2000);
        }

        List<WebElement> ads = driver.findElements(By.xpath("//ul[@class='line-list clearfix']/li"));
        adName = ads.get(0).findElement(By.xpath("div[@class='msg']/div[@class='title-line']/h4")).getText();
        ads.get(0).findElement(By.xpath(".//div[@class='sign-logs-box']/span")).click();
        Thread.sleep(1000);
        lives = driver.findElements(By.xpath("//ul[@class='content-list']/li"));
        for (int n = 0; n < lives.size(); n++) {
            live = driver.findElement(By.xpath("//ul[@class='content-list']/li[1]"));
            actions.moveToElement(live).perform();
            Thread.sleep(500);
            live.findElement(By.className("delete-btn")).click();
            Thread.sleep(500);
            driver.findElement(By.className("layui-layer-btn0")).click();
            Thread.sleep(2000);
        }
        System.out.println(adName + " 投放的直播已被删除");
        Thread.sleep(2000);
    }

    //开启广告
    public static void openAd() throws InterruptedException {

        String adName, path;
        int num = searchAndAddAd();

        for (int i = 1; i < num + 1; i++) {
            adName = driver.findElement(By.xpath("//li[@class='list-item fl'][" + i + "]/div[@class='msg']/div[@class='title-line']/h4")).getText();
            path = "//li[@class='list-item fl'][" + i + "]/div[@class='msg']/div[@class='title-line']/div/div[@class='layui-unselect layui-form-switch']";
            if (CommonMethod.isJudgingElement(driver,By.xpath(path))) {
                driver.findElement(By.xpath(path)).click();
                Thread.sleep(500);
                driver.findElement(By.className("layui-layer-btn0")).click();
                System.out.println(adName + " 被开启");
                break;
            } else System.out.println(adName + "已开启");
        }
    }

    //关闭广告
    public static void closeAd() throws InterruptedException {

        String adName, path;
        int num = searchAndAddAd();

        for (int i = 1; i < num + 1; i++) {
            adName = driver.findElement(By.xpath("//li[@class='list-item fl'][" + i + "]/div[@class='msg']/div[@class='title-line']/h4")).getText();
            path = "//li[@class='list-item fl'][" + i + "]/div[@class='msg']/div[@class='title-line']/div/div[@class='layui-unselect layui-form-switch layui-form-onswitch']";
            if (CommonMethod.isJudgingElement(driver,By.xpath(path))) {
                driver.findElement(By.xpath(path)).click();
                Thread.sleep(500);
                driver.findElement(By.className("layui-layer-btn0")).click();
                System.out.println(adName + " 被关闭");
                break;
            } else System.out.println(adName + "未开启");
        }
        Thread.sleep(2000);
    }

    //删除广告
    public static void delAd() throws InterruptedException {
        //获取自动化创建的广告数据
        int num = searchAndAddAd();
        for (int i = 0; i < num; i++) {
            driver.findElement(By.cssSelector("li.fl.deleteAd")).click();
            Thread.sleep(200);
            driver.findElement(By.className("layui-layer-btn0")).click();
            Thread.sleep(3000);
        }
        System.out.println("~~~delAd() 删除成功~~~");
    }


    //搜索自动化创建的广告
    public static void searchAuto() throws InterruptedException {
        driver.get(domain+"/scene/ad");
        Thread.sleep(1000);
        driver.findElement(By.name("keyword")).sendKeys("autoTest");
        driver.findElement(By.id("searchBtn")).click();
        Thread.sleep(2000);
    }

    //搜索无结果则添加自动化广告
    public static int searchAndAddAd() throws InterruptedException {
        searchAuto();
        List<WebElement> ads = driver.findElements(By.cssSelector("li.list-item.fl"));//自动化直播list
        if (ads.size() == 0) {
            addAd();
            searchAuto();
            Thread.sleep(2000);
            ads = driver.findElements(By.cssSelector("li.list-item.fl"));
        }
        return ads.size();
    }

    //初始化登录
    static {
        try {
            driver = login();
            for (int i = 0; i < 3; i++) {
                if (!CommonMethod.isJudgingElement(driver,By.tagName("header"))) {
                    driver.get(domain+"/scene/ad");
                    Thread.sleep(2000);
                }else break;
            }

            if (!driver.findElement(By.xpath("//div[@class='nav-right']/ul/li/a")).getText().contains(siteName)) {
                Actions action = new Actions(driver);
                action.moveToElement(driver.findElement(By.className("nav-right"))).perform();
                Thread.sleep(500);
                driver.findElement(By.linkText(siteName)).click();
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
