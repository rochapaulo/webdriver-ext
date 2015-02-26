import org.openqa.selenium.WebDriver;

public class TestPage extends TestTemplate {

    public TestPage(WebDriver driver) {
        super(driver);
    }

    public TestPage clickButton() {
        System.out.println("ButtonClicked");
        return this;
    }

    public void clickButton2() {
        System.out.println("ButtonClicked");
    }

}