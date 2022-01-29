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
 * @date 2021/9/27 14:27
 */
public class Sign extends LoginPortal {

    static WebDriver driver;

    //新建直播
    public static void addScence(int type, int status) throws InterruptedException {

        if (type <= 3 && status <= 3) {
            String title = "autoTest" + Calendar.getInstance().getTimeInMillis();

            driver.findElement(By.linkText("新建直播")).click();
            Thread.sleep(1000);

            driver.findElement(By.name("title")).sendKeys(title);//录入标题
            driver.findElement(By.xpath("//form[@class='layui-form']/div[5]/div/div[" + type + "]")).click();//设置直播类型
            driver.findElement(By.cssSelector("input.layui-input.layui-unselect")).click();//设置直播状态
            driver.findElement(By.xpath("//dl[@class='layui-anim layui-anim-upbit']/dd[" + status + "]")).click();//直播状态下拉列表选择状态
            driver.findElement(By.cssSelector("div.addimg.addimg16-9.num1.addimg-btn")).click();

            if (type == 1) {
                driver.findElement(By.name("liveAddress")).sendKeys("http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8");
                driver.findElement(By.name("h5LiveAddress")).sendKeys("http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8");
            }
            if (type == 3) {
                driver.findElement(By.name("outAddress")).sendKeys("http://ivi.bupt.edu.cn/hls/cctv5phd.m3u8");
            }
            Thread.sleep(1000);

            //在线资源库获取图片
            CommonMethod.getImg(driver);
            Thread.sleep(1500);

            driver.findElement(By.cssSelector("button.layui-btn.layui-btn-primary")).click();//保存直播数据
            Thread.sleep(3000);
            System.out.println("~~~ addScence() 新建直播，执行成功~~~");

        } else
            System.out.println("类型及状态应为1、2、3");
        Thread.sleep(3000);
    }

    //新建直播，默认普通，直播中
    public static void addScence() throws InterruptedException {
        addScence(1, 2);
        Thread.sleep(3000);
    }

    //发布直播
    public static void publish() throws InterruptedException {
        searchAuto();
        List<WebElement> scences = driver.findElements(By.cssSelector("li.list-item.fl"));

        if (scences.size() == 0) {
            addScence();
            searchAuto();
            Thread.sleep(3000);
        }

        driver.findElement(By.xpath("//li[@class='fl isPublish']")).click();
        Thread.sleep(200);
        driver.findElement(By.className("layui-layer-btn0")).click();
        Thread.sleep(2000);

        System.out.println("~~~ publish() 发布直播，执行成功~~~");
        Thread.sleep(3000);

    }

    //编辑直播
    public static void edit() throws InterruptedException {
        //搜索自动化创建的直播
        searchAuto();
        Actions action = new Actions(driver);

        List<WebElement> scences = driver.findElements(By.cssSelector("li.list-item.fl"));//获取直播数据list

        if (scences.size() > 0) {
            action.moveToElement(driver.findElement(By.xpath("//li[@class='myicon fr']"))).perform();//悬浮显示出编辑操作入口
            Thread.sleep(200);
            driver.findElement(By.linkText("编辑")).click();//进入到编辑页
            Thread.sleep(500);
            driver.findElement(By.name("title")).sendKeys("update");//修改标题
            driver.findElement(By.cssSelector("button.layui-btn.layui-btn-primary")).click();//保存直播数据
            Thread.sleep(3000);
        } else System.out.println("~~~~~没有可删除的自动化创建的直播~~~~~");
        System.out.println("~~~ edit() 编辑直播，执行成功~~~");
        Thread.sleep(3000);
    }

    //删除直播
    public static void delScence() throws InterruptedException {
        searchAuto();
        Actions action = new Actions(driver);

        List<WebElement> scences = driver.findElements(By.cssSelector("li.list-item.fl"));
        int num = scences.size();

        if (num > 0) {
            for (int i = 0; i < num; i++) {
                action.moveToElement(driver.findElement(By.xpath("//li[@class='myicon fr']"))).perform();
                Thread.sleep(200);
                driver.findElement(By.className("del")).click();
                Thread.sleep(500);
                driver.findElement(By.className("layui-layer-btn0")).click();
                Thread.sleep(3000);
                break;
            }
        } else System.out.println("~~~~~没有可删除的自动化创建的直播~~~~~");
        System.out.println("~~~ delScence() 删除直播，执行成功。共删除了 " + num + " 条。~~~");
        Thread.sleep(3000);
    }

