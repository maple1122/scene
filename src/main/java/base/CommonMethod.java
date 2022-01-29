package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;

/**
 * @author wufeng
 * @date 2021/12/28 17:17
 */
public class CommonMethod {

    //图片资源库获取图片文件
    public static void getImg(WebDriver driver) throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("button.cropSet-button.cropSet-button-primary.online")).click();//上传图片-在线资源库

        Thread.sleep(1000);
        driver.switchTo().frame("material_iframe");//切换到资源库frame进行操作
        Thread.sleep(1000);

        if (!isJudgingElement(driver, By.cssSelector("ul.mtl_nav.clearfix"))) {
            driver.switchTo().defaultContent();
            Thread.sleep(500);
            driver.findElement(By.cssSelector("button.cropSet-button.cropSet-button-primary.online")).click();
            Thread.sleep(1000);
            driver.switchTo().frame("material_iframe");//切换到资源库frame进行操作
            Thread.sleep(1000);
        }

        if (!isJudgingElement(driver, By.xpath("//ul[@class='mtl_videoList clearfix']/li")))
            Thread.sleep(2000);
        //获取素材数据列表
        List<WebElement> pics = driver.findElements(By.xpath("//*[@id='cont_1']/div[3]/div/ul/li"));

        //判断是否有音频素材，无素材则结束；有素材>则选择第1个图片
        if (pics.size() > 0) {
            driver.findElement(By.xpath("//*[@id='cont_1']/div[3]/div/ul/li[1]/div")).click();//选择第一张图片
            driver.findElement(By.cssSelector("button.mtl_btn.yes")).click();//融媒页确认添加图片返回
        } else {
            System.out.println("没有可用素材！");
            driver.findElement(By.cssSelector("button.mtl_btn.cancel")).click();//融媒页关闭返回
        }
        driver.switchTo().defaultContent();//退出当前iframe
        Thread.sleep(500);

        driver.findElement(By.cssSelector("button.cropSet-button.ok.save")).click();//保存图片
        Thread.sleep(1500);

        if (isJudgingElement(driver, By.cssSelector("button.cropSet-button.ok.save")))
            driver.findElement(By.cssSelector("button.cropSet-button.cancel")).click();
        Thread.sleep(2000);
    }

    //本地上传图片
    public static void uploadImg(WebDriver driver) throws InterruptedException, IOException {
        driver.findElement(By.cssSelector("button.cropSet-button.cropSet-button-primary.upload ")).click();
        Thread.sleep(500);
        Runtime.getRuntime().exec("D:\\autotest\\jixianAutoTest\\src\\main\\resources\\test.exe");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("button.cropSet-button.ok.save")).click();
        Thread.sleep(2000);

        if (isJudgingElement(driver, By.cssSelector("button.cropSet-button.ok.save")))
            driver.findElement(By.cssSelector("button.cropSet-button.cancel")).click();
        Thread.sleep(500);
    }

    //签发到测试频道
    public static Boolean getTestChannel(WebDriver driver) throws InterruptedException {
//        driver.findElement(By.cssSelector("input.layui-input.myKeyword2")).sendKeys("auto");//自动化要签发的频道搜索关键词
//        driver.findElement(By.cssSelector("button.layui-btn.layui-btn-primary.search2")).click();//点击搜索
//        Thread.sleep(1000);

        List<WebElement> channel1, channel2;//两级频道Tree的list（忽略站点名称级）
        String testChannelName;//测试频道名称
        Boolean hasTestChannel = false;//初始化是否找到测试频道的标识
        channel1 = driver.findElements(By.xpath("//div[@id='channelTree']/div/div"));//第一级频道list对象
        for (int i = 0; i < channel1.size(); i++) {//遍历一级频道
            if (isJudgingElement(channel1.get(i), By.xpath("div"))) {//校验一级频道下是否有二级频道
                channel2 = channel1.get(i).findElements(By.xpath("div"));//二级频道list对象
                for (int j = 0; j < channel2.size(); j++) {//遍历二级频道
                    if (isJudgingElement(channel2.get(j), By.xpath("div/div"))) {//校验二级频道
                        testChannelName = channel2.get(j).findElement(By.xpath("div/div")).getText();//获取二级频道名称
                        if (testChannelName.contains("auto")) {//校验二级频道名称是否有auto
                            hasTestChannel = true;//是auto名称，则更新hasTestChannel为true
                            channel2.get(j).findElement(By.xpath("div/i")).click();//选中该二级测试频道
                            Thread.sleep(1000);
                            break;
                        }
                    }
                }
            }
            if (hasTestChannel) break;
        }
        return hasTestChannel;//返回是否已选择测试频道标识
    }

    //校验元素是否存在
    public static boolean isJudgingElement(WebDriver driver, By by) {
        try {
            driver.findElement(by);
            return true;//有登录按钮，登录界面
        } catch (Exception e) {
            return false;//无登录按钮，非登录界面
        }
    }

    //校验元素是否存在
    public static boolean isJudgingElement(WebElement webElement, By by) {
        try {
            webElement.findElement(by);
            return true;//有登录按钮，登录界面
        } catch (Exception e) {
            return false;//无登录按钮，非登录界面
        }
    }
}
