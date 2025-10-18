package testcases;

import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;
import pages.RegistrationPage;
import testbase.BaseTest;

public class TC_001AccountRegistration extends BaseTest {

    @Test(groups = {"Regression", "Master"})
    public void verify_account_registration() throws InterruptedException {

        logger.info("Starting TC_001AccountRegistration");

        try {
            HomePage hp = new HomePage(driver);
            hp.clickAccountBtn();
            logger.info("Clicked on My Account");

            hp.clickRegisterBtn();
            logger.info("Clicked on Register Link");

            RegistrationPage rp = new RegistrationPage(driver);

            logger.info("Proving user details");
            rp.setFirstname("Ankitha");
            rp.setLastName(randomString().toUpperCase());
            //rp.setEmail("ankithaelsa12@gmail.com");
            rp.setEmail(randomString() + "@gmail.com"); //  create random data at runtime
            rp.setTelephone(randomNumbers());
            String password = randomAlphaNumeric();
            rp.setPassword(password);
            rp.setConfirmPassword(password);
            Thread.sleep(2000);
            rp.clickPrivacyPolicy();
            rp.clickRegister();

            logger.info("Validating expected msg");
            String actualconfirmationmsg = rp.getConfirmationMessage();
            if(actualconfirmationmsg.equals("Your Account Has Been Created!")) {
                Assert.assertTrue(true);
            }
            else{

                logger.error("Test failed");
                logger.debug("Debug logs");
                Assert.assertTrue(false);
            }
           }
            catch (Exception e) {
            Assert.fail();
          }

            logger.info("Test finished");
       }

      }
