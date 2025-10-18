package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{

    WebDriver driver; // local driver

    //Constructor - initiate driver

    public HomePage(WebDriver driver) //refer driver passed by test method
    {
       super(driver);

    }

    //Locators - xpath..

    @FindBy(xpath="//ul[@class='list-inline']//li[@class='dropdown']") WebElement accountBtn;
    @FindBy(xpath="(//a[normalize-space()='Register'])[1]") WebElement registerBtn;
    @FindBy(xpath="//a[normalize-space()='Login']") WebElement loginBtn;
    //Action methods - perform action on Web Element

    public void clickAccountBtn()
    {
        accountBtn.click();
    }

    public void clickRegisterBtn()
    {
        registerBtn.click();

    }

    public void clickLoginBtn()
    {
        loginBtn.click();

    }

}

