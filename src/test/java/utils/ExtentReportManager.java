package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

    private static ExtentReports extentReports; //Variable which stores the extent report

    //Getter method to get the extent report whenever asked for in the framework.
    //It is static as we want one instance of the report
    public static ExtentReports getReportInstance() {

        //Checks wether the report is initially created or not
        if (extentReports == null) {

            //Tells the path where to create the report
            String reportPath = System.getProperty("user.dir")
                    + "/test-output/ExtentReport.html";

            //It tells to create an html report and this is where the report will be stored
            ExtentSparkReporter sparkReporter =
                    new ExtentSparkReporter(reportPath);

            //Here we are storing the reportName and the Browser tab title
            sparkReporter.config().setReportName("API Automation Report");
            sparkReporter.config().setDocumentTitle("REST Assured API Test Report");


            //Creating the instance of the report which will be returned whenever the reportInstance is asked for
            extentReports = new ExtentReports();

            //Attaching the spark reporter to the extent report
            extentReports.attachReporter(sparkReporter);

            extentReports.setSystemInfo("Project", "API Automation");
            extentReports.setSystemInfo("Framework", "REST Assured");
            extentReports.setSystemInfo("Test Framework", "TestNG");
            extentReports.setSystemInfo("Java Version", "11");
        }

        return extentReports;
    }
}