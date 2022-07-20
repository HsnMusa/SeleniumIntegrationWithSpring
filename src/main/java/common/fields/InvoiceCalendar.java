package common.fields;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.text.DateFormatSymbols;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class InvoiceCalendar {
    WebDriver driver;
    private WebElement monthYearElement;

    public InvoiceCalendar(WebDriver driver) {
        this.driver = driver;
    }

    private void nextMonth() {
        WebElement next = driver.findElement(By.xpath("//button[@aria-label='Next month']"));
        next.click();
    }

    private void previousMonth() {
        WebElement previous = driver.findElement(By.xpath("//button[@aria-label='Previous month']"));
        previous.click();
    }

    private void nextYear() {
        WebElement next = driver.findElement(By.xpath("//button[@aria-label='Next 21 years']"));
        next.click();
    }

    private void previousYear() {
        WebElement previous = driver.findElement(By.xpath("//button[@aria-label='Previous 21 years']"));
        previous.click();
    }

    private void selectMonth(String selectedMonth, String currentMonth) {
        int sMonthValue = Integer.parseInt(selectedMonth);
        int cMonthValue = Integer.parseInt(currentMonth);
        if (cMonthValue != sMonthValue) {
            while (cMonthValue > sMonthValue) {
                previousMonth();
                cMonthValue--;
            }
            while (cMonthValue < sMonthValue) {
                nextMonth();
                cMonthValue++;
            }
        }
    }

    private void selectTableMonth(String selectedMonth) throws ParseException {
        int sMonth = Integer.parseInt(selectedMonth);
        WebElement monthsBody = driver.findElement(By.className("owl-dt-calendar-body"));
        List<WebElement> monthsRows = monthsBody.findElements(By.tagName("tr"));
        for (WebElement row : monthsRows) {
            List<WebElement> months = row.findElements(By.tagName("td"));
            for (WebElement month : months) {
                String tdContent =  Integer.toString(getCorrespondingMonth(month.getText()));
                int mValue = Integer.parseInt(tdContent);
                if (mValue == sMonth) {
                    month.click();
                    return;
                }
            }
        }
    }

    private void selectYear(int sYear, int cYear) {
        if (cYear != sYear) {
            monthYearElement.click();
            WebElement yearBody = driver.findElement(By.className("owl-dt-calendar-body"));
            while (cYear > sYear) {
                List<WebElement> yearsRows = yearBody.findElements(By.tagName("tr"));
                for (WebElement row : yearsRows) {
                    List<WebElement> years = row.findElements(By.tagName("td"));
                    int firstYear = Integer.parseInt(years.get(0).getText());
                    if (firstYear > sYear) {
                        previousYear();
                        cYear--;
                        break;
                    } else {
                        for (WebElement year : years) {
                            int tdContent = Integer.parseInt(year.getText());
                            if (tdContent == sYear) {
                                year.click();
                                return;
                            }
                        }
                    }
                }
            }
            while (cYear < sYear) {
                List<WebElement> yearsRows = yearBody.findElements(By.tagName("tr"));
                for (WebElement row : yearsRows) {
                    List<WebElement> years = row.findElements(By.tagName("td"));
                    int firstYear = Integer.parseInt(years.get(0).getText());
                    if (firstYear > sYear) {
                        nextYear();
                        cYear++;
                        break;
                    } else {
                        for (WebElement year : years) {
                            int tdContent = Integer.parseInt(year.getText());
                            if (tdContent == sYear) {
                                year.click();
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private void selectDay(String selectedDay) {
        WebElement daysBody = driver.findElement(By.className("owl-dt-calendar-body"));
        List<WebElement> daysRows = daysBody.findElements(By.tagName("tr"));
        for (WebElement row : daysRows) {
            List<WebElement> days = row.findElements(By.tagName("td"));
            for (WebElement day : days) {
                WebElement tdSpan = day.findElement(By.tagName("span"));
                int dValue = Integer.parseInt(day.getText());
                int sDay = Integer.parseInt(selectedDay);
                if (!tdSpan.getAttribute("class").contains("owl-dt-calendar-cell-out") && dValue == sDay) {
                    day.click();
                    return;
                }
            }
        }
    }


    private List<String> getCurrentMonthYear() throws ParseException {
        monthYearElement = driver.findElement(By.xpath("//button[@aria-label='Choose month and year']"));
        List<String> monthYear = Arrays.asList(monthYearElement.getText().split(" "));
        int month = getCorrespondingMonth(monthYear.get(0));
        monthYear.set(0, Integer.toString(month));
        return monthYear;
    }

    private int getCorrespondingMonth(String month) throws ParseException {
        Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(month);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }


    // selected date with pattern dd-MM-YYYY
    public void selectDate(String selectedDate) throws ParseException {
        List<String> date = Arrays.asList(selectedDate.split("-"));
        List<String> monthYear = getCurrentMonthYear();
        int cYear = Integer.parseInt(monthYear.get(1));
        int sYear = Integer.parseInt(date.get(2));
        if (cYear == sYear) {
            selectMonth(date.get(1), monthYear.get(0));
            selectDay(date.get(0));
        } else {
            selectYear(sYear, cYear);
            selectTableMonth(date.get(1));
            selectDay(date.get(0));
        }
    }

    public void openCalendar(String buttonName) {
        WebElement openCalendar = driver.findElement(By.name(buttonName));
        openCalendar.click();
    }

    public void save() {
        List<WebElement> buttons = driver.findElements(By.className("owl-dt-control-button-content"));
        for (WebElement button : buttons) {
            if (button.getText().equals("Set")) {
                button.click();
            }
        }
    }
}
