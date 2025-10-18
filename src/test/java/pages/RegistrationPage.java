package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class RegistrationPage extends BasePage {

    WebDriver driver;
    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@id='input-firstname']")
    WebElement txtFirstname;
    @FindBy(xpath = "//input[@id='input-lastname']")
    WebElement txtLastname;
    @FindBy(xpath = "//input[@id='input-email']")
    WebElement txtEmail;
    @FindBy(xpath = "//input[@id='input-telephone']")
    WebElement txtTelephone;
    @FindBy(xpath = "//input[@id='input-password']")
    WebElement txtPassword;
    @FindBy(xpath = "//input[@id='input-confirm']")
    WebElement txtPasswordConfirm;
    @FindBy(xpath = "//input[@name='agree']")
    WebElement chkPrivacyPolicy;
    @FindBy(xpath = "//input[@value='Continue']")
    WebElement btnRegister;
    @FindBy(xpath="(//h1[normalize-space()='Your Account Has Been Created!'])[1]")
    WebElement msgConfirmation;

     //@FindBy(xpath = "//select[@id='input-country']")
    //WebElement drpdwnCountry;
    //public void setCountry(String countryName) {
    //Select countrySelect = new Select(drpdwnCountry);
    //countrySelect.selectByVisibleText(countryName);
    // }

    public void setFirstname(String firstname) {

        txtFirstname.sendKeys(firstname);
    }

    public void setLastName(String lastname) {

        txtLastname.sendKeys(lastname);
    }

    public void setEmail(String email) {

        txtEmail.sendKeys(email);
    }

    public void setTelephone(String telephone) {

        txtTelephone.sendKeys(telephone);
    }

    public void setPassword(String pwd) {

        txtPassword.sendKeys(pwd);
    }

    public void setConfirmPassword(String pwd) {

        txtPasswordConfirm.sendKeys(pwd);
    }


    public void clickPrivacyPolicy() {
        chkPrivacyPolicy.click();
    }

    public void clickRegister() {
        btnRegister.click();
    }

    public String getConfirmationMessage() {
        try {
            return msgConfirmation.getText();
        }
        catch (Exception e) {
           return (e.getMessage());
        }
    }
}
