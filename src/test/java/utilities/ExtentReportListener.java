package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testbase.BaseTest;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtentReportListener implements ITestListener {

        public ExtentSparkReporter sparkReporter ;
        public ExtentReports extent;
        public ExtentTest test;
        //public static Map<String, ExtentTest> testMap = new HashMap<>();
        String repname;

        public void onStart(ITestContext context) {//run only once

//            SimpleDateFormat df= new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
//            Date dt=new Date();
//            String timestamp= df.format(dt);

            String timestamp= new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

            //specify path to store report

            //sparkReporter= new ExtentSparkReporter(System.getProperty("user.dir") + "/reports/myExtentReport1.html");

            //rather than hardcording report name - use Timestamp and create separate report
            // for every run &  preserve history

            repname= "Test-Report" +timestamp+ ".html";
            sparkReporter= new ExtentSparkReporter(".\\reports\\" +repname);
            sparkReporter.config().setDocumentTitle("Automation Report"); // report title
            sparkReporter.config().setReportName("Functional Test"); // report name
            sparkReporter.config().setTheme(Theme.DARK);

            extent=new ExtentReports();
            extent.attachReporter(sparkReporter);

            extent.setSystemInfo("Env","QA");
            extent.setSystemInfo("Tester","Ankitha");

            try {
                String browser = context.getCurrentXmlTest().getParameter("browser"); //frm xml get details
                extent.setSystemInfo("Browser", browser);
//            String os= context.getCurrentXmlTest().getParameter("os");
//            extent.setSystemInfo("OS", os);
                List<String> groups = context.getCurrentXmlTest().getIncludedGroups();
                if (!groups.isEmpty())
                    extent.setSystemInfo("Groups", groups.toString());

                extent.setSystemInfo("User Name", System.getProperty("user.name"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

//    public void onTestStart(ITestResult result) {
//        ExtentTest test = extent.createTest(result.getName());
//        test.log(Status.INFO, "Starting test: " + result.getName());
//        testMap.put(result.getName(), test);
//    }

        public void onTestSuccess(ITestResult result) { // result contains details about test
            test= extent.createTest(result.getTestClass().getName() +" " +result.getName()); //create new entry in report, if no test start
            //test = testMap.get(result.getName()); // if test start used, to avoid duplicates events
            test.assignCategory(result.getMethod().getGroups());
            test.log(Status.PASS, "Test passed is " +result.getName() +" Test passed belongs to class " +
                    result.getTestClass().getName());
        }

        public void onTestFailure(ITestResult result) {
            test= extent.createTest(result.getTestClass().getName() +" " +result.getName()); //create new entry in report, if no test start
            //test = testMap.get(result.getName());
            test.assignCategory(result.getMethod().getGroups());
            test.log(Status.FAIL, "Test failed is " +result.getName() + "  Test failed cause: " + result.getThrowable());
            test.log(Status.INFO, result.getThrowable().getMessage());
            try {
                BaseTest testInstance = (BaseTest) result.getInstance();
                String imgPath = testInstance.captureScreen(result.getName());
                test.addScreenCaptureFromPath(imgPath);
            } catch (Exception e) {
                test.log(Status.WARNING, "Failed to capture screenshot: " + e.getMessage());
            }

        }

        public void onTestSkipped(ITestResult result) {

            test= extent.createTest(result.getTestClass().getName() +" " +result.getName()); //create new entry in report, if no test start
            //test = testMap.get(result.getName());
            test.log(Status.SKIP, "Test skipped is " +result.getName());
            test.log(Status.INFO, result.getThrowable().getMessage());

        }

        public void onFinish(ITestContext context) {
            extent.flush(); // whatever created will be updated on this, mandatory
            String extentreportpath= System.getProperty("user.dir") +"\\reports\\" +repname;
            File extentreport= new File(extentreportpath);
            try{
                Desktop.getDesktop().browse(extentreport.toURI());

            } catch (Exception e) {
                e.printStackTrace();
             }

            try{

              URL url = new URL ("file:///"+System.getProperty("user.dir") + "\\reports\\" + repname);

                ImageHtmlEmail email = new ImageHtmlEmail();
                email.setDataSourceResolver(new DataSourceUrlResolver(url));

                // SMTP configuration (Gmail example)
                email.setHostName("smtp.gmail.com");
                email.setSmtpPort(465);
                email.setAuthenticator(new DefaultAuthenticator("your_email@gmail.com", "your_app_password"));
                email.setSSLOnConnect(true);

                // Sender and recipients
                email.setFrom("your_email@gmail.com", "Automation Report Bot");
                email.addTo("recipient_email@gmail.com");
                email.setSubject("Automation Test Report with Embedded Screenshot");
                email.setMsg("Automation Report");
                email.attach(url, "extent report", "please check report..");
                email.send();


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


