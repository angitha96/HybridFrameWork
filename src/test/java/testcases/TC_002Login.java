package testcases;

import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.MyAccountPage;
import testbase.BaseTest;

public class TC_002Login extends BaseTest {

    @Test(groups={"Sanity", "Master"})
    public void verifyLogin(){
     try {

         logger.info("Starting login test");
         HomePage hp = new HomePage(driver);
         hp.clickAccountBtn();
         logger.info("Clicking login btn");
         hp.clickLoginBtn();
         

         LoginPage lp =new LoginPage(driver);
         lp.setUserMail(prop.getProperty("email"));
         lp.setPassword(prop.getProperty("password"));
         lp.clickLogin();
         logger.info("Enter user details & login btn clicked");

         MyAccountPage mp= new MyAccountPage(driver);
         boolean status= mp.isMyAccountPageExists();
         Assert.assertEquals(status,true,"Login failed");
         //Assert.assertTrue(status);
      }

      catch(Exception e){
        System.out.println(e.getMessage());
        Assert.fail();
      }

        logger.info("Finished Login Test");
    }
   }
