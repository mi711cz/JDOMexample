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
        DocumentBuilder outBuilder = null;
        try {
            outBuilder = dbFactory2.newDocumentBuilder();
            Document outDoc = outBuilder.newDocument();

            Element root = outDoc.createElement("StudenList");
            Element student = outDoc.createElement("student");
            Element studentName = outDoc.createElement("studentName");
            Text nameValue = outDoc.createTextNode("Demo");
            studentName.appendChild(nameValue);
            Element studentID = outDoc.createElement("studentID");
            Text idValue = outDoc.createTextNode("444");
            studentID.appendChild(idValue);
            Element studentBirthday = outDoc.createElement("studentBirthday");
            Text birhtdayValue = outDoc.createTextNode("01/01/1980");
            studentBirthday.appendChild(birhtdayValue);
            student.appendChild(studentName);
            student.appendChild(studentID);
            student.appendChild(studentBirthday);
            root.appendChild(student);
            outDoc.appendChild(root);
            DOMSource source = new DOMSource(outDoc);
            Result result = new StreamResult(outputFile);
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