    //互动直播添加现场报道
    public static void addReportLive() throws InterruptedException {
        //搜索自动化创建的直播
        searchAuto();
        String type;

        List<WebElement> scences = driver.findElements(By.cssSelector("li.list-item.fl"));//获取直播数据list
        if (scences.size() > 0) {
            for (int i = 0; i < scences.size(); i++) {
                type = driver.findElement(By.xpath("//li[@class='list-item fl'][" + (i + 1) + "]/div[1]/span")).getText();//获取直播类型
                if (type.equals("互动直播")) {
                    driver.findElement(By.linkText("相关报道")).click();//进入到互动直播的相关报道页面
                    Thread.sleep(500);
                    driver.findElement(By.cssSelector("i.layui-icon.addLive1")).click();//打开创建现场的界面
                    Thread.sleep(500);
                    //录入现场信息
                    driver.findElement(By.name("title")).sendKeys("autoTest现场");//标题
                    driver.findElement(By.name("liveAddress")).sendKeys("http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8");
                    driver.findElement(By.name("h5LiveAddress")).sendKeys("http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8");
                    Thread.sleep(500);

                    //开始操作上传封面~
                    driver.findElement(By.cssSelector("div.addimg.addimg-btn")).click();
                    Thread.sleep(1000);

                    //在线资源库获取图片
                    CommonMethod.getImg(driver);
                    Thread.sleep(1500);

                    driver.findElement(By.className("saveData")).click();//保存现场报道
                    break;
                }
            }
        } else System.out.println("没有自动化创建的数据");
        System.out.println("~~~ addReportLive() 创建现场报道，执行成功~~~");
        Thread.sleep(3000);

    }

    //互动直播现场报道-审核
    public static void auditReportLive() throws InterruptedException {
        //搜索自动化创建的直播
        searchAuto();
        String type, typeLive;

        List<WebElement> scences = driver.findElements(By.cssSelector("li.list-item.fl"));//自动化直播list
        if (scences.size() > 0) {
            for (int i = 0; i < scences.size(); i++) {
                type = driver.findElement(By.xpath("//li[@class='list-item fl'][" + (i + 1) + "]/div[1]/span")).getText();//获取直播状态
                if (type.equals("互动直播")) {
                    driver.findElement(By.linkText("相关报道")).click();//进入互动直播的相关报道
                    Thread.sleep(2000);
                    List<WebElement> lives = driver.findElements(By.xpath("//ul[@id='live1']/li"));//直播报道的现场数据list
                    if (lives.size() > 0) {
                        for (int j = 0; j < lives.size(); j++) {
                            typeLive = driver.findElement(By.xpath("//ul[@id='live1']/li[" + (j + 1) + "]/p/span")).getText();//获取报道状态
                            if (typeLive.equals("【待审】")) {
                                driver.findElement(By.xpath("//ul[@id='live1']/li[" + (j + 1) + "]")).click();//打开现场直播详情
                                Thread.sleep(500);
                                driver.findElement(By.cssSelector("span.hidden.btn1")).click();//审核
                                Thread.sleep(500);
                                driver.findElement(By.className("layui-layer-btn0")).click();//审核确认
                                Thread.sleep(1000);
                            }
                        }
                    } else System.out.println("没取到现场直播的数据");
                    break;
                }
            }
        } else System.out.println("~~没有自动化创建的数据~~");
        System.out.println("~~~ auditReportLive() 审核现场报道，执行成功~~~");
        Thread.sleep(3000);
    }

