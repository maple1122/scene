import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.testng.Assert.*;

/**
 * @author wufeng
 * @date 2022/1/29 14:39
 */
public class AdTest {

    @Test(priority = 1)//添加广告
    public void testAddAd() throws InterruptedException {
        Ad.addAd();
    }

    @Test(priority = 2)//编辑广告
    public void testEditAd() throws InterruptedException {
        Ad.editAd();
    }

    @Test(priority = 3)//广告投入直播
    public void testAddLive() throws InterruptedException {
        Ad.addLive();
    }

//    @Test(priority = 4)//广告删除投入直播
//    public void testDelLive() throws InterruptedException {
//        Ad.delLive();
//    }

    @Test(priority = 5)//开启广告
    public void testOpenAd() throws InterruptedException {
        Ad.openAd();
    }

    @Test(priority = 6)//关闭广告
    public void testCloseAd() throws InterruptedException {
        Ad.closeAd();
    }

//    @Test(priority = 7)//删除广告
//    public void testDelAd() throws InterruptedException {
//        Ad.delAd();
//    }

    @BeforeMethod
    public void testStart(Method method) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Test case: "
                + method.getName());
    }

    @AfterMethod
    public void testEnd(Method method){
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<< Test End!\n");
    }
}