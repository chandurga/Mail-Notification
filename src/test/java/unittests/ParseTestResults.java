package unittests;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ParseTestResults {
    public static void main(String[] args) {
        try {
            File reportDir = new File("surefire-reports");
            File[] files = reportDir.listFiles();

            StringBuilder resultSummary = new StringBuilder();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".xml")) {
                    Document doc = dBuilder.parse(file);
                    doc.getDocumentElement().normalize();

                    NodeList testCases = doc.getElementsByTagName("testcase");

                    for (int i = 0; i < testCases.getLength(); i++) {
                        Element testCase = (Element) testCases.item(i);
                        String name = testCase.getAttribute("name");
                        String className = testCase.getAttribute("classname");
                        String time = testCase.getAttribute("time");
                        String status = "passed";

                        if (testCase.getElementsByTagName("failure").getLength() > 0) {
                            status = "failed";
                        } else if (testCase.getElementsByTagName("error").getLength() > 0) {
                            status = "error";
                        } else if (testCase.getElementsByTagName("skipped").getLength() > 0) {
                            status = "skipped";
                        }

                        resultSummary.append(String.format("Name: %s.%s, Status: %s, Time: %s seconds%n",
                                className, name, status, time));
                    }
                }
            }

            System.out.print(resultSummary.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