    //互动直播现场报道-删除
    public static void delReportLive() throws InterruptedException {
        searchAuto();
        String title, type;

        List<WebElement> scences = driver.findElements(By.cssSelector("li.list-item.fl"));//自动化直播list
        if (scences.size() > 0) {
            for (int i = 0; i < scences.size(); i++) {
                title = driver.findElement(By.xpath("//li[@class='list-item fl'][" + (i + 1) + "]/div[@class='msg']/h4")).getText();
                type = driver.findElement(By.xpath("//li[@class='list-item fl'][" + (i + 1) + "]/div[@class='topcont']/span")).getText();//获取直播状态
                if (type.equals("互动直播")) {
                    driver.findElement(By.xpath("//li[@class='list-item fl'][" + (i + 1) + "]/ul/li[5]")).click();//进入互动直播的相关报道
                    Thread.sleep(2000);
                    if (CommonMethod.isJudgingElement(driver,By.xpath("//ul[@id='live1']/li"))) {
                        List<WebElement> lives = driver.findElements(By.xpath("//ul[@id='live1']/li"));//直播报道的现场数据list
                        if (lives.size() > 0) {
                            for (int j = 0; j < lives.size(); j++) {
                                driver.findElement(By.xpath("//ul[@id='live1']/li[1]")).click();//打开现场直播详情
                                Thread.sleep(1000);
                                driver.findElement(By.xpath("//div[@class='form-box live1form']/div/span[4]")).click();//点击删除
                                Thread.sleep(200);
                                driver.findElement(By.className("layui-layer-btn0")).click();//删除确认
                                Thread.sleep(3000);
                            }
                            System.out.println(title + "的相关报道被删除");
                            break;
                        }
                    } else {
                        System.out.println(title + "没有可删除的相关报道");
                        searchAuto();
                    }
                }
            }
        }
        System.out.println("~~~ delReportLive() 删除现场报道，执行成功~~~");
        Thread.sleep(3000);
    }

    //互动直播创建直播间
    public static void addReportLive2() throws InterruptedException {
        searchAuto();
        String type;
        List<WebElement> scences = driver.findElements(By.cssSelector("li.list-item.fl"));//自动化直播list

        if (scences.size() > 0) {
            for (int i = 0; i < scences.size(); i++) {
                type = driver.findElement(By.xpath("//li[@class='list-item fl'][" + (i + 1) + "]/div[1]/span")).getText();//获取直播状态
                if (type.equals("互动直播")) {
                    driver.findElement(By.linkText("相关报道")).click();//进入互动直播的相关报道
                    Thread.sleep(500);
                    driver.findElement(By.cssSelector("li.fl.live2")).click();//切换到直播间tab
                    driver.findElement(By.cssSelector("i.layui-icon.addLive2")).click();//打开创建直播间的界面
                    Thread.sleep(500);
                    //录入直播间信息
                    driver.findElement(By.xpath("//div[@class='form-box live2form']/form/div[1]/div/input")).sendKeys("autoTest直播间");//标题
                    driver.findElement(By.xpath("//div[@class='form-box live2form']/form/div[2]/div/input")).sendKeys("记者张，记者吴");//记者
                    driver.findElement(By.xpath("//div[@class='form-box live2form']/form/div[@class='layui-form-item layui-form-text']/div/textarea")).sendKeys("这是报道内容这是报道内容这是报道内容这是报道内容这是报道内容");//报道内容
                    driver.findElement(By.xpath("//div[@class='form-box live2form']/div/span[@class='saveData']")).click();//保存直播间信息
                    break;
                }
            }
        } else System.out.println("没有自动化创建的数据");
        System.out.println("~~~ addReportLive2() 创建直播间，执行成功~~~");
        Thread.sleep(3000);
    }

