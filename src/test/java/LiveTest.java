import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.testng.Assert.*;

/**
 * @author wufeng
 * @date 2022/1/29 14:40
 */
public class LiveTest {

    @Test(priority = 5)//直播下线
    public void testOffLine() throws InterruptedException {
        Live.offLine();
    }

    @Test(priority = 1)//直播添加评论
    public void testAddComment() throws InterruptedException {
        Live.addComment();
        Live.addComment();
        Live.addComment();
    }

//    @Test(priority = 2)//直播删除评论
//    public void testDelComment() throws InterruptedException {
//        Live.delComment();
//    }

    @Test(priority = 3)//直播添加活动
    public void testAddActivity() throws InterruptedException {
        Live.addActivity();
    }

//    @Test(priority = 4)//直播删除活动
//    public void testDelActivity() throws InterruptedException {
//        Live.delActivity();
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