package google;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LogInTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("http://google.com");
        System.out.println(driver.getTitle());
        System.out.println(driver.getCurrentUrl());
        driver.close();
    }
}