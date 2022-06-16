package xml.parse;

import java.io.*;
import java.util.*;

import org.dom4j.*;
import org.dom4j.io.*;

public class Dom4jTest {

    public static void main(String arge[]) {
        test2();
    }

    private static void test2() {
        try {
            File src = new File("resource/data.xml");
            SAXReader reader = new SAXReader();
            Document doc = reader.read(src);
            // 解析FieldName字段
            Element fieldNames = doc.getRootElement().element("Objects").element("FieldName");
            for (Iterator i = fieldNames.elementIterator("N"); i.hasNext(); ) {
                Element foo = (Element) i.next();
                System.out.println(foo.getText());
                System.out.println(foo.attribute("i").getText());
            }

            // 解析FieldValue
            Element objects = doc.getRootElement().element("Objects").element("FieldValue");
            for (Iterator i = objects.elementIterator("Object"); i.hasNext(); ) {
                Element foo = (Element) i.next();
                System.out.println(foo.attribute("Dn").getText());
                for (Iterator j = foo.elementIterator("V"); j.hasNext(); ) {
                    Element row = (Element) j.next();
                    System.out.printf(row.attribute("i").getText());
                    System.out.println(": " + row.getText());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}