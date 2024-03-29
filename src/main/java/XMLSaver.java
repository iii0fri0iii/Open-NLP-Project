import javax.lang.model.element.Element;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.util.*;
import javax.xml.stream.events.*;

public class XMLSaver {
    public static void wholeFileSaver(List<List<List<String>>> src, String filename, String URL) throws FileNotFoundException, XMLStreamException {
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        XMLEventWriter writer = outputFactory.createXMLEventWriter(
                new FileOutputStream(filename));
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        boolean yesfound;

        Characters indent = eventFactory.createCharacters("\t");
        Characters newLine = eventFactory.createCharacters("\n");

        StartDocument startDocument = eventFactory.createStartDocument();
        StartElement startRoot = eventFactory.createStartElement(
                "", "", "results");

        EndElement endRoot = eventFactory.createEndElement("", "", "results");
        StartElement startLevel1 = eventFactory.createStartElement(
                "", "", "result");
        StartElement startLevel2 = eventFactory.createStartElement(
                "", "", "word");
        StartElement startLevel31 = eventFactory.createStartElement(
                "", "", "token");
        StartElement startLevel32 = eventFactory.createStartElement(
                "", "", "pos");
        StartElement startLevel33 = eventFactory.createStartElement(
                "", "", "lemma");
        EndElement endLevel2 = eventFactory.createEndElement("", "", "word");
        EndElement endLevel31 = eventFactory.createEndElement("", "", "token");
        EndElement endLevel32 = eventFactory.createEndElement("", "", "pos");
        EndElement endLevel33 = eventFactory.createEndElement("", "", "lemma");
        EndElement endLevel1 = eventFactory.createEndElement("", "", "result");
        EndDocument endDocument = eventFactory.createEndDocument();

        writer.add(startDocument);
        writer.add(newLine);
        writer.add(startRoot);
        writer.add(eventFactory.createAttribute("url", URL));
        writer.add(newLine);
        for(List<List<String>> sent : src){
            writer.add(indent);
            writer.add(startLevel1);
            writer.add(newLine);
            yesfound=false;
            for(List<String> word : sent){
                Characters token = eventFactory.createCharacters(word.get(0));
                Characters pos = eventFactory.createCharacters(word.get(1));
                Characters lemma = eventFactory.createCharacters(word.get(2));
                Attribute attr;
                writer.add(indent);
                writer.add(indent);
                writer.add(startLevel2);
                if (word.size()>=4&&!yesfound){
                    attr = eventFactory.createAttribute("interest", "yes");
                    word.remove(word.size()-1);
                    yesfound=true;
                }
                else {
                    attr = eventFactory.createAttribute("interest", "no");
                }
                writer.add(attr);
                writer.add(newLine);
                writer.add(indent);
                writer.add(indent);
                writer.add(indent);
                writer.add(startLevel31);
                writer.add(token);
                writer.add(endLevel31);
                writer.add(newLine);
                writer.add(indent);
                writer.add(indent);
                writer.add(indent);
                writer.add(startLevel32);
                writer.add(pos);
                writer.add(endLevel32);
                writer.add(newLine);
                writer.add(indent);
                writer.add(indent);
                writer.add(indent);
                writer.add(startLevel33);
                writer.add(lemma);
                writer.add(endLevel33);
                writer.add(newLine);
                writer.add(indent);
                writer.add(indent);
                writer.add(endLevel2);
                writer.add(newLine);
            }
            writer.add(indent);
            writer.add(endLevel1);
            writer.add(newLine);
        }
        writer.add(endRoot);
        writer.add(newLine);
        writer.add(endDocument);
        writer.close();
    }
}
