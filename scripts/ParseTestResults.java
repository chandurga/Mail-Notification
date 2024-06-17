// scripts/ParseTestResults.java

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ParseTestResults {
    public static void main(String[] args) {
        int totalTests = 0;
        int passedTests = 0;
        int failedTests = 0;
        int skippedTests = 0;

        try (FileWriter writer = new FileWriter("test_results_summary.txt")) {
            for (String filePath : args) {
                File xmlFile = new File(filePath);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();

                NodeList testCases = doc.getElementsByTagName("testcase");

                for (int i = 0; i < testCases.getLength(); i++) {
                    totalTests++;
                    Element testCase = (Element) testCases.item(i);
                    String name = testCase.getAttribute("name");
                    String className = testCase.getAttribute("classname");
                    String status = "passed";
                    if (testCase.getElementsByTagName("failure").getLength() > 0) {
                        status = "failed";
                        failedTests++;
                    } else if (testCase.getElementsByTagName("skipped").getLength() > 0) {
                        status = "skipped";
                        skippedTests++;
                    } else {
                        passedTests++;
                    }
                    writer.write(className + "." + name + ": " + status + "\n");
                }
            }

            String overallStatus = failedTests > 0 ? "Failed" : "Passed";
            writer.write("\nTotal Test Run Passed: " + passedTests +
                    " Failure: " + failedTests +
                    " Status: " + overallStatus);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
