package testbase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class BaseTest {

    public WebDriver driver; // driver obj created in listener class captures if not made static and conflict occurs
    public Logger logger;
    public Properties prop;

    @BeforeClass(groups={"Regression", "Sanity", "Master"})
    @Parameters({"os", "browser"})
    public void setUp(String os, String br) throws InterruptedException, IOException {

        FileInputStream file=new FileInputStream("./src/test/resources/config.properties");
        prop=new Properties();
        prop.load(file);

        logger= LogManager.getLogger(this.getClass());

        if(prop.getProperty("execution_env").equalsIgnoreCase("remote")) {

            DesiredCapabilities dc = new DesiredCapabilities();

            //OS
            if (os.equalsIgnoreCase("windows")) {
                dc.setPlatform(Platform.WIN11);
            }

            else if (os.equalsIgnoreCase("linux")) {
                dc.setPlatform(Platform.LINUX);
            }
            else if (os.equalsIgnoreCase("mac")) {
                dc.setPlatform(Platform.MAC);
            } else {
                System.out.println("No matching os");
                return;
            }

            //Browser
            switch (br.toLowerCase()) {

                case "chrome": dc.setBrowserName("chrome");break;
                case "edge": dc.setBrowserName("MicrosoftEdge");break;
                case "firefox": dc.setBrowserName("firefox");break;
                default: System.out.println("No matching browser");return;
            }

            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
        }
        if(prop.getProperty("execution_env").equalsIgnoreCase("local")) {

            switch (br.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "edge":
                    System.setProperty("webdriver.edge.driver", "C:\\Project\\Selenium\\msedgedriver.exe");
                    driver = new EdgeDriver();
                    break;
                default:
                    System.out.println("Invalid browser");
                    return;
            }
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(prop.getProperty("appURL"));
    }

    public String randomString(){

        // RSU predefined class in java(commons-lang3) - Only alphabets & numbers, no special char
        String generatedString= RandomStringUtils.randomAlphabetic(5);
        return generatedString;
    }

    public String randomNumbers(){

        String generatedNumber= RandomStringUtils.randomNumeric(10);
        return generatedNumber;
    }

    public String randomAlphaNumeric(){

        String generatedAlphaNum= RandomStringUtils.randomAlphanumeric(7);
        return generatedAlphaNum+"@";
    }

    //Capture Screenshot

    public String captureScreen(String tname) throws IOException {
        String timestamp= new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        TakesScreenshot ts=(TakesScreenshot) driver;
        File sourcefile= ts.getScreenshotAs(OutputType.FILE);
        String targetfilepath= System.getProperty("user.dir") +"\\screenshots\\" + tname +"__" + timestamp + ".png";;
        File targetfile= new File(targetfilepath);
        //sourcefile.renameTo(targetfile);
        FileUtils.copyFile(sourcefile, targetfile);
        return targetfilepath;
    }

    @AfterClass(groups={"Regression", "Sanity", "Master"})
    public  void teardown(){
        driver.quit();

    }
}
