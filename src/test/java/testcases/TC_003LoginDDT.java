package testcases;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.MyAccountPage;
import testbase.BaseTest;
import utilities.DataProviderUtil;

public class TC_003LoginDDT extends BaseTest {

  @Test(dataProvider ="LoginData", dataProviderClass = DataProviderUtil.class, groups="datadriven")
  public void verifyLogin_DDT(String email, String pwd, String exp){

      logger.info("Starting LoginDDT");
      try {
          HomePage hp = new HomePage(driver);
          hp.clickAccountBtn();
          logger.info("Clicking login btn");
          hp.clickLoginBtn();

          LoginPage lp = new LoginPage(driver);
          lp.setUserMail(email);
          lp.setPassword(pwd);
          lp.clickLogin();
          logger.info("Enter user details & login btn clicked");

          MyAccountPage mp = new MyAccountPage(driver);
          boolean status = mp.isMyAccountPageExists();

          //Data Valid - success - test pass -logout
          //              fail - test fail

          //Data InValid - success - test fail -logout
          //                fail - test pass


          if (exp.equalsIgnoreCase("Valid")) {

              if (status == true) {

                  mp.clickLogout();
                  Assert.assertTrue(true);
              } else {

                  Assert.assertTrue(false);
              }
          } else {
              if (status == true) {

                  mp.clickLogout();
                  Assert.assertTrue(false);
              } else {

                  Assert.assertTrue(true);
              }
          }

      } catch (Exception e) {

          Assert.fail();
      }

      logger.info("Finished LoginDDT");

      }

  }

