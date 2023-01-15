package org.example;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;


public class App 
{
    public static void main(String[] args) {

        readXML();
        writeXMLFile();

    }

    public static void readXML() {
        try {
            File inputFile = new File("input.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("student");
            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    NodeList nameList = eElement.getChildNodes();
                    for (int j = 0; j < nameList.getLength(); j++) {
                        Node n = nameList.item(j);
                        if (n.getNodeType() == Node.ELEMENT_NODE) {
                            Element name = (Element) n;
                            System.out.println("Tag " + name.getTagName() + ": " + name.getTextContent());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void writeXMLFile() {
        File outputFile = new File("output.xml");
        DocumentBuilderFactory dbFactory2 = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder outBuilder = dbFactory2.newDocumentBuilder();
            Document outDoc = outBuilder.newDocument();
            // create root node
            Element root = outDoc.createElement("StudentList");
            // create student element
            Element student = outDoc.createElement("student");
            // create parent element
            Element parent = outDoc.createElement("parent");

            // create tag
            Element studentName = outDoc.createElement("studentName");
            Text nameValue = outDoc.createTextNode("Demo");
            studentName.appendChild(nameValue);
            // create tag
            Element studentID = outDoc.createElement("studentID");
            Text idValue = outDoc.createTextNode("444");
            studentID.appendChild(idValue);
            // create tag
            Element studentBirthday = outDoc.createElement("studentBirthday");
            Text birthdayValue = outDoc.createTextNode("01/01/1980");
            studentBirthday.appendChild(birthdayValue);
            // create tag
            Element parentName = outDoc.createElement("parentName");
            Text parentValue = outDoc.createTextNode("Carl");
            parentName.appendChild(parentValue);
            // add student tags to studentList root
            student.appendChild(studentName);
            student.appendChild(studentID);
            student.appendChild(studentBirthday);
            // add parent tags to studentList root
            parent.appendChild(parentName);

            root.appendChild(student);
            root.appendChild(parent);
            // add root (studentList) to document/XML
            outDoc.appendChild(root);
            // write from memory
            // create nguon data
            DOMSource source = new DOMSource(outDoc);
            // create result stream
            Result result = new StreamResult(outputFile);
            // ...
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
            System.out.println("End ...");
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }

    }
}
