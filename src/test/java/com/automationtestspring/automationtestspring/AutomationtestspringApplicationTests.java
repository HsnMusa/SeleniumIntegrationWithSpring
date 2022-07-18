package com.automationtestspring.automationtestspring;
import dev.failsafe.internal.util.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AutomationtestspringApplicationTests {
    private WebDriver driver;

    @BeforeAll
    void beforeAll() {
        System.setProperty("webdriver.gecko.driver", "D:/Devops/demo/automationtestspring/geckodriver.exe");
        driver = new FirefoxDriver();
    }

    @Test
    void loginScreenTest() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/login");
        WebElement username = driver.findElement(By.name("ud"));
        WebElement password = driver.findElement(By.name("password"));
        WebElement loginBtn = driver.findElement(By.className("radius"));
        username.sendKeys("tomsmith");
        password.sendKeys("SuperSecretPassword!");
        loginBtn.submit();
        Thread.sleep(3000L);
        logout();
    }

    void logout() {
        WebElement logoutBtn = driver.findElement(By.linkText("Logout"));
        Assert.isTrue(logoutBtn.getText().equals("Logout"), "Assertion is Not True");
        logoutBtn.click();
    }

//    private static void doubleClickEvent() {
//        driver.navigate().to("https://the-internet.herokuapp.com/login");
//        WebElement label = driver.findElement(By.tagName("h4"));
//        System.out.println(label.getText());
//        // in case double click
//        Actions action = new Actions(driver);
//        action.doubleClick(label).perform();
//        //////////
//    }
//
//    private static void scroll() {
//        driver.navigate().to("https://the-internet.herokuapp.com/login");
//        WebElement label = driver.findElement(By.tagName("h4"));
//        System.out.println(label.getText());
//        // in case double click
//        Actions action = new Actions(driver);
//        action.scrollByAmount(10, 79).perform();
//        //////////
//    }
//
//    static void dropDownScreenTest() {
//        driver.navigate().to("https://the-internet.herokuapp.com/dropdown");
//        WebElement element = driver.findElement(By.id("dropdown"));
//        Select select = new Select(element);
//        select.selectByValue("1");
//        List<WebElement> options = select.getOptions();
//        for (int i = 1; i <= options.size(); i++) {
//            Assert.isTrue(options.get(i - 1).getText().equals("Option " + i), "Not Named Perfectly");
//        }
//        Assert.isTrue(!select.getAllSelectedOptions().isEmpty(), "Not Selected");
//    }
//
//    static void tableScreenTest() {
//        driver.navigate().to("https://the-internet.herokuapp.com/tables");
//        WebElement table = driver.findElement(By.id("table1"));
//
//        List<WebElement> rows = table.findElements(By.tagName("tr"));
//        Assert.isTrue(rows.size() == 5, "");
//
//        for (int i = 1; i < rows.size(); i++) {
//            System.out.println(rows.get(i));
//            List<WebElement> tds = rows.get(i).findElements(By.tagName("td"));
//            Assert.isTrue(tds.size() == 6, "columns must be 5");
//            for (int j = 0; j < tds.size(); j++) {
//                if (j == 3) {
//                    int pointIndex = tds.get(j).getText().indexOf(".");
//                    String decimalDigits = tds.get(j).getText().substring(pointIndex + 1);
//                    Assert.isTrue(decimalDigits.length() == 2, "Must Be Two digits after decimal point");
//                }
//                if (j == 5) {
//                    List<WebElement> elements = tds.get(j).findElements(By.tagName("a"));
//                    for (int k = 0; k < elements.size(); k++) {
//                        if (elements.get(k).getText().equals("delete")) {
//                            elements.get(k).click();
//                            break;
//                        }
//                    }
//                }
//                System.out.println(tds.get(j));
//            }
//        }
//    }
//
//    void getByXPath() {
//        WebElement xpath1 = driver.findElement(By.xpath("//*{@id=\"username\"}"));
//        WebElement xpath2 = driver.findElement(By.xpath("//input[@id='username']"));
//    }
//
//    static boolean isElementExist(By by) {
//        try {
//            driver.findElement(by);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }

}
