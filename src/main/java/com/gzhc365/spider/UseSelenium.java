package com.gzhc365.spider;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;

/**
 * @Author: ZhouYuling
 * @Description: 如果不能够获取post参数进行请求、另外一个方式就是使用selenium。虽然速度慢一点、但是所见即所得，避免js渲染造成的数据获取不完整和Ajax重要参数无从破解的问题。
 * @Date: Created in 下午 16:34 2019/7/10 0010
 * @Modified by:
 */
public class UseSelenium {

    private ChromeDriver webDriver;

    @Before
    public void Setup(){
        //参考这博文安装chromedriver.exe、https://www.cnblogs.com/tonyzq/p/7978833.html
        //设置chromedriver.exe路径
        File chromeDriverPath = new File("C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", chromeDriverPath.getAbsolutePath());
        //新建一个ChromeDriver
        webDriver = new ChromeDriver();
    }

    @Test
    public void writeCookies() {

        // 知乎--模拟登陆
//        webDriver.get("https://www.zhihu.com/signup");
//        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div/div/div[2]/div[2]/span")).click();
//        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div/div/div[2]/div[1]/form/div[1]/div[2]/div[1]/input")).sendKeys("435454770@qq.com");
//        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div/div/div[2]/div[1]/form/div[2]/div/div[1]/input")).sendKeys("");
//        webDriver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div/div/div[2]/div[1]/form/button")).click();

        //selenium打开一个网页
        webDriver.get("http://www.ashospital.com/professorlist.aspx");
        //selenium模拟点击
        webDriver.findElementById("LinkButton2").click();
        //xpath选择医生列表
        List<WebElement> elements = webDriver.findElements(By.xpath("//*[@class=\"mingyi\"]/ul/li"));//xpath语法http://www.w3school.com.cn/xpath/index.asp
        for (WebElement element : elements) {
            System.out.println("===");
            //xpath选择医生名字
            String docName = element.findElement(By.xpath("//*[@class=\"e_warp\"]/h2/a")).getAttribute("title");
            //xpath选择医生url
            String docInfoUrl = element.findElement(By.xpath("//*[@class=\"e_warp\"]/h2/a")).getAttribute("href");
            System.out.println(docName + "   " + docInfoUrl);
        }

        /** selenium的其他选择器 **/
        //通过html的id
//        webDriver.findElementById("");
        //通过html的name
//        webDriver.findElementByName("");
        //通过css选择器、在jsoup中很大部分使用到css选择器，这个前端应该比较熟悉
//        webDriver.findElementByCssSelector("");
        //通过html的className
//        webDriver.findElementByClassName("");

        /**selenium隐式等待**/
        try {
            //等待10分钟
            webDriver.wait(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**selenium显示等待**/
//        WebDriverWait webDriverWait = new WebDriverWait(webDriver,10);
//        WebElement inputKey = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("q")));//对于输入框、等待10s看能不能输入
//        WebElement button = webDriverWait.until(ExpectedConditions.elementToBeClickable((By.cssSelector(".btn-search"))));//对于按钮、等待10s看能不能点击


    }


}
