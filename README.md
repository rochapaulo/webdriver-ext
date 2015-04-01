# webdriver-ext

[![Build Status](https://travis-ci.org/rochapaulo/webdriver-ext.svg)](https://travis-ci.org/rochapaulo/webdriver-ext)
[![Coverage Status](https://coveralls.io/repos/rochapaulo/webdriver-ext/badge.svg)](https://coveralls.io/r/rochapaulo/webdriver-ext)

##Gerador automático de métodos para PageObjects
-> Necessário ativar o processador de anotações da IDE

###Exemplo de uso:
  
1) Página Login.html
```html
<body>
  <form id="formLogin">
    <label for="loginID">Login ID</label>
    <input id="loginID" type="text">
    
    <label for="password">Login ID</label>
    <input id="password" type="text">
  </form>
  
  <a href="clear">
    <input id="clear" type="button" value="Clear">
  </a>
</body>
```
2) Mapeamento LoginTemplate.java
```java
public class LoginTemplate extends AbstractPage {

  public LoginTemplate(WebDriver driver) {
    super(driver);
  }

  @FindBy(id = "loginID")
  @PageElement(type = ComponentType.INPUT)
  protected WebElement loginID;
  
  @FindBy(id = "password")
  @PageElement(type = ComponentType.INPUT)
  protected WebElement password;
  
  @FindBy(id = "clear")
  @PageElement(type = ComponentType.BUTTON, generateAssert = false)
  protected WebElement clear;
  
}
```
3) Arquivo gerado pelo processador
```java
public class LoginPage extends LoginTemplate {

  public LoginPage(WebDriver driver) {
    super(driver);
  }
  
  public LoginPage assertLoginID(Matcher<WebElement> matcher) {
    Assert.assertThat(loginID, matcher);
    return this;
  }
  
  public LoginPage assertPassword(Matcher<WebElement> matcher) {
    Assert.assertThat(password, matcher);
    return this;
  }
  
  public LoginPage clickClear() {
    clear.click();
    return this;
  }
  
  public LoginPage typeLoginID(String value) {
    loginID.sendKeys(value);
    return this;
  }
  
  public LoginPage typePassword(String value) {
    password.sendKeys(value);
    return this;
  }

}
```

--------------------------------------------
Arquivos script com as implementações e dependencias de cada método

- ComponentType.UNKNOUWN [assert.method]
```script
imports:[
org.junit.Assert
org.openqa.selenium.WebElement
org.hamcrest.Matcher
]
 
body:[
__Assert.assertThat($element, matcher);
]
```

- ComponentType.BUTTON [button.method]
```script
imports:[
]
 
body:[
__$element.click();
]
```

- ComponentType.INPUT [input.method]
```script
#INPUT
imports:[
]
 
body:[
__$element.sendKeys(value);
]
```
 
