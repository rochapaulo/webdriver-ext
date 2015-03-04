import org.hamcrest.Matcher;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TestPage extends TestTemplate {

    public TestPage(WebDriver driver) {
        super(driver);
    }

    public TestPage assertFirstName(Matcher<WebElement> matcher) {
        Assert.assertThat(firstName, matcher);
        return this;
    }

    public void assertLastName(Matcher<WebElement> matcher) {
        Assert.assertThat(lastName, matcher);
    }

    public TestPage clickCancel() {
        System.out.println("ButtonClicked");
        return this;
    }

    public TestPage clickSave() {
        System.out.println("ButtonClicked");
        return this;
    }

    public TestPage typeFirstName(String value) {
        System.out.println("Typed value = " + value + " into " + firstName.getText());
        return this;
    }

    public void typeLastName(String value) {
        System.out.println("Typed value = " + value + " into " + lastName.getText());
    }

    public TestPage typeNote(String value) {
        System.out.println("Typed value = " + value + " into " + note.getText());
        return this;
    }

}