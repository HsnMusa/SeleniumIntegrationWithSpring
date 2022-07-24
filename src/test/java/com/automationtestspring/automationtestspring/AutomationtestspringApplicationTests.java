package com.automationtestspring.automationtestspring;
import common.fields.InvoiceCalendar;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AutomationtestspringApplicationTests {
    private WebDriver driver;
    private String lang;

    private RemoteWebDriver remoteWebDriver;

    @BeforeAll
    void beforeAll() throws InterruptedException, MalformedURLException {
//        System.setProperty("webdriver.gecko.driver", "D:/Devops/demo/automationtestspring/geckodriver.exe");
//        driver = new FirefoxDriver();
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        remoteWebDriver = new RemoteWebDriver(new URL("http://192.168.0.14:4444"), firefoxOptions);
        lang = "English";
        remoteWebDriver.get("http://einvoice-srv.tradenet.com:8080/IMOREGATEWAY/IMORECORE/Einvoicing/Einvoicing/Einvoicing/landing");
        invoiceLogin();
    }

//    @Test
    void switchLang() {
        WebElement languageElement = driver.findElement(By.className("dropdown-toggle"));
        languageElement.click();
        WebElement item = driver.findElement(By.className("dropdown-item"));
        item.click();
    }

    void invoiceLogin() throws InterruptedException {
        List<WebElement> elements = remoteWebDriver.findElements(By.className("nav-link"));
//        WebElement languageElement = driver.findElement(By.className("dropdown-toggle"));
//        lang = languageElement.getText();
        for (WebElement element : elements) {
//            if (lang.equals("English")) {
//                String content = element.getText();
//                if(content.contains("current")) {
//                    content = content.split("\n")[0];
//                }
//                Assert.isTrue(content.equals(content.toUpperCase()), content + " Not in UpperCase");
//            }
            if(element.getText().equals("Login")) {
                element.click();
                Thread.sleep(3000L);
                WebElement email = remoteWebDriver.findElement(By.id("email"));
                WebElement password = remoteWebDriver.findElement(By.id("password"));
                WebElement loginBtn = remoteWebDriver.findElement(By.xpath("//button[@type='submit']"));
                email.sendKeys("mcyassoc@hlife.site");
                password.sendKeys("P@ssw0rd1");
                loginBtn.submit();
                break;
            }
        }
    }


    @Test
    void openInvoiceList() throws InterruptedException {
        Thread.sleep(20000L);
        WebElement element = remoteWebDriver.findElement(By.id("side-menu"));
        List<WebElement> items = element.findElements(By.tagName("li"));
        for(WebElement item : items) {
            WebElement upperItem = item.findElement(By.tagName("a"));
            if(upperItem.getText().toLowerCase().equals("invoices")) {
                upperItem.click();
                List<WebElement> internalElements = item.findElements(By.tagName("li"));
                for (WebElement internalElement : internalElements) {
                    WebElement taxInvoice = internalElement.findElement(By.tagName("a"));
                    if(taxInvoice != null) {
                        if (taxInvoice.getText().toLowerCase().equals("tax invoices")) {
                            taxInvoice.click();
                            goToAddInvoice();
                            break;
                        }
                    }
                }
            }
        }
    }

    void goToAddInvoice() throws InterruptedException {
        Thread.sleep(5000L);
        List<WebElement> buttons = remoteWebDriver.findElements(By.ByTagName.tagName("button"));
        for(WebElement button : buttons) {
            if(button.getText().contains("Add Tax Invoice")) {
                button.click();
                createInvoice();
            }
        }
    }

    void createInvoice() throws InterruptedException {
        // select Date
        Thread.sleep(5000L);
        InvoiceCalendar calendar = new InvoiceCalendar(remoteWebDriver);
//        calendar.openCalendarByName("InvoiceDate");
        calendar.openCalendarByName("supplyDate");
        calendar.selectDate("29-05-2050");
        calendar.selectTime("14:05");
        calendar.save();
    }

//    @Test
//    @Order(1)
    void loginScreenHerokuappTest() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/login");
        WebElement username = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("password"));
        WebElement loginBtn = driver.findElement(By.className("radius"));
        username.sendKeys("tomsmith");
        password.sendKeys("SuperSecretPassword!");
        loginBtn.submit();
        Thread.sleep(3000L);
        logoutHerokuapp();
    }

//    @Test
//    @Order(2)
    private void logoutHerokuapp() throws InterruptedException {
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
