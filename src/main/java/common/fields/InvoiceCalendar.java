package common.fields;

import com.google.common.collect.Iterables;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.testng.asserts.SoftAssert;

import java.util.*;

public class InvoiceCalendar {
    WebDriver driver;
    private WebElement monthYearElement;

    public InvoiceCalendar(WebDriver driver) {
        this.driver = driver;
    }

    private boolean isDisabled(WebElement element) {
        String disabledAttr = element.getAttribute("disabled");
        if (disabledAttr != null && disabledAttr.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    private List<String> getCurrentMonthYear() {
        monthYearElement = driver.findElement(By.xpath("//button[@aria-label='Choose month and year']"));
        List<String> monthYear = Arrays.asList(monthYearElement.getText().split(" "));
        String month = getCorrespondingMonth(monthYear.get(0));
        monthYear.set(0, month);
        return monthYear;
    }

    private String getCorrespondingMonth(String month) {
        if ("january".contains(month.toLowerCase())) {
            return "01";
        } else if ("february".contains(month.toLowerCase())) {
            return "02";
        } else if ("march".contains(month.toLowerCase())) {
            return "03";
        } else if ("april".contains(month.toLowerCase())) {
            return "04";
        } else if ("may".contains(month.toLowerCase())) {
            return "05";
        } else if ("june".contains(month.toLowerCase())) {
            return "06";
        } else if ("july".contains(month.toLowerCase())) {
            return "07";
        } else if ("august".contains(month.toLowerCase())) {
            return "08";
        } else if ("september".contains(month.toLowerCase())) {
            return "09";
        } else if ("october".contains(month.toLowerCase())) {
            return "10";
        } else if ("november".contains(month.toLowerCase())) {
            return "11";
        } else if ("december".contains(month.toLowerCase())) {
            return "12";
        }
        return "";
    }

    private void click(String xPath, String errorMessage) {
        WebElement button = driver.findElement(By.xpath(xPath));
        if (!isDisabled(button)) {
            button.click();
        } else {
//            SoftAssert softAssert = new SoftAssert();
//            softAssert.fail(errorMessage);
        }
    }

    private void nextMonth() {
        click("//button[@aria-label='Next month']", "Invalid next Month");
    }

    private void previousMonth() {
        click("//button[@aria-label='Previous month']", "Invalid previous Month");
    }

    private void nextYear() {
        click("//button[@aria-label='Next 21 years']", "Invalid next year");
    }

    private void previousYear() {
        click("//button[@aria-label='Previous 21 years']", "Invalid previous year");
    }

    private void nextHour() {
        click("//button[@aria-label='Add a hour']", "Invalid next Hour");
    }

    private void previousHour() {
        click("//button[@aria-label='Minus a hour']", "Invalid previous Hour");
    }

    private void nextMinute() {
        click("//button[@aria-label='Add a minute']", "Invalid next Minute");
    }

    private void previousMinute() {
        click("//button[@aria-label='Minus a minute']", "Invalid previous minute");
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

    private void selectTableMonth(String selectedMonth) {
        int sMonth = Integer.parseInt(selectedMonth);
        WebElement monthsBody = driver.findElement(By.className("owl-dt-calendar-body"));
        List<WebElement> monthsRows = monthsBody.findElements(By.tagName("tr"));
        for (WebElement row : monthsRows) {
            List<WebElement> months = row.findElements(By.tagName("td"));
            for (WebElement month : months) {
                String tdContent = getCorrespondingMonth(month.getText());
                int mValue = Integer.parseInt(tdContent);
                if (mValue == sMonth) {
                    month.click();
                    return;
                }
            }
        }
    }

    private void selectYear(int sYear, int cYear) {
        monthYearElement.click();
        WebElement yearBody = driver.findElement(By.className("owl-dt-calendar-body"));
        if (cYear != sYear) {
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
                for(int i = yearsRows.size() - 1 ; i >=0 ; i--) {
                    List<WebElement> years = yearsRows.get(i).findElements(By.tagName("td"));
                    int lastYear = Integer.parseInt(years.get(years.size() - 1).getText());
                    if (lastYear < sYear) {
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
        List<WebElement> days = daysBody.findElements(By.className("owl-dt-calendar-cell-content"));
        for (WebElement day : days) {
            int dValue = Integer.parseInt(day.getText());
            int sDay = Integer.parseInt(selectedDay);
            if (!day.getAttribute("class").contains("owl-dt-calendar-cell-out") && dValue == sDay) {
                day.click();
                return;
            }
        }
    }

    private void selectHour(String selectedHour) {
        List<WebElement> timeItems = driver.findElements(By.className("owl-dt-timer-content"));
        for(WebElement item: timeItems) {
            WebElement hiddenSpan = item.findElement(By.className("owl-hidden-accessible"));
            if(hiddenSpan.getText().equals("Hour")) {
                WebElement hourInput = item.findElement(By.className("owl-dt-timer-input"));
                int cHour = Integer.parseInt(hourInput.getAttribute("value"));
                int sHour = Integer.parseInt(selectedHour);
                if(sHour > 23 || sHour < 0) {
                    System.out.println("Invalid selected hour");
                } else {
                    while(cHour > sHour) {
                        cHour--;
                        previousHour();
                    }
                    while (cHour < sHour) {
                        cHour++;
                        nextHour();
                    }
                }
            }
        }
    }

    private void selectMinute(String selectedMinute) {
        List<WebElement> timeItems = driver.findElements(By.className("owl-dt-timer-content"));
        for(WebElement item: timeItems) {
            WebElement hiddenSpan = item.findElement(By.className("owl-hidden-accessible"));
            if(hiddenSpan.getText().equals("Minute")) {
                WebElement minuteInput = item.findElement(By.className("owl-dt-timer-input"));
                int cMinute = Integer.parseInt(minuteInput.getAttribute("value"));
                int sMinute = Integer.parseInt(selectedMinute);
                if(sMinute > 59 || sMinute < 0) {
                    System.out.println("Invalid selected Minute");
                } else {
                    while(cMinute > sMinute) {
                        cMinute--;
                        previousMinute();
                    }
                    while (cMinute < sMinute) {
                        cMinute++;
                        nextMinute();
                    }
                }
            }
        }
    }

    // selected date with pattern dd-MM-YYYY
    public void selectDate(String selectedDate) {
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

    // selected time with pattern hh:MM
    public void selectTime(String selectedTime) {
        List<String> time = Arrays.asList(selectedTime.split(":"));
        selectHour(time.get(0));
        selectMinute(time.get(1));
    }

    // selected date time with pattern dd-MM-YYYY hh:MM
    public void selectDateTime(String selectedDateTime) {
        List<String> dateTime = Arrays.asList(selectedDateTime.split(" "));
        selectDate(dateTime.get(0));
        selectTime(dateTime.get(1));
    }

    public void openCalendarByName(String buttonName) {
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