    //互动直播直播间-审核
    public static void auditReportLive2() throws InterruptedException {
        searchAuto();
        String type, typeLive;

        List<WebElement> scences = driver.findElements(By.cssSelector("li.list-item.fl"));//自动化直播list
        if (scences.size() > 0) {
            for (int i = 0; i < scences.size(); i++) {
                type = driver.findElement(By.xpath("//li[@class='list-item fl'][" + (i + 1) + "]/div[1]/span")).getText();//获取直播状态
                if (type.equals("互动直播")) {
                    driver.findElement(By.linkText("相关报道")).click();//进入互动直播的相关报道
                    Thread.sleep(500);
                    driver.findElement(By.cssSelector("li.fl.live2")).click();//切换到直播间tab
                    Thread.sleep(2000);
                    List<WebElement> lives = driver.findElements(By.xpath("//ul[@id='live2']/li"));//获取直播间数据list
                    if (lives.size() > 0) {
                        for (int j = 0; j < lives.size(); j++) {
                            typeLive = driver.findElement(By.xpath("//ul[@id='live2']/li[" + (j + 1) + "]/div/p[2]/span[2]")).getText();//获取报道状态
                            if (typeLive.equals("待审")) {
                                driver.findElement(By.xpath("//ul[@id='live2']/li[" + (j + 1) + "]")).click();//打开直播间详情
                                Thread.sleep(500);
                                driver.findElement(By.xpath("//div[@class='form-box live2form']/div/span")).click();//审核
                                Thread.sleep(500);
                                driver.findElement(By.className("layui-layer-btn0")).click();//审核确认
                                Thread.sleep(1000);
                            }
                        }
                        break;
                    } else System.out.println("没取到直播间的数据");
                }
            }
        }
        System.out.println("~~~ auditReportLive2() 审核直播间，执行成功~~~");
        Thread.sleep(3000);
    }

    //互动直播直播间-删除
    public static void delReportLive2() throws InterruptedException {
        searchAuto();
        String title, type;

        List<WebElement> scences = driver.findElements(By.cssSelector("li.list-item.fl"));//自动化直播list
        if (scences.size() > 0) {
            for (int i = 0; i < scences.size(); i++) {
                title = driver.findElement(By.xpath("//li[@class='list-item fl'][" + (i + 1) + "]/div[@class='msg']/h4")).getText();
                type = driver.findElement(By.xpath("//li[@class='list-item fl'][" + (i + 1) + "]/div[@class='topcont']/span")).getText();//获取直播状态
                if (type.equals("互动直播")) {
                    driver.findElement(By.xpath("//li[@class='list-item fl'][" + (i + 1) + "]/ul/li[5]")).click();//进入互动直播的相关报道
                    Thread.sleep(500);
                    driver.findElement(By.cssSelector("li.fl.live2")).click();//切换到直播间tab
                    Thread.sleep(2000);
                    if (CommonMethod.isJudgingElement(driver,By.xpath("//ul[@id='live2']/li"))) {
                        List<WebElement> lives = driver.findElements(By.xpath("//ul[@id='live2']/li"));//直播报道的现场数据list
                        if (lives.size() > 0) {
                            for (int j = 0; j < lives.size(); j++) {
                                driver.findElement(By.xpath("//ul[@id='live2']/li[1]")).click();//打开现场直播详情
                                Thread.sleep(1000);
                                driver.findElement(By.xpath("//div[@class='form-box live2form']/div/span[4]")).click();//点击删除
                                Thread.sleep(200);
                                driver.findElement(By.className("layui-layer-btn0")).click();//删除确认
                                Thread.sleep(3000);
                            }
                            System.out.println(title + "的直播间被删除");
                            break;
                        }
                    } else {
                        System.out.println(title + "没有可删除的直播间");
                        searchAuto();
                    }
                }
            }
        }
        System.out.println("~~~ delReportLive() 删除现场报道，执行成功~~~");
        Thread.sleep(3000);
    }


    //搜索自动化创建的直播
    public static void searchAuto() throws InterruptedException {
        driver.get("http://app.test.pdmiryun.com/scene/sign");
        Thread.sleep(1000);
        driver.findElement(By.name("keyword")).sendKeys("autoTest");
        driver.findElement(By.id("searchBtn")).click();
        Thread.sleep(3000);
    }

    //初始化登录
    static {
        try {
            driver = login();
            for (int i = 0; i < 3; i++) {
                if (!CommonMethod.isJudgingElement(driver,By.tagName("header"))) {
                    driver.get("http://app.test.pdmiryun.com/scene/sign");
                    Thread.sleep(2000);
                }else break;
            }

            if (!driver.findElement(By.xpath("//div[@class='nav-right']/ul/li/a")).getText().contains("爱富县")) {
                Actions action = new Actions(driver);
                action.moveToElement(driver.findElement(By.className("nav-right"))).perform();
                Thread.sleep(500);
                driver.findElement(By.linkText("爱富县")).click();
                Thread.sleep(2000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
