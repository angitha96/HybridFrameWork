package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

    public class MyAccountPage extends BasePage {

        WebDriver driver;

        public MyAccountPage(WebDriver driver) {

            super(driver);
        }

        @FindBy(xpath = "//h2[normalize-space()='My Account']")
        WebElement txtMyAccountHeading;
        @FindBy(xpath="//a[@class='list-group-item'][normalize-space()='Logout']")
        WebElement linkLogout;

        public boolean isMyAccountPageExists() {
            try {
                return(txtMyAccountHeading.isDisplayed());
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        }

        public void clickLogout(){

            linkLogout.click();

        }

    }