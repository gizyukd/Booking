package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class SearchSteps {
    String placeName;
    WebDriver driver;

    @Before
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1280,768));
    }

    @Given("Имя местности {string}")
    public void имяМестности(String placeName) {
        this.placeName = placeName;
    }

    @When("Выполняется поиск отелей по местности")
    public void выполняетсяПоискОтелейПоМестности() {
        driver.get("https://www.booking.com/searchresults.en-gb.html");
        driver.findElement(By.xpath("//input[@type='search']")).sendKeys("Minsk");
        driver.findElement(By.xpath("//form[@role='search']")).submit();
    }

    @Then("Первая локация {string} с рейтингом {string}")
    public void перваяЛокацияSmartTVПанорамныйВидМинскЦентрГорода(String placeName, String rating) {
        Assert.assertEquals(driver.findElement(By.xpath("//div[@data-testid='title']")).getText(), placeName,"На первом месте не локация:" + placeName);
        Assert.assertEquals(driver.findElement(By.xpath("//div[@data-testid='review-score']/div[@aria-label]")).getText(), rating, "Рейтинг отеля не равен: " + rating);
        ////div[text()='Sauna Jacuzzi 55"SmartTV Панорамный вид, Минск-центр города']
    }

    @After
    public void tearDown(){
        System.out.println("teardown");
        driver.quit();
    }
}
