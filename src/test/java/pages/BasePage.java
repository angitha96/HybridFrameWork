package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

//Base/Parent page for all pages, common action used in pages stored here,eg: driver initiation in constructor

public class BasePage {

    WebDriver driver; // local driver

    //Constructor - initiate driver

    BasePage(WebDriver driver)
    {
        this.driver=driver;
        PageFactory.initElements(driver,this);

    }
}
