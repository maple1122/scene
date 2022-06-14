import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.testng.Assert.*;

/**
 * @author wufeng
 * @date 2022/1/29 14:41
 */
public class SignTest {

    @Test(priority = 0)//创建默认直播：普通直播，直播中状态
    public void testAddsignDefault() throws InterruptedException {
        Sign.addScence();
        Sign.addScence(2, 2);//创建不同类型不同状态的直播：type1-3：普通直播、互动直播、外部直播；status1-3：预告、直播中、回放
    }

    @Test(priority = 1)//编辑自动化创建的直播
    public void testEdit() throws InterruptedException {
        Sign.edit();
    }

    @Test(priority = 9)//发布自动化创建的直播
    public void testPublish() throws InterruptedException {
        Sign.publish();
    }

//    @Test(priority = 10)//删除自动化创建的直播
//    public void testDelScence() throws InterruptedException {
//        Sign.delScence();
//    }

    @Test(priority = 3)//互动直播报道添加现场
    public void testAddReportLive() throws InterruptedException {
        Sign.addReportLive();
    }

    @Test(priority = 4)//互动直播报道审核现场
    public void testAuditReport() throws InterruptedException {
        Sign.auditReportLive();
    }

//    @Test(priority = 7)//互动直播报道删除现场
//    public void testDelReport() throws InterruptedException {
//        Sign.delReportLive();
//    }

    @Test(priority = 5)//互动直播创建直播间
    public void testAddReportLive2() throws InterruptedException {
        Sign.addReportLive2();
    }

    @Test(priority = 6)//互动直播直播间审核
    public void testAuditReportLive2() throws InterruptedException {
        Sign.auditReportLive2();
    }

//    @Test(priority = 8)//互动直播互动报道删除直播间
//    public void testDelReport2() throws InterruptedException {
//        Sign.delReportLive2();
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