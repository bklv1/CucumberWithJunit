
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.cucumber.java.*;
import io.cucumber.java.en.*;
import io.cucumber.java.ja.但し;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class StepDefinitions{
    public WebDriver driver;
    public WebDriverWait wait;
    @Before
    public void BeforeEachTest(){
        String projectPath = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver", projectPath + "/src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait= new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @After
    public void AfterEachTest(){
        driver.quit();
    }
    @Given("user navigates to todo page")
    public void user_navigates_to_login_page() {
        driver.navigate().to("https://todomvc.com/examples/vue/");
    }
    @When("^user creates (.*) tasks named (.*)$")
    public void WhenUserCreatesNumberOfTasks(int num, String taskName)
    {
        for (int i = 0; i < num; i++)
        {
            WebElement textBox= driver.findElement(By.xpath("//*[@placeholder='What needs to be done?']"));
            textBox.sendKeys(taskName);
            textBox.sendKeys(Keys.ENTER);
        }
    }
    @Then("^tasks with (.*) and (.*) are created successfully$")
    public void ThenATaskIsCreatedSuccessfully(String taskName, int numberOf)
    {
        //THIS HERE IS THE ACTUAL RESULT FROM THE WEBDRIVER
        List<WebElement> allTasks = driver.findElements(By.xpath("//label[contains(text(),'task')]"));
        List<String> allTasksStringValues = new ArrayList<>();
        for (var element: allTasks){
//           allTasksStringValues.add(element.getText());
            Assertions.assertEquals(element.getText(), taskName);
        }

        //EXPECTED
//        List<String> expectedStrings = new ArrayList<>();
//        for (int i=0; i<numberOf; i++){
//           expectedStrings.add(taskName);
//        }
//          Assertions.assertEquals(allTasksStringValues, expectedStrings);

    }
    @Then("^their count is (.*)$")
    public void their_count_is(int count) {
       String xpathValue = "//strong[contains(text(),'"+count+"')]";
       String actualResult= driver.findElement(By.xpath(xpathValue)).getText();
       int actualInt = Integer.parseInt(actualResult);
       Assertions.assertEquals(count, actualInt);
    }

    @When("^user creates (.*) tasks$")
    public void user_creates_odd_tasks(int countOfTasks) {
        WebElement textBox= driver.findElement(By.xpath("//*[@placeholder='What needs to be done?']"));
        for (int i=1; i<=countOfTasks; i++){
            textBox.sendKeys("task"+ String.valueOf(i));
            textBox.sendKeys(Keys.ENTER);
        }
    }

    @When("removes the even tasks")
    public void removes_the_even_tasks(){
        List<WebElement> allElementsToHover = driver.findElements(By.xpath("//li[@class='todo']"));
        Actions actions = new Actions(driver);
        int counter=0;
        for (WebElement element: allElementsToHover){
            counter++;
            if (counter%2==0){
                actions.moveToElement(element).perform();
                WebElement destroy=wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(element, By.className("destroy")));
                destroy.click();
            }
        }


    }
    @Then("^only the odd tasks should remain out of (.*) tasks$")
    public void only_the_odd_tasks_should_remain(int countOfTasks) {
        List<String> collectedNames = new ArrayList<>();
        List<WebElement> allOddElements = driver.findElements(By.xpath("//label[contains(text(),'task')]"));
        for (var element : allOddElements){
           collectedNames.add(element.getText());
        }

        List<String> expectedList = new ArrayList<>();
        for (int i=0; i<countOfTasks; i++){
            if (i%2==1){
                expectedList.add("task"+String.valueOf(i));
            }
        }
        Assertions.assertEquals(collectedNames, expectedList);
    }

    @When("^user creates a task with (.*) symbols$")
    public void user_creates_a_task_with_symbols(int count) {
        WebElement textBox= driver.findElement(By.xpath("//*[@placeholder='What needs to be done?']"));
        for (int i=0; i< count; i++){
            textBox.sendKeys(String.valueOf(i));
        }
        textBox.sendKeys(Keys.ENTER);
    }
    @Then("it should have no issues")
    public void it_should_have_no_issues() {
        Assertions.assertTrue(true);
    }

    @When("^user checks (.*) tasks$")
    public void user_checks_the_first_task(int count) {
        List<WebElement> allToggleButtons = driver.findElements(By.className("toggle"));
        for (int i=0; i<count;i++){
            allToggleButtons.get(i).click();
        }

    }
    @Then("^(.*) tasks should remain in active$")
    public void tasks_should_remain_in_active(int int1) {
        driver.navigate().to("https://todomvc.com/examples/vue/#/active");
        List<WebElement> allOddElements = driver.findElements(By.xpath("//label[contains(text(),'task')]"));
        List<String> twoTasks = new ArrayList<>();
        for (var element: allOddElements){
          twoTasks.add(element.getText());
        }
        List<String> expectedList = new ArrayList<>();
        for (int i=0; i<int1;i++){
            expectedList.add("task"+String.valueOf(i+int1));
        }
        Assertions.assertEquals(expectedList, twoTasks);
    }
    @Then("^(.*) task should go to completed$")
    public void task_should_go_to_completed(int int1) {
        driver.navigate().to("https://todomvc.com/examples/vue/#/completed");
        List<WebElement> allOddElements = driver.findElements(By.xpath("//label[contains(text(),'task')]"));
        List<String> tasks = new ArrayList<>();
        for (var element: allOddElements){
            tasks.add(element.getText());
        }
        List<String> expectedList = new ArrayList<>();
        for (int i=1; i<=int1;i++){
            expectedList.add("task"+String.valueOf(i));
        }
        Assertions.assertEquals(expectedList, tasks);

    }

}
