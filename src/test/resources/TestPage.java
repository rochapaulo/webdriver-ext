import org.hamcrest.Matcher;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TestPage extends TestTemplate {

    public TestPage(WebDriver driver) {
        super(driver);
    }

    public TestPage assertButton(Matcher<WebElement> matcher) {
        Assert.assertThat(button, matcher);
        return this;
    }

    public TestPage assertUnknown(Matcher<WebElement> matcher) {
        Assert.assertThat(unknown, matcher);
        return this;
    }

    public TestPage clickButton() {
        System.out.println("ButtonClicked");
        return this;
    }

    public void clickButton2() {
        System.out.println("ButtonClicked");
    }

}