package org.dom4j.goat;

import org.dom4j.AbstractTestCase;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;
import org.testng.annotations.Test;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 64274 on 2019/7/24.
 *
 * @ Description: TODO
 * @ author  山羊来了
 * @ date 2019/7/24---14:18
 */
public class Demo extends AbstractTestCase {


    @Test
    public void test() throws Exception {
        Document document = getDocument("/xml/student.xml");

        //		document.getRootElement().asXML();//去掉头部
        // 获取根元素
        Element root = document.getRootElement();
        System.out.println("Root: " + root.getName());

        // 获取所有子元素
        List<Element> childList = root.elements();
        System.out.println("total child count: " + childList.size());

        // 获取特定名称的子元素
        List<Element> childList2 = root.elements("hello");
        System.out.println("hello child: " + childList2.size());

        // 获取名字为指定名称的第一个子元素
        Element firstWorldElement = root.element("world");
        // 输出其属性
        System.out.println("first World Attr: "
                + firstWorldElement.attribute(0).getName() + "="
                + firstWorldElement.attributeValue("name"));

        System.out.println("迭代输出-----------------------");
        // 迭代输出
        for (Iterator iter = root.elementIterator(); iter.hasNext();) {
            Element e = (Element) iter.next();
            System.out.println(e.attributeValue("name"));

        }

        System.out.println("用DOMReader-----------------------");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        // 注意要用完整类名
        org.w3c.dom.Document document2 = db.parse(new File("students.xml "));

        DOMReader domReader = new DOMReader();

        // 将JAXP的Document转换为dom4j的Document
        Document document3 = domReader.read(document2);

        Element rootElement = document3.getRootElement();

        System.out.println("Root: " + rootElement.getName());

    }


    public  String fileToPath(String filename) throws UnsupportedEncodingException {
        URL url = this.getClass().getResource(filename);
        return java.net.URLDecoder.decode(url.getPath(),"UTF-8");
    }







}
