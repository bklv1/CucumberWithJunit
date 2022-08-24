import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/main/java", glue="", monochrome = true, publish = true)//,
    //    plugin = {"pretty","json:target/JSONReport/report.json", "junit:target/report.xml"})
public class TestRunner {
}